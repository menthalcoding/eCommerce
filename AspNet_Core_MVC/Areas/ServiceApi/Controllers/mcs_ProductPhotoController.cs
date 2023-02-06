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
    public class mcs_ProductPhotoController : ControllerBase
    {
        mcs_ProductPhotoRepository _mcs_ProductPhotoRepo;

        public mcs_ProductPhotoController()
        {
            _mcs_ProductPhotoRepo = new mcs_ProductPhotoRepository();
        }

        // GET: api/<mcs_ProductPhotoController>
        
        [HttpGet]        
        public IActionResult Get()
        {
            List<mcs_ProductPhoto> items = _mcs_ProductPhotoRepo.List(0, 1000);
            if (items == null)
                return NotFound();

            return Ok(items.Select(i => mcs_ProductPhotoDTO.ToDataTransferObject(i)).ToList());
        }

        // GET api/<mcs_ProductPhotoController>/5
        [HttpGet("{ProductPhotoId}")]
        public IActionResult Get(Guid ProductPhotoId)
        {
            mcs_ProductPhoto item = _mcs_ProductPhotoRepo.Get(ProductPhotoId);
            if (item == null)
                return NotFound();

            return Ok(mcs_ProductPhotoDTO.ToDataTransferObject(item));
        }

        // POST api/<mcs_ProductPhotoController>
        [HttpPost("post")]
        public IActionResult Post([FromBody] mcs_ProductPhotoDTO model)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    var item = mcs_ProductPhotoDTO.FromDataTransferObject(model);
                    _mcs_ProductPhotoRepo.Insert(item);
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

        // PUT api/<mcs_ProductPhotoController>/5
        [HttpPost("put")]
        public IActionResult Put([FromBody] mcs_ProductPhotoDTO model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    var item = mcs_ProductPhotoDTO.FromDataTransferObject(model);
                    _mcs_ProductPhotoRepo.Update(item);
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

        // DELETE api/<mcs_ProductPhotoController>/5
        [HttpPost("delete/{ProductPhotoId}")]
        public void Delete(Guid ProductPhotoId)
        {
            try
            {
                _mcs_ProductPhotoRepo.Delete(ProductPhotoId);
            }
            catch
            {
            }
        }
    }
}
