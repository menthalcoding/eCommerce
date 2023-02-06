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
    public class mcs_CategoryController : ControllerBase
    {
        mcs_CategoryRepository _mcs_CategoryRepo;

        public mcs_CategoryController()
        {
            _mcs_CategoryRepo = new mcs_CategoryRepository();
        }

        // GET: api/<mcs_CategoryController>
        
        [HttpGet]        
        public IActionResult Get()
        {
            List<mcs_Category> items = _mcs_CategoryRepo.List(0, 1000);
            if (items == null)
                return NotFound();

            return Ok(items.Select(i => mcs_CategoryDTO.ToDataTransferObject(i)).ToList());
        }

        // GET api/<mcs_CategoryController>/5
        [HttpGet("{CategoryId}")]
        public IActionResult Get(int CategoryId)
        {
            mcs_Category item = _mcs_CategoryRepo.Get(CategoryId);
            if (item == null)
                return NotFound();

            return Ok(mcs_CategoryDTO.ToDataTransferObject(item));
        }

        // POST api/<mcs_CategoryController>
        [HttpPost("post")]
        public IActionResult Post([FromBody] mcs_CategoryDTO model)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    var item = mcs_CategoryDTO.FromDataTransferObject(model);
                    _mcs_CategoryRepo.Insert(item);
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

        // PUT api/<mcs_CategoryController>/5
        [HttpPost("put")]
        public IActionResult Put([FromBody] mcs_CategoryDTO model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    var item = mcs_CategoryDTO.FromDataTransferObject(model);
                    _mcs_CategoryRepo.Update(item);
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

        // DELETE api/<mcs_CategoryController>/5
        [HttpPost("delete/{CategoryId}")]
        public void Delete(int CategoryId)
        {
            try
            {
                _mcs_CategoryRepo.Delete(CategoryId);
            }
            catch
            {
            }
        }
    }
}
