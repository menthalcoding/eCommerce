///////////////////////////////////////
// Author: Orhan Erdebil
// CreatedDate: 6 Şubat 2023 Pazartesi
// License terms are specified in the "license.txt" file in the root directory.
///////////////////////////////////////

using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations; 

namespace eCommerce.Models 
{
    public class mcs_CartItem
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



        public mcs_CartItem()
        { 
        }

		public mcs_CartItem(int CartItemId, int CartId, int ProductId, int? Quantity, DateTime? Created_at, DateTime? Updated_at)
	{
			this.CartItemId = CartItemId;
			this.CartId = CartId;
			this.ProductId = ProductId;
			this.Quantity = Quantity;
			this.Created_at = Created_at;
			this.Updated_at = Updated_at;
	}
    }
}