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
    public class mcs_StockController : Controller
    {    
        mcs_StockRepository mcs_StockRepo;

        public mcs_StockController()
        {
            mcs_StockRepo = new mcs_StockRepository();
        }

        //
        // GET: /mcs_Stock/
        
        public ActionResult Index()
        {
            return View(mcs_StockRepo.List(0, 1000));
        }
 
        //
        // GET: /mcs_Stock/Details?id=5
        public ActionResult Details(int StockId)
        {
			mcs_Stock m = mcs_StockRepo.Get(StockId);

			return View (m);
        }
 
        //
        // GET: /mcs_Stock/Create
        public ActionResult Create()
        {
			ViewBag.mcs_ProductList = new mcs_ProductRepository().List(0, 1000).ToSelectList<mcs_Product>(nameof(mcs_Product.ProductId), nameof(mcs_Product.Name));

            return View(new Models.mcs_Stock());
        }
 
        //
        // POST: /mcs_Stock/Create
        [HttpPost]
        public ActionResult Create(Models.mcs_Stock model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_StockRepo.Insert(model);
                    return RedirectToAction("Index");
                }
            }
            catch
            {
            }

				ViewBag.mcs_ProductList = new mcs_ProductRepository().List(0, 1000).ToSelectList<mcs_Product>(nameof(mcs_Product.ProductId), nameof(mcs_Product.Name));

            return View(model);
        }
 
        //
        // GET: /mcs_Stock/Edit?id=5
        public ActionResult Edit(int StockId)
        {
            try
            {
				ViewBag.mcs_ProductList = new mcs_ProductRepository().List(0, 1000).ToSelectList<mcs_Product>(nameof(mcs_Product.ProductId), nameof(mcs_Product.Name));

                return View(mcs_StockRepo.Get(StockId));
            }
            catch
            {
                return View(new Models.mcs_Stock());
            }
        }
 
        //
        // POST: /mcs_Stock/Edit?id=5
        [HttpPost]
        public ActionResult Edit(int StockId, Models.mcs_Stock model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_StockRepo.Update(model);
 
                    return RedirectToAction("Index");
                }
                else
                {
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
        // GET: /mcs_Stock/Delete?id=5
        public ActionResult Delete(int StockId)
        {
            try
            {
                return View(mcs_StockRepo.Get(StockId));
            }
            catch
            {
                return View();
            }
        }
 
        //
        // POST: /mcs_Stock/Delete?id=5
        [HttpPost]
        public ActionResult Delete(int StockId, Models.mcs_Stock model)
        {
            try
            {
                mcs_StockRepo.Delete(StockId);
 
                return RedirectToAction("Index");
            }
            catch
            {
                return View(model);
            }
        }
    }
}