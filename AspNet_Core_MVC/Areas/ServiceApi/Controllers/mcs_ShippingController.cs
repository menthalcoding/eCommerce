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
    public class mcs_ShippingController : ControllerBase
    {
        mcs_ShippingRepository _mcs_ShippingRepo;

        public mcs_ShippingController()
        {
            _mcs_ShippingRepo = new mcs_ShippingRepository();
        }

        // GET: api/<mcs_ShippingController>
        
        [HttpGet]        
        public IActionResult Get()
        {
            List<mcs_Shipping> items = _mcs_ShippingRepo.List(0, 1000);
            if (items == null)
                return NotFound();

            return Ok(items.Select(i => mcs_ShippingDTO.ToDataTransferObject(i)).ToList());
        }

        // GET api/<mcs_ShippingController>/5
        [HttpGet("{ShippingId}")]
        public IActionResult Get(int ShippingId)
        {
            mcs_Shipping item = _mcs_ShippingRepo.Get(ShippingId);
            if (item == null)
                return NotFound();

            return Ok(mcs_ShippingDTO.ToDataTransferObject(item));
        }

        // POST api/<mcs_ShippingController>
        [HttpPost("post")]
        public IActionResult Post([FromBody] mcs_ShippingDTO model)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    var item = mcs_ShippingDTO.FromDataTransferObject(model);
                    _mcs_ShippingRepo.Insert(item);
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

        // PUT api/<mcs_ShippingController>/5
        [HttpPost("put")]
        public IActionResult Put([FromBody] mcs_ShippingDTO model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    var item = mcs_ShippingDTO.FromDataTransferObject(model);
                    _mcs_ShippingRepo.Update(item);
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

        // DELETE api/<mcs_ShippingController>/5
        [HttpPost("delete/{ShippingId}")]
        public void Delete(int ShippingId)
        {
            try
            {
                _mcs_ShippingRepo.Delete(ShippingId);
            }
            catch
            {
            }
        }
    }
}
