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
    public class mcs_PaymentController : Controller
    {    
        mcs_PaymentRepository mcs_PaymentRepo;

        public mcs_PaymentController()
        {
            mcs_PaymentRepo = new mcs_PaymentRepository();
        }

        //
        // GET: /mcs_Payment/
        
        public ActionResult Index()
        {
            return View(mcs_PaymentRepo.List(0, 1000));
        }
 
        //
        // GET: /mcs_Payment/Details?id=5
        public ActionResult Details(int PaymentId)
        {
			mcs_Payment m = mcs_PaymentRepo.Get(PaymentId);

			return View (m);
        }
 
        //
        // GET: /mcs_Payment/Create
        public ActionResult Create()
        {

            return View(new Models.mcs_Payment());
        }
 
        //
        // POST: /mcs_Payment/Create
        [HttpPost]
        public ActionResult Create(Models.mcs_Payment model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_PaymentRepo.Insert(model);
                    return RedirectToAction("Index");
                }
            }
            catch
            {
            }


            return View(model);
        }
 
        //
        // GET: /mcs_Payment/Edit?id=5
        public ActionResult Edit(int PaymentId)
        {
            try
            {

                return View(mcs_PaymentRepo.Get(PaymentId));
            }
            catch
            {
                return View(new Models.mcs_Payment());
            }
        }
 
        //
        // POST: /mcs_Payment/Edit?id=5
        [HttpPost]
        public ActionResult Edit(int PaymentId, Models.mcs_Payment model)
        {
            try
            {
                if (ModelState.IsValid) 
                {
                    mcs_PaymentRepo.Update(model);
 
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
        // GET: /mcs_Payment/Delete?id=5
        public ActionResult Delete(int PaymentId)
        {
            try
            {
                return View(mcs_PaymentRepo.Get(PaymentId));
            }
            catch
            {
                return View();
            }
        }
 
        //
        // POST: /mcs_Payment/Delete?id=5
        [HttpPost]
        public ActionResult Delete(int PaymentId, Models.mcs_Payment model)
        {
            try
            {
                mcs_PaymentRepo.Delete(PaymentId);
 
                return RedirectToAction("Index");
            }
            catch
            {
                return View(model);
            }
        }
    }
}