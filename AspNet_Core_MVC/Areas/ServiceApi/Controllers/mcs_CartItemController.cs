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
    public class mcs_CartItemController : ControllerBase
    {
        mcs_CartItemRepository _mcs_CartItemRepo;

        public mcs_CartItemController()
        {
            _mcs_CartItemRepo = new mcs_CartItemRepository();
        }

        // GET: api/<mcs_CartItemController>
        
        [HttpGet]        
        public IActionResult Get()
        {
            List<mcs_CartItem> items = _mcs_CartItemRepo.List(0, 1000);
            if (items == null)
                return NotFound();

            return Ok(items.Select(i => mcs_CartItemDTO.ToDataTransferObject(i)).ToList());
        }

        // GET api/<mcs_CartItemController>/5
        [HttpGet("{CartItemId}")]
        public IActionResult Get(int CartItemId)
        {
            mcs_CartItem item = _mcs_CartItemRepo.Get(CartItemId);
            if (item == null)
                return NotFound();

            return Ok(mcs_CartItemDTO.ToDataTransferObject(item));
        }

        // POST api/<mcs_CartItemController>
        [HttpPost("post")]
        public IActionResult Post([FromBody] mcs_CartItemDTO model)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    var item = mcs_CartItemDTO.FromDataTransferObject(model);
                    _mcs_CartItemRepo.Insert(item);
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

        // PUT api/<mcs_CartItemController>/5
        [HttpPost("put")]
        public IActionResult Put([FromBody] mcs_CartItemDTO model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    var item = mcs_CartItemDTO.FromDataTransferObject(model);
                    _mcs_CartItemRepo.Update(item);
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

        // DELETE api/<mcs_CartItemController>/5
        [HttpPost("delete/{CartItemId}")]
        public void Delete(int CartItemId)
        {
            try
            {
                _mcs_CartItemRepo.Delete(CartItemId);
            }
            catch
            {
            }
        }
    }
}
