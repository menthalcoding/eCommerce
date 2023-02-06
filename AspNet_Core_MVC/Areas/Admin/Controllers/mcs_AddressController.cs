///////////////////////////////////////
// Author: Orhan Erdebil
// CreatedDate: 6 Şubat 2023 Pazartesi
// License terms are specified in the "license.txt" file in the root directory.
///////////////////////////////////////

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Microsoft.AspNetCore.Mvc;
//using System.Web.Mvc;
using OnlineCoding.Code.Helper;
using eCommerce.Models;
using eCommerce.Repositories;

namespace eCommerce.Controllers
{
    [Area(@"Admin")]
    public class mcs_AddressController : Controller
    {    
        mcs_AddressRepository mcs_AddressRepo;

        public mcs_AddressController()
        {
            mcs_AddressRepo = new mcs_AddressRepository();
        }

        //
        // GET: /mcs_Address/
        
        public ActionResult Index()
        {
            return View(mcs_AddressRepo.List(0, 1000));
        }
 
        //
        // GET: /mcs_Address/Details?id=5
        public ActionResult Details(int AddressId)
        {
			mcs_Address m = mcs_AddressRepo.Get(AddressId);
			m.mcs_ShippingList = mcs_AddressRepo.Getmcs_ShippingList(AddressId);

			return View (m);
        }
 
        //
        // GET: /mcs_Address/Create
        public ActionResult Create()
        {

            return View(new Models.mcs_Address());
        }
 
        //
        // POST: /mcs_Address/Create
        [HttpPost]
        public ActionResult Create(Models.mcs_Address model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_AddressRepo.Insert(model);
                    return RedirectToAction("Index");
                }
            }
            catch
            {
            }


            return View(model);
        }
 
        //
        // GET: /mcs_Address/Edit?id=5
        public ActionResult Edit(int AddressId)
        {
            try
            {

                return View(mcs_AddressRepo.Get(AddressId));
            }
            catch
            {
                return View(new Models.mcs_Address());
            }
        }
 
        //
        // POST: /mcs_Address/Edit?id=5
        [HttpPost]
        public ActionResult Edit(int AddressId, Models.mcs_Address model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_AddressRepo.Update(model);
 
                    return RedirectToAction("Index");
                }
                else
                {
                    return View(model);
                }
            }
            catch
            {
                return View(model);
            }
        }
 
        //
        // GET: /mcs_Address/Delete?id=5
        public ActionResult Delete(int AddressId)
        {
            try
            {
                return View(mcs_AddressRepo.Get(AddressId));
            }
            catch
            {
                return View();
            }
        }
 
        //
        // POST: /mcs_Address/Delete?id=5
        [HttpPost]
        public ActionResult Delete(int AddressId, Models.mcs_Address model)
        {
            try
            {
                mcs_AddressRepo.Delete(AddressId);
 
                return RedirectToAction("Index");
            }
            catch
            {
                return View(model);
            }
        }
    }
}