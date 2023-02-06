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
    public class mcs_CategoryController : Controller
    {    
        mcs_CategoryRepository mcs_CategoryRepo;

        public mcs_CategoryController()
        {
            mcs_CategoryRepo = new mcs_CategoryRepository();
        }

        //
        // GET: /mcs_Category/
        
        public ActionResult Index()
        {
            return View(mcs_CategoryRepo.List(0, 1000));
        }
 
        //
        // GET: /mcs_Category/Details?id=5
        public ActionResult Details(int CategoryId)
        {
			mcs_Category m = mcs_CategoryRepo.Get(CategoryId);
			m.mcs_ProductList = mcs_CategoryRepo.Getmcs_ProductList(CategoryId);
			m.mcs_CategoryList = mcs_CategoryRepo.Getmcs_CategoryList(CategoryId);

			return View (m);
        }
 
        //
        // GET: /mcs_Category/Create
        public ActionResult Create()
        {
			ViewBag.mcs_CategoryList = new mcs_CategoryRepository().List(0, 1000).ToSelectList<mcs_Category>(nameof(mcs_Category.CategoryId), nameof(mcs_Category.Name));

            return View(new Models.mcs_Category());
        }
 
        //
        // POST: /mcs_Category/Create
        [HttpPost]
        public ActionResult Create(Models.mcs_Category model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_CategoryRepo.Insert(model);
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
        // GET: /mcs_Category/Edit?id=5
        public ActionResult Edit(int CategoryId)
        {
            try
            {
				ViewBag.mcs_CategoryList = new mcs_CategoryRepository().List(0, 1000).ToSelectList<mcs_Category>(nameof(mcs_Category.CategoryId), nameof(mcs_Category.Name));

                return View(mcs_CategoryRepo.Get(CategoryId));
            }
            catch
            {
                return View(new Models.mcs_Category());
            }
        }
 
        //
        // POST: /mcs_Category/Edit?id=5
        [HttpPost]
        public ActionResult Edit(int CategoryId, Models.mcs_Category model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_CategoryRepo.Update(model);
 
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
        // GET: /mcs_Category/Delete?id=5
        public ActionResult Delete(int CategoryId)
        {
            try
            {
                return View(mcs_CategoryRepo.Get(CategoryId));
            }
            catch
            {
                return View();
            }
        }
 
        //
        // POST: /mcs_Category/Delete?id=5
        [HttpPost]
        public ActionResult Delete(int CategoryId, Models.mcs_Category model)
        {
            try
            {
                mcs_CategoryRepo.Delete(CategoryId);
 
                return RedirectToAction("Index");
            }
            catch
            {
                return View(model);
            }
        }
    }
}