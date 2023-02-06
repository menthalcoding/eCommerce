///////////////////////////////////////
// Author: Orhan Erdebil
// CreatedDate: 6 Şubat 2023 Pazartesi
// License terms are specified in the "license.txt" file in the root directory.
///////////////////////////////////////
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using System.ComponentModel.DataAnnotations;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using System.Xml.Linq;

namespace eCommerce.WebApi.Controllers
{
    public class LoginWebApiModel
    {
        [Required(ErrorMessage = "Please enter your e-mail address.")]
        [DataType(DataType.EmailAddress, ErrorMessage = "Please enter an e-mail address in valid format.")]
        public string Email { get; set; }

        [Required(ErrorMessage = "Please enter the password.")]
        [DataType(DataType.Password, ErrorMessage = "Please enter a valid password.")]
        public string Password { get; set; }

        public bool Persistent { get; set; }

        public bool Lock { get; set; }
    }

    [Route("api/[controller]")]
    [ApiController]
    public class AuthenticationController : ControllerBase
    {
        private readonly IConfiguration _configuration;
        private readonly UserManager<IdentityUser> _userManager;
        private readonly SignInManager<IdentityUser> _signInManager;

        public AuthenticationController(IConfiguration configuration, UserManager<IdentityUser> userManager, SignInManager<IdentityUser> signInManager)
        {
            _configuration = configuration;
            _userManager = userManager;
            _signInManager = signInManager;
        }

        [HttpPost]
        public async Task<IActionResult> Token(LoginWebApiModel model)
        {
            if (ModelState.IsValid)
            {
                IdentityUser user = await _userManager.FindByEmailAsync(model.Email);
                if (user != null)
                {
                    var isInRole = _userManager.IsInRoleAsync(user, "WebApiUser");

                    if(isInRole.Result)
                    {
                        return BadRequest("Not found user: Email or password is incorrect or unauthorized user.");
                    }

                    await _signInManager.SignOutAsync();
                    Microsoft.AspNetCore.Identity.SignInResult result = await _signInManager.PasswordSignInAsync(user, model.Password, false, false);

                    if (result.Succeeded)
                    {
                        await _userManager.ResetAccessFailedCountAsync(user);

                        //create claims details based on the user information
                        var claims = new[] {
                            new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
                            new Claim(JwtRegisteredClaimNames.Iat, DateTime.UtcNow.ToString()),
                            new Claim("Email", model.Email)
                        };

                        var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration.GetValue<string> ("Jwt:Key")));
                        var signIn = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);
                        var token = new JwtSecurityToken(_configuration.GetValue<string> ("Jwt:Issuer"), 
                                _configuration.GetValue<string> ("Jwt:Audience"), 
                                claims,
                                expires: DateTime.UtcNow.AddMinutes(10),
                                signingCredentials: signIn);

                        return Ok(new JwtSecurityTokenHandler().WriteToken(token));
                    }
                    else
                    {
                        await _userManager.AccessFailedAsync(user);

                        int failcount = await _userManager.GetAccessFailedCountAsync(user);
                        if (failcount == 10)
                        {
                            await _userManager.SetLockoutEndDateAsync(user, new DateTimeOffset(DateTime.Now.AddHours(1)));
                            return BadRequest("Locked: Your account has been locked for 1 hour because you have made 10 unsuccessful login attempts in a row.");
                        }
                        else
                        {
                            if (result.IsLockedOut)
                                return BadRequest("Locked: Your account has been locked for 1 hour because you have made 10 unsuccessful login attempts in a row.");
                            else
                                return BadRequest("Not found user: Email or password is incorrect or unauthorized user.");
                        }
                    }
                }
            }
            return BadRequest();
        }
    }
}
