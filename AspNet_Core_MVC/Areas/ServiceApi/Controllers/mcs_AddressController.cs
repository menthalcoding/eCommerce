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
    public class mcs_AddressController : ControllerBase
    {
        mcs_AddressRepository _mcs_AddressRepo;

        public mcs_AddressController()
        {
            _mcs_AddressRepo = new mcs_AddressRepository();
        }

        // GET: api/<mcs_AddressController>
        
        [HttpGet]        
        public IActionResult Get()
        {
            List<mcs_Address> items = _mcs_AddressRepo.List(0, 1000);
            if (items == null)
                return NotFound();

            return Ok(items.Select(i => mcs_AddressDTO.ToDataTransferObject(i)).ToList());
        }

        // GET api/<mcs_AddressController>/5
        [HttpGet("{AddressId}")]
        public IActionResult Get(int AddressId)
        {
            mcs_Address item = _mcs_AddressRepo.Get(AddressId);
            if (item == null)
                return NotFound();

            return Ok(mcs_AddressDTO.ToDataTransferObject(item));
        }

        // POST api/<mcs_AddressController>
        [HttpPost("post")]
        public IActionResult Post([FromBody] mcs_AddressDTO model)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    var item = mcs_AddressDTO.FromDataTransferObject(model);
                    _mcs_AddressRepo.Insert(item);
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

        // PUT api/<mcs_AddressController>/5
        [HttpPost("put")]
        public IActionResult Put([FromBody] mcs_AddressDTO model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    var item = mcs_AddressDTO.FromDataTransferObject(model);
                    _mcs_AddressRepo.Update(item);
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

        // DELETE api/<mcs_AddressController>/5
        [HttpPost("delete/{AddressId}")]
        public void Delete(int AddressId)
        {
            try
            {
                _mcs_AddressRepo.Delete(AddressId);
            }
            catch
            {
            }
        }
    }
}
