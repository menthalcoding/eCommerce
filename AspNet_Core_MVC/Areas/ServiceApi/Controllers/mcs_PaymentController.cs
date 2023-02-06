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
    public class mcs_PaymentController : ControllerBase
    {
        mcs_PaymentRepository _mcs_PaymentRepo;

        public mcs_PaymentController()
        {
            _mcs_PaymentRepo = new mcs_PaymentRepository();
        }

        // GET: api/<mcs_PaymentController>
        
        [HttpGet]        
        public IActionResult Get()
        {
            List<mcs_Payment> items = _mcs_PaymentRepo.List(0, 1000);
            if (items == null)
                return NotFound();

            return Ok(items.Select(i => mcs_PaymentDTO.ToDataTransferObject(i)).ToList());
        }

        // GET api/<mcs_PaymentController>/5
        [HttpGet("{PaymentId}")]
        public IActionResult Get(int PaymentId)
        {
            mcs_Payment item = _mcs_PaymentRepo.Get(PaymentId);
            if (item == null)
                return NotFound();

            return Ok(mcs_PaymentDTO.ToDataTransferObject(item));
        }

        // POST api/<mcs_PaymentController>
        [HttpPost("post")]
        public IActionResult Post([FromBody] mcs_PaymentDTO model)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    var item = mcs_PaymentDTO.FromDataTransferObject(model);
                    _mcs_PaymentRepo.Insert(item);
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

        // PUT api/<mcs_PaymentController>/5
        [HttpPost("put")]
        public IActionResult Put([FromBody] mcs_PaymentDTO model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    var item = mcs_PaymentDTO.FromDataTransferObject(model);
                    _mcs_PaymentRepo.Update(item);
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

        // DELETE api/<mcs_PaymentController>/5
        [HttpPost("delete/{PaymentId}")]
        public void Delete(int PaymentId)
        {
            try
            {
                _mcs_PaymentRepo.Delete(PaymentId);
            }
            catch
            {
            }
        }
    }
}
