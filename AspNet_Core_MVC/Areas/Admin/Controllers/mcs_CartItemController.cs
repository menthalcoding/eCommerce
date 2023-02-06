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
    public class mcs_CartItemController : Controller
    {    
        mcs_CartItemRepository mcs_CartItemRepo;

        public mcs_CartItemController()
        {
            mcs_CartItemRepo = new mcs_CartItemRepository();
        }

        //
        // GET: /mcs_CartItem/
        
        public ActionResult Index()
        {
            return View(mcs_CartItemRepo.List(0, 1000));
        }
 
        //
        // GET: /mcs_CartItem/Details?id=5
        public ActionResult Details(int CartItemId)
        {
			mcs_CartItem m = mcs_CartItemRepo.Get(CartItemId);

			return View (m);
        }
 
        //
        // GET: /mcs_CartItem/Create
        public ActionResult Create()
        {
			ViewBag.mcs_CartList = new mcs_CartRepository().List(0, 1000).ToSelectList<mcs_Cart>(nameof(mcs_Cart.CartId), nameof(mcs_Cart.CartId));
			ViewBag.mcs_ProductList = new mcs_ProductRepository().List(0, 1000).ToSelectList<mcs_Product>(nameof(mcs_Product.ProductId), nameof(mcs_Product.Name));

            return View(new Models.mcs_CartItem());
        }
 
        //
        // POST: /mcs_CartItem/Create
        [HttpPost]
        public ActionResult Create(Models.mcs_CartItem model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_CartItemRepo.Insert(model);
                    return RedirectToAction("Index");
                }
            }
            catch
            {
            }

				ViewBag.mcs_CartList = new mcs_CartRepository().List(0, 1000).ToSelectList<mcs_Cart>(nameof(mcs_Cart.CartId), nameof(mcs_Cart.CartId));
				ViewBag.mcs_ProductList = new mcs_ProductRepository().List(0, 1000).ToSelectList<mcs_Product>(nameof(mcs_Product.ProductId), nameof(mcs_Product.Name));

            return View(model);
        }
 
        //
        // GET: /mcs_CartItem/Edit?id=5
        public ActionResult Edit(int CartItemId)
        {
            try
            {
				ViewBag.mcs_CartList = new mcs_CartRepository().List(0, 1000).ToSelectList<mcs_Cart>(nameof(mcs_Cart.CartId), nameof(mcs_Cart.CartId));
				ViewBag.mcs_ProductList = new mcs_ProductRepository().List(0, 1000).ToSelectList<mcs_Product>(nameof(mcs_Product.ProductId), nameof(mcs_Product.Name));

                return View(mcs_CartItemRepo.Get(CartItemId));
            }
            catch
            {
                return View(new Models.mcs_CartItem());
            }
        }
 
        //
        // POST: /mcs_CartItem/Edit?id=5
        [HttpPost]
        public ActionResult Edit(int CartItemId, Models.mcs_CartItem model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_CartItemRepo.Update(model);
 
                    return RedirectToAction("Index");
                }
                else
                {
					ViewBag.mcs_CartList = new mcs_CartRepository().List(0, 1000).ToSelectList<mcs_Cart>(nameof(mcs_Cart.CartId), nameof(mcs_Cart.CartId));
					ViewBag.mcs_ProductList = new mcs_ProductRepository().List(0, 1000).ToSelectList<mcs_Product>(nameof(mcs_Product.ProductId), nameof(mcs_Product.Name));
                    return View(model);
                }
            }
            catch
            {
                return View(model);
            }
        }
 
        //
        // GET: /mcs_CartItem/Delete?id=5
        public ActionResult Delete(int CartItemId)
        {
            try
            {
                return View(mcs_CartItemRepo.Get(CartItemId));
            }
            catch
            {
                return View();
            }
        }
 
        //
        // POST: /mcs_CartItem/Delete?id=5
        [HttpPost]
        public ActionResult Delete(int CartItemId, Models.mcs_CartItem model)
        {
            try
            {
                mcs_CartItemRepo.Delete(CartItemId);
 
                return RedirectToAction("Index");
            }
            catch
            {
                return View(model);
            }
        }
    }
}