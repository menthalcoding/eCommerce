///////////////////////////////////////
// Author: Orhan Erdebil
// CreatedDate: 18 Ocak 2023 Çarşamba
// License terms are specified in the "license.txt" file in the root directory.
///////////////////////////////////////

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Microsoft.AspNetCore.Mvc;
using OnlineCoding.Code.Helper;
using eCommerce.Models;
using eCommerce.Repositories;
using System.Security.Claims;

namespace eCommerce.Controllers
{
    public class AddressController : Controller
    {
        mcs_AddressRepository AddressRepo;

        public AddressController()
        {
            AddressRepo = new mcs_AddressRepository();
        }

        //
        // GET: /Address/

        public IActionResult Index()
        {
            return Ok(AddressRepo.List(User.FindFirstValue(ClaimTypes.NameIdentifier), 0, 1000));
        }

        //
        // GET: /Address/Details?id=5
        public IActionResult Details(int AddressId)
        {
            mcs_Address m = AddressRepo.Get(AddressId);
            //m.ShippingList = AddressRepo.GetShippingList(AddressId);

            return Ok(m);
        }

        //
        // GET: /Address/Create
        public ActionResult Create()
        {

            return View(new Models.mcs_Address());
        }

        //
        // POST: /Address/Create
        [HttpPost]
        public IActionResult Create([FromBody]Models.mcs_Address model)
        {
            try
            {
                model.UserId = User.FindFirstValue(ClaimTypes.NameIdentifier);
                ModelState.Remove("UserId");

                if (ModelState.IsValid)
                {
                    AddressRepo.Insert(model);
                    return Ok(true);
                }
            }
            catch
            {
            }
            return BadRequest(false);
        }

        //
        // GET: /Address/Edit?id=5
        public IActionResult Edit(int AddressId)
        {
            try
            {

                return Ok(AddressRepo.Get(AddressId));
            }
            catch
            {
                return BadRequest(new Models.mcs_Address());
            }
        }

        //
        // POST: /Address/Edit?id=5
        [HttpPost]
        public IActionResult Edit(int AddressId, Models.mcs_Address model)
        {
            try
            {
                model.UserId = User.FindFirstValue(ClaimTypes.NameIdentifier);
                ModelState.Remove("UserId");

                if (ModelState.IsValid)
                {
                    AddressRepo.Update(model);

                    return Ok(true);
                }
            }
            catch
            {
            }
            return BadRequest(model);
        }

        //
        // GET: /Address/Delete?id=5
        public IActionResult Delete(int AddressId)
        {
            try
            {
                AddressRepo.Delete(User.FindFirstValue(ClaimTypes.NameIdentifier), AddressId);
                return Ok(true);
            }
            catch
            {
            }
            return BadRequest(false);
        }
    }
}