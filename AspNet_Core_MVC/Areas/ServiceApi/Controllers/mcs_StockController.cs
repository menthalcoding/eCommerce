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
    public class mcs_StockController : ControllerBase
    {
        mcs_StockRepository _mcs_StockRepo;

        public mcs_StockController()
        {
            _mcs_StockRepo = new mcs_StockRepository();
        }

        // GET: api/<mcs_StockController>
        
        [HttpGet]        
        public IActionResult Get()
        {
            List<mcs_Stock> items = _mcs_StockRepo.List(0, 1000);
            if (items == null)
                return NotFound();

            return Ok(items.Select(i => mcs_StockDTO.ToDataTransferObject(i)).ToList());
        }

        // GET api/<mcs_StockController>/5
        [HttpGet("{StockId}")]
        public IActionResult Get(int StockId)
        {
            mcs_Stock item = _mcs_StockRepo.Get(StockId);
            if (item == null)
                return NotFound();

            return Ok(mcs_StockDTO.ToDataTransferObject(item));
        }

        // POST api/<mcs_StockController>
        [HttpPost("post")]
        public IActionResult Post([FromBody] mcs_StockDTO model)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    var item = mcs_StockDTO.FromDataTransferObject(model);
                    _mcs_StockRepo.Insert(item);
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

        // PUT api/<mcs_StockController>/5
        [HttpPost("put")]
        public IActionResult Put([FromBody] mcs_StockDTO model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    var item = mcs_StockDTO.FromDataTransferObject(model);
                    _mcs_StockRepo.Update(item);
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

        // DELETE api/<mcs_StockController>/5
        [HttpPost("delete/{StockId}")]
        public void Delete(int StockId)
        {
            try
            {
                _mcs_StockRepo.Delete(StockId);
            }
            catch
            {
            }
        }
    }
}
