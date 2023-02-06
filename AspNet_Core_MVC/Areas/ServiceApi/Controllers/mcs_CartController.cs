///////////////////////////////////////
// Author: Orhan Erdebil
// CreatedDate: 6 Şubat 2023 Pazartesi
// License terms are specified in the "license.txt" file in the root directory.
///////////////////////////////////////
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using eCommerce.Repositories;
using eCommerce.Models;
using eCommerce.WebApi.Models;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace eCommerce.WebApi.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    public class mcs_CartController : ControllerBase
    {
        mcs_CartRepository _mcs_CartRepo;

        public mcs_CartController()
        {
            _mcs_CartRepo = new mcs_CartRepository();
        }

        // GET: api/<mcs_CartController>
        
        [HttpGet]        
        public IActionResult Get()
        {
            List<mcs_Cart> items = _mcs_CartRepo.List(0, 1000);
            if (items == null)
                return NotFound();

            return Ok(items.Select(i => mcs_CartDTO.ToDataTransferObject(i)).ToList());
        }

        // GET api/<mcs_CartController>/5
        [HttpGet("{CartId}")]
        public IActionResult Get(int CartId)
        {
            mcs_Cart item = _mcs_CartRepo.Get(CartId);
            if (item == null)
                return NotFound();

            return Ok(mcs_CartDTO.ToDataTransferObject(item));
        }

        // POST api/<mcs_CartController>
        [HttpPost("post")]
        public IActionResult Post([FromBody] mcs_CartDTO model)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    var item = mcs_CartDTO.FromDataTransferObject(model);
                    _mcs_CartRepo.Insert(item);
                    // New id can be assigned here
                    return Ok(model);
                }
                else
                {
                    return BadRequest(model);
                }
            }
            catch
            {
            }
            return BadRequest();
        }

        // PUT api/<mcs_CartController>/5
        [HttpPost("put")]
        public IActionResult Put([FromBody] mcs_CartDTO model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    var item = mcs_CartDTO.FromDataTransferObject(model);
                    _mcs_CartRepo.Update(item);
                    return Ok();
                }
                else
                {
                    return BadRequest(model);
                }
            }
            catch
            {
            }
            return BadRequest();
        }

        // DELETE api/<mcs_CartController>/5
        [HttpPost("delete/{CartId}")]
        public void Delete(int CartId)
        {
            try
            {
                _mcs_CartRepo.Delete(CartId);
            }
            catch
            {
            }
        }
    }
}
