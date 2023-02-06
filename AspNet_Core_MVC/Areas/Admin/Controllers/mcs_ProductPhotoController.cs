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
    public class mcs_ProductPhotoController : Controller
    {    
        mcs_ProductPhotoRepository mcs_ProductPhotoRepo;

        public mcs_ProductPhotoController()
        {
            mcs_ProductPhotoRepo = new mcs_ProductPhotoRepository();
        }

        //
        // GET: /mcs_ProductPhoto/
        
        public ActionResult Index()
        {
            return View(mcs_ProductPhotoRepo.List(0, 1000));
        }
 
        //
        // GET: /mcs_ProductPhoto/Details?id=5
        public ActionResult Details(Guid ProductPhotoId)
        {
			mcs_ProductPhoto m = mcs_ProductPhotoRepo.Get(ProductPhotoId);

			return View (m);
        }
 
        //
        // GET: /mcs_ProductPhoto/Create
        public ActionResult Create()
        {
			ViewBag.mcs_ProductList = new mcs_ProductRepository().List(0, 1000).ToSelectList<mcs_Product>(nameof(mcs_Product.ProductId), nameof(mcs_Product.Name));

            return View(new Models.mcs_ProductPhoto());
        }
 
        //
        // POST: /mcs_ProductPhoto/Create
        [HttpPost]
        public ActionResult Create(Models.mcs_ProductPhoto model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_ProductPhotoRepo.Insert(model);
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
        // GET: /mcs_ProductPhoto/Edit?id=5
        public ActionResult Edit(Guid ProductPhotoId)
        {
            try
            {
				ViewBag.mcs_ProductList = new mcs_ProductRepository().List(0, 1000).ToSelectList<mcs_Product>(nameof(mcs_Product.ProductId), nameof(mcs_Product.Name));

                return View(mcs_ProductPhotoRepo.Get(ProductPhotoId));
            }
            catch
            {
                return View(new Models.mcs_ProductPhoto());
            }
        }
 
        //
        // POST: /mcs_ProductPhoto/Edit?id=5
        [HttpPost]
        public ActionResult Edit(Guid ProductPhotoId, Models.mcs_ProductPhoto model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_ProductPhotoRepo.Update(model);
 
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
        // GET: /mcs_ProductPhoto/Delete?id=5
        public ActionResult Delete(Guid ProductPhotoId)
        {
            try
            {
                return View(mcs_ProductPhotoRepo.Get(ProductPhotoId));
            }
            catch
            {
                return View();
            }
        }
 
        //
        // POST: /mcs_ProductPhoto/Delete?id=5
        [HttpPost]
        public ActionResult Delete(Guid ProductPhotoId, Models.mcs_ProductPhoto model)
        {
            try
            {
                mcs_ProductPhotoRepo.Delete(ProductPhotoId);
 
                return RedirectToAction("Index");
            }
            catch
            {
                return View(model);
            }
        }
    }
}