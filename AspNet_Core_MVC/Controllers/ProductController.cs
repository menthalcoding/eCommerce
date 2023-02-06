using eCommerce.Models;
using eCommerce.Repositories;
using Microsoft.AspNetCore.Mvc;
using System.Security.Cryptography.Xml;

namespace eCommerce.Controllers
{
    public class ProductController : Controller
    {
        public IActionResult Index(int? CategoryId, string? Sort, string? OrderDirection, string? Status)
        {
            ViewBag.CategoryList = (new mcs_CategoryRepository()).List(0, 1000);

            if (CategoryId == null && string.IsNullOrEmpty(Sort) && string.IsNullOrEmpty(Status))
                return View(new mcs_ProductRepository().List(0, 1000));
            else
            {
                ProductRequest productRequest = new ProductRequest();
                    productRequest.CategoryId = CategoryId;
                    productRequest.Sort = Sort;
                    productRequest.OrderDirection = OrderDirection;
                    productRequest.Status = Status;
                return View(new mcs_ProductRepository().List(productRequest, 0, 1000).GroupBy(p => p.ProductId).Select(g => g.First()).ToList());
            }
        }

        public IActionResult Detail(int id)
        {
            mcs_ProductRepository ProductRepo = new mcs_ProductRepository();
            mcs_Product product = new mcs_Product();

            product = ProductRepo.Get(id);
            if (product == null)
            { 
                product = new mcs_Product();
                product.mcs_ProductPhotoList = new List<mcs_ProductPhoto>();
            }
            else
            { 
                product.mcs_ProductPhotoList = ProductRepo.Getmcs_ProductPhotoList(id);
                List<mcs_Product> rProducts = ProductRepo.List(new ProductRequest { CategoryId = product.CategoryId }, 0, 5);
                    rProducts.Remove(rProducts.Find(p => p.ProductId == product.ProductId));
                ViewBag.RelatedProducts = rProducts;
                ViewBag.CategoryList = (new mcs_CategoryRepository()).List(0, 1000);
            }

            return View(product);
        }
    }
}
