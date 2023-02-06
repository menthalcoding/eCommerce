using eCommerce.Models;
using eCommerce.Repositories;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using OnlineCoding.Code.Helper;
using System.Security.Claims;

namespace eCommerce.Controllers
{
    public class BasketController : Controller
    {
        public IActionResult Index()
        {
            Basket basket = HttpContext.Session.Get<Basket>("basket");

            return View(basket);
        }

        [Authorize]
        public IActionResult Purchase()
        {            
            Basket basket = HttpContext.Session.Get<Basket>("basket");
            if (basket != null)
            {
                string userId = User.FindFirstValue(ClaimTypes.NameIdentifier);
                mcs_CartRepository cartRepo = new mcs_CartRepository();
                mcs_CartItemRepository cartItemRepo = new mcs_CartItemRepository();
                List<mcs_CartItem> cartItems = new List<mcs_CartItem>();

                if (basket.CartId == 0)
                {
                    basket.CartId = cartRepo.Insert(new mcs_Cart
                    {
                        UserId = userId,
                        Status = "Open",
                        Created_at = DateTime.Now,
                        Updated_at = DateTime.Now
                    });
                }
                else 
                {
                    cartItems = cartRepo.Getmcs_CartItemList(basket.CartId);
                }
                if (basket.CartId > 0)
                {
                    foreach (BasketItem item in basket.BasketItemList)
                    {
                        if (cartItems.Exists(i => i.ProductId == item.ProductId))
                        {
                            mcs_CartItem cItem = cartItems.Find(i => i.ProductId == item.ProductId);
                            cartItemRepo.Update(new mcs_CartItem
                            {
                                CartItemId = cItem.CartItemId,
                                CartId = basket.CartId,
                                ProductId = item.ProductId,
                                Quantity = item.Quantity,
                                Updated_at = DateTime.Now
                            });
                            cartItems.Remove(cItem);
                        }
                        else
                        { 
                            cartItemRepo.Insert(new mcs_CartItem { 
                                CartId = basket.CartId, 
                                ProductId = item.ProductId, 
                                Quantity = item.Quantity, 
                                Created_at = DateTime.Now, 
                                Updated_at = DateTime.Now });
                        }
                    }
                    if (cartItems.Count > 0)
                    {
                        cartItems.ForEach(i => {
                            cartItemRepo.Delete(i.CartItemId);
                        });
                    }
                    HttpContext.Session.Set<Basket>("basket", basket);
                }
            }

            return View();
        }

        [HttpPost]
        public IActionResult AddToBasket([FromBody] BasketItem item)
        {
            Basket basket = HttpContext.Session.Get<Basket>("basket");

            if (basket == null)
                basket = new Basket();

            if (basket.BasketItemList == null)
                basket.BasketItemList = new List<BasketItem>();

            if (basket.BasketItemList.Exists(c => c.ProductId == item.ProductId))
                basket.BasketItemList.Find(c => c.ProductId == item.ProductId).Quantity += item.Quantity;
            else
            {
                mcs_ProductRepository productRepository = new mcs_ProductRepository();
                mcs_Product product = productRepository.Get(item.ProductId);
                if (product != null)
                {
                    basket.BasketItemList.Add(new BasketItem
                    {
                        ProductId = item.ProductId,
                        Name = product.Name,
                        Description = product.Description,
                        Price = product.Price,
                        ImageUri = product.DefaultPhoto,
                        Quantity = item.Quantity
                    });
                }
            }

            HttpContext.Session.Set<Basket>("basket", basket);

            return Ok(basket.BasketItemList.Count);
        }

        [HttpPost]
        public IActionResult ChangeQuantity([FromBody] BasketItemQuantity item)
        {
            Basket basket = HttpContext.Session.Get<Basket>("basket");

            if (basket != null && basket.BasketItemList != null)
            {
                BasketItem bItem = basket.BasketItemList.Find(c => c.ProductId == item.ProductId);
                if (bItem != null)
                {
                    bItem.Quantity = item.Quantity;
                    HttpContext.Session.Set<Basket>("basket", basket);

                    return Ok(bItem.Price);
                }
            }

            return BadRequest(false);
        }

        [HttpPost]
        public IActionResult BasketItemCount()
        {
            Basket basket = HttpContext.Session.Get<Basket>("basket");
            if (basket != null && basket.BasketItemList != null)
            {
                return Ok(basket.BasketItemList.Count);
            }

            return Ok(0);
        }

        [HttpPost]
        public IActionResult TotalAmount()
        {
            Basket basket = HttpContext.Session.Get<Basket>("basket");
            if (basket != null && basket.BasketItemList != null)
            {
                decimal amount = 0;
                foreach (BasketItem item in basket.BasketItemList)
                {
                    amount += item.Price * item.Quantity;
                }
                return Ok(amount);
            }

            return Ok(0);
        }

        [HttpPost]
        public IActionResult RemoveCartItem([FromBody]int productId)
        {
            Basket basket = HttpContext.Session.Get<Basket>("basket");
            if (basket != null && basket.BasketItemList != null)
            {
                BasketItem item = basket.BasketItemList.Find(i => i.ProductId == productId);
                if (item != null)
                {
                    basket.BasketItemList.Remove(item);
                    HttpContext.Session.Set<Basket>("basket", basket);
                    
                    return Ok(true);
                }
            }

            return BadRequest(false);
        }
    }
}
