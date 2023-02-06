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
    public class mcs_ProductController : Controller
    {    
        mcs_ProductRepository mcs_ProductRepo;

        public mcs_ProductController()
        {
            mcs_ProductRepo = new mcs_ProductRepository();
        }

        //
        // GET: /mcs_Product/
        
        public ActionResult Index()
        {
            return View(mcs_ProductRepo.List(0, 1000));
        }
 
        //
        // GET: /mcs_Product/Details?id=5
        public ActionResult Details(int ProductId)
        {
			mcs_Product m = mcs_ProductRepo.Get(ProductId);
			m.mcs_StockList = mcs_ProductRepo.Getmcs_StockList(ProductId);
			m.mcs_CartItemList = mcs_ProductRepo.Getmcs_CartItemList(ProductId);
			m.mcs_ProductPhotoList = mcs_ProductRepo.Getmcs_ProductPhotoList(ProductId);

			return View (m);
        }
 
        //
        // GET: /mcs_Product/Create
        public ActionResult Create()
        {
			ViewBag.mcs_CategoryList = new mcs_CategoryRepository().List(0, 1000).ToSelectList<mcs_Category>(nameof(mcs_Category.CategoryId), nameof(mcs_Category.Name));

            return View(new Models.mcs_Product());
        }
 
        //
        // POST: /mcs_Product/Create
        [HttpPost]
        public ActionResult Create(Models.mcs_Product model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_ProductRepo.Insert(model);
                    return RedirectToAction("Index");
                }
            }
            catch
            {
            }

				ViewBag.mcs_CategoryList = new mcs_CategoryRepository().List(0, 1000).ToSelectList<mcs_Category>(nameof(mcs_Category.CategoryId), nameof(mcs_Category.Name));

            return View(model);
        }
 
        //
        // GET: /mcs_Product/Edit?id=5
        public ActionResult Edit(int ProductId)
        {
            try
            {
				ViewBag.mcs_CategoryList = new mcs_CategoryRepository().List(0, 1000).ToSelectList<mcs_Category>(nameof(mcs_Category.CategoryId), nameof(mcs_Category.Name));

                return View(mcs_ProductRepo.Get(ProductId));
            }
            catch
            {
                return View(new Models.mcs_Product());
            }
        }
 
        //
        // POST: /mcs_Product/Edit?id=5
        [HttpPost]
        public ActionResult Edit(int ProductId, Models.mcs_Product model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_ProductRepo.Update(model);
 
                    return RedirectToAction("Index");
                }
                else
                {
					ViewBag.mcs_CategoryList = new mcs_CategoryRepository().List(0, 1000).ToSelectList<mcs_Category>(nameof(mcs_Category.CategoryId), nameof(mcs_Category.Name));
                    return View(model);
                }
            }
            catch
            {
                return View(model);
            }
        }
 
        //
        // GET: /mcs_Product/Delete?id=5
        public ActionResult Delete(int ProductId)
        {
            try
            {
                return View(mcs_ProductRepo.Get(ProductId));
            }
            catch
            {
                return View();
            }
        }
 
        //
        // POST: /mcs_Product/Delete?id=5
        [HttpPost]
        public ActionResult Delete(int ProductId, Models.mcs_Product model)
        {
            try
            {
                mcs_ProductRepo.Delete(ProductId);
 
                return RedirectToAction("Index");
            }
            catch
            {
                return View(model);
            }
        }
    }
}