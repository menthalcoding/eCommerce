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
    public class mcs_ShippingController : Controller
    {    
        mcs_ShippingRepository mcs_ShippingRepo;

        public mcs_ShippingController()
        {
            mcs_ShippingRepo = new mcs_ShippingRepository();
        }

        //
        // GET: /mcs_Shipping/
        
        public ActionResult Index()
        {
            return View(mcs_ShippingRepo.List(0, 1000));
        }
 
        //
        // GET: /mcs_Shipping/Details?id=5
        public ActionResult Details(int ShippingId)
        {
			mcs_Shipping m = mcs_ShippingRepo.Get(ShippingId);

			return View (m);
        }
 
        //
        // GET: /mcs_Shipping/Create
        public ActionResult Create()
        {
			ViewBag.mcs_OrderList = new mcs_OrderRepository().List(0, 1000).ToSelectList<mcs_Order>(nameof(mcs_Order.OrderId), nameof(mcs_Order.OrderId));
			ViewBag.mcs_AddressList = new mcs_AddressRepository().List(0, 1000).ToSelectList<mcs_Address>(nameof(mcs_Address.AddressId), nameof(mcs_Address.Name));

            return View(new Models.mcs_Shipping());
        }
 
        //
        // POST: /mcs_Shipping/Create
        [HttpPost]
        public ActionResult Create(Models.mcs_Shipping model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_ShippingRepo.Insert(model);
                    return RedirectToAction("Index");
                }
            }
            catch
            {
            }

				ViewBag.mcs_OrderList = new mcs_OrderRepository().List(0, 1000).ToSelectList<mcs_Order>(nameof(mcs_Order.OrderId), nameof(mcs_Order.OrderId));
				ViewBag.mcs_AddressList = new mcs_AddressRepository().List(0, 1000).ToSelectList<mcs_Address>(nameof(mcs_Address.AddressId), nameof(mcs_Address.Name));

            return View(model);
        }
 
        //
        // GET: /mcs_Shipping/Edit?id=5
        public ActionResult Edit(int ShippingId)
        {
            try
            {
				ViewBag.mcs_OrderList = new mcs_OrderRepository().List(0, 1000).ToSelectList<mcs_Order>(nameof(mcs_Order.OrderId), nameof(mcs_Order.OrderId));
				ViewBag.mcs_AddressList = new mcs_AddressRepository().List(0, 1000).ToSelectList<mcs_Address>(nameof(mcs_Address.AddressId), nameof(mcs_Address.Name));

                return View(mcs_ShippingRepo.Get(ShippingId));
            }
            catch
            {
                return View(new Models.mcs_Shipping());
            }
        }
 
        //
        // POST: /mcs_Shipping/Edit?id=5
        [HttpPost]
        public ActionResult Edit(int ShippingId, Models.mcs_Shipping model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_ShippingRepo.Update(model);
 
                    return RedirectToAction("Index");
                }
                else
                {
					ViewBag.mcs_OrderList = new mcs_OrderRepository().List(0, 1000).ToSelectList<mcs_Order>(nameof(mcs_Order.OrderId), nameof(mcs_Order.OrderId));
					ViewBag.mcs_AddressList = new mcs_AddressRepository().List(0, 1000).ToSelectList<mcs_Address>(nameof(mcs_Address.AddressId), nameof(mcs_Address.Name));
                    return View(model);
                }
            }
            catch
            {
                return View(model);
            }
        }
 
        //
        // GET: /mcs_Shipping/Delete?id=5
        public ActionResult Delete(int ShippingId)
        {
            try
            {
                return View(mcs_ShippingRepo.Get(ShippingId));
            }
            catch
            {
                return View();
            }
        }
 
        //
        // POST: /mcs_Shipping/Delete?id=5
        [HttpPost]
        public ActionResult Delete(int ShippingId, Models.mcs_Shipping model)
        {
            try
            {
                mcs_ShippingRepo.Delete(ShippingId);
 
                return RedirectToAction("Index");
            }
            catch
            {
                return View(model);
            }
        }
    }
}