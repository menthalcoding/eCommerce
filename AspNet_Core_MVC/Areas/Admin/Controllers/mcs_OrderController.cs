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
    public class mcs_OrderController : Controller
    {    
        mcs_OrderRepository mcs_OrderRepo;

        public mcs_OrderController()
        {
            mcs_OrderRepo = new mcs_OrderRepository();
        }

        //
        // GET: /mcs_Order/
        
        public ActionResult Index()
        {
            return View(mcs_OrderRepo.List(0, 1000));
        }
 
        //
        // GET: /mcs_Order/Details?id=5
        public ActionResult Details(int OrderId)
        {
			mcs_Order m = mcs_OrderRepo.Get(OrderId);
			m.mcs_ShippingList = mcs_OrderRepo.Getmcs_ShippingList(OrderId);

			return View (m);
        }
 
        //
        // GET: /mcs_Order/Create
        public ActionResult Create()
        {
			ViewBag.mcs_CartList = new mcs_CartRepository().List(0, 1000).ToSelectList<mcs_Cart>(nameof(mcs_Cart.CartId), nameof(mcs_Cart.CartId));

            return View(new Models.mcs_Order());
        }
 
        //
        // POST: /mcs_Order/Create
        [HttpPost]
        public ActionResult Create(Models.mcs_Order model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_OrderRepo.Insert(model);
                    return RedirectToAction("Index");
                }
            }
            catch
            {
            }

				ViewBag.mcs_CartList = new mcs_CartRepository().List(0, 1000).ToSelectList<mcs_Cart>(nameof(mcs_Cart.CartId), nameof(mcs_Cart.CartId));

            return View(model);
        }
 
        //
        // GET: /mcs_Order/Edit?id=5
        public ActionResult Edit(int OrderId)
        {
            try
            {
				ViewBag.mcs_CartList = new mcs_CartRepository().List(0, 1000).ToSelectList<mcs_Cart>(nameof(mcs_Cart.CartId), nameof(mcs_Cart.CartId));

                return View(mcs_OrderRepo.Get(OrderId));
            }
            catch
            {
                return View(new Models.mcs_Order());
            }
        }
 
        //
        // POST: /mcs_Order/Edit?id=5
        [HttpPost]
        public ActionResult Edit(int OrderId, Models.mcs_Order model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_OrderRepo.Update(model);
 
                    return RedirectToAction("Index");
                }
                else
                {
					ViewBag.mcs_CartList = new mcs_CartRepository().List(0, 1000).ToSelectList<mcs_Cart>(nameof(mcs_Cart.CartId), nameof(mcs_Cart.CartId));
                    return View(model);
                }
            }
            catch
            {
                return View(model);
            }
        }
 
        //
        // GET: /mcs_Order/Delete?id=5
        public ActionResult Delete(int OrderId)
        {
            try
            {
                return View(mcs_OrderRepo.Get(OrderId));
            }
            catch
            {
                return View();
            }
        }
 
        //
        // POST: /mcs_Order/Delete?id=5
        [HttpPost]
        public ActionResult Delete(int OrderId, Models.mcs_Order model)
        {
            try
            {
                mcs_OrderRepo.Delete(OrderId);
 
                return RedirectToAction("Index");
            }
            catch
            {
                return View(model);
            }
        }
    }
}