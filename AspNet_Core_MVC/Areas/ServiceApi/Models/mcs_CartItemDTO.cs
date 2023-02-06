///////////////////////////////////////
// Author: Orhan Erdebil
// CreatedDate: 6 Şubat 2023 Pazartesi
// License terms are specified in the "license.txt" file in the root directory.
///////////////////////////////////////

using Microsoft.AspNetCore.Mvc;
using System.ComponentModel.DataAnnotations;
using eCommerce.Models;

namespace eCommerce.WebApi.Models
{
    // Remove the fields you do not want to be served
    public class mcs_CartItemDTO
    {
		[Required(ErrorMessage = "Required")]
		public int CartItemId { get; set; }
		[Required(ErrorMessage = "Required")]
		public int CartId { get; set; }
		[Required(ErrorMessage = "Required")]
		public int ProductId { get; set; }
		
		public int? Quantity { get; set; }
		
		public DateTime? Created_at { get; set; }
		
		public DateTime? Updated_at { get; set; }
        public static mcs_CartItemDTO ToDataTransferObject(mcs_CartItem item)
        {
            if (item == null)
                return null;

            return new mcs_CartItemDTO
            {
				CartItemId = item.CartItemId,
				CartId = item.CartId,
				ProductId = item.ProductId,
				Quantity = item.Quantity,
				Created_at = item.Created_at,
				Updated_at = item.Updated_at,
            };
        }

        public static mcs_CartItem FromDataTransferObject(mcs_CartItemDTO item)
        {
            if (item == null)
                return null;

            return new mcs_CartItem
            {
				CartItemId = item.CartItemId,
				CartId = item.CartId,
				ProductId = item.ProductId,
				Quantity = item.Quantity,
				Created_at = item.Created_at,
				Updated_at = item.Updated_at,
            };
        }
    }
}