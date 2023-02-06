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
    public class mcs_CartController : Controller
    {    
        mcs_CartRepository mcs_CartRepo;

        public mcs_CartController()
        {
            mcs_CartRepo = new mcs_CartRepository();
        }

        //
        // GET: /mcs_Cart/
        
        public ActionResult Index()
        {
            return View(mcs_CartRepo.List(0, 1000));
        }
 
        //
        // GET: /mcs_Cart/Details?id=5
        public ActionResult Details(int CartId)
        {
			mcs_Cart m = mcs_CartRepo.Get(CartId);
			m.mcs_CartItemList = mcs_CartRepo.Getmcs_CartItemList(CartId);
			m.mcs_OrderList = mcs_CartRepo.Getmcs_OrderList(CartId);

			return View (m);
        }
 
        //
        // GET: /mcs_Cart/Create
        public ActionResult Create()
        {

            return View(new Models.mcs_Cart());
        }
 
        //
        // POST: /mcs_Cart/Create
        [HttpPost]
        public ActionResult Create(Models.mcs_Cart model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_CartRepo.Insert(model);
                    return RedirectToAction("Index");
                }
            }
            catch
            {
            }


            return View(model);
        }
 
        //
        // GET: /mcs_Cart/Edit?id=5
        public ActionResult Edit(int CartId)
        {
            try
            {

                return View(mcs_CartRepo.Get(CartId));
            }
            catch
            {
                return View(new Models.mcs_Cart());
            }
        }
 
        //
        // POST: /mcs_Cart/Edit?id=5
        [HttpPost]
        public ActionResult Edit(int CartId, Models.mcs_Cart model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_CartRepo.Update(model);
 
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
        // GET: /mcs_Cart/Delete?id=5
        public ActionResult Delete(int CartId)
        {
            try
            {
                return View(mcs_CartRepo.Get(CartId));
            }
            catch
            {
                return View();
            }
        }
 
        //
        // POST: /mcs_Cart/Delete?id=5
        [HttpPost]
        public ActionResult Delete(int CartId, Models.mcs_Cart model)
        {
            try
            {
                mcs_CartRepo.Delete(CartId);
 
                return RedirectToAction("Index");
            }
            catch
            {
                return View(model);
            }
        }
    }
}