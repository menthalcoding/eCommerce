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
    public class mcs_OrderController : ControllerBase
    {
        mcs_OrderRepository _mcs_OrderRepo;

        public mcs_OrderController()
        {
            _mcs_OrderRepo = new mcs_OrderRepository();
        }

        // GET: api/<mcs_OrderController>
        
        [HttpGet]        
        public IActionResult Get()
        {
            List<mcs_Order> items = _mcs_OrderRepo.List(0, 1000);
            if (items == null)
                return NotFound();

            return Ok(items.Select(i => mcs_OrderDTO.ToDataTransferObject(i)).ToList());
        }

        // GET api/<mcs_OrderController>/5
        [HttpGet("{OrderId}")]
        public IActionResult Get(int OrderId)
        {
            mcs_Order item = _mcs_OrderRepo.Get(OrderId);
            if (item == null)
                return NotFound();

            return Ok(mcs_OrderDTO.ToDataTransferObject(item));
        }

        // POST api/<mcs_OrderController>
        [HttpPost("post")]
        public IActionResult Post([FromBody] mcs_OrderDTO model)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    var item = mcs_OrderDTO.FromDataTransferObject(model);
                    _mcs_OrderRepo.Insert(item);
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

        // PUT api/<mcs_OrderController>/5
        [HttpPost("put")]
        public IActionResult Put([FromBody] mcs_OrderDTO model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    var item = mcs_OrderDTO.FromDataTransferObject(model);
                    _mcs_OrderRepo.Update(item);
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

        // DELETE api/<mcs_OrderController>/5
        [HttpPost("delete/{OrderId}")]
        public void Delete(int OrderId)
        {
            try
            {
                _mcs_OrderRepo.Delete(OrderId);
            }
            catch
            {
            }
        }
    }
}
