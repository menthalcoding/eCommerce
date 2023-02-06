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
    public class mcs_ProductController : ControllerBase
    {
        mcs_ProductRepository _mcs_ProductRepo;

        public mcs_ProductController()
        {
            _mcs_ProductRepo = new mcs_ProductRepository();
        }

        // GET: api/<mcs_ProductController>
        
        [HttpGet]        
        public IActionResult Get()
        {
            List<mcs_Product> items = _mcs_ProductRepo.List(0, 1000);
            if (items == null)
                return NotFound();

            return Ok(items.Select(i => mcs_ProductDTO.ToDataTransferObject(i)).ToList());
        }

        // GET api/<mcs_ProductController>/5
        [HttpGet("{ProductId}")]
        public IActionResult Get(int ProductId)
        {
            mcs_Product item = _mcs_ProductRepo.Get(ProductId);
            if (item == null)
                return NotFound();

            return Ok(mcs_ProductDTO.ToDataTransferObject(item));
        }

        // POST api/<mcs_ProductController>
        [HttpPost("post")]
        public IActionResult Post([FromBody] mcs_ProductDTO model)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    var item = mcs_ProductDTO.FromDataTransferObject(model);
                    _mcs_ProductRepo.Insert(item);
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

        // PUT api/<mcs_ProductController>/5
        [HttpPost("put")]
        public IActionResult Put([FromBody] mcs_ProductDTO model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    var item = mcs_ProductDTO.FromDataTransferObject(model);
                    _mcs_ProductRepo.Update(item);
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

        // DELETE api/<mcs_ProductController>/5
        [HttpPost("delete/{ProductId}")]
        public void Delete(int ProductId)
        {
            try
            {
                _mcs_ProductRepo.Delete(ProductId);
            }
            catch
            {
            }
        }
    }
}
