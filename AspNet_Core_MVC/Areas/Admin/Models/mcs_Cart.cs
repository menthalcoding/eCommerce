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
    public class mcs_Cart
    {
		[Required(ErrorMessage = "Required")]
		public int CartId { get; set; }

		[Required(ErrorMessage = "Required")]
		public string UserId { get; set; }

		
		public string Status { get; set; }

		
		public DateTime Created_at { get; set; }

		
		public DateTime Updated_at { get; set; }


		public List<mcs_CartItem> mcs_CartItemList { get; set; }
		public List<mcs_Order> mcs_OrderList { get; set; }

        public mcs_Cart()
        { 
			mcs_CartItemList = new List<mcs_CartItem>();
			mcs_OrderList = new List<mcs_Order>();
        }

		public mcs_Cart(int CartId, string UserId, string Status, DateTime Created_at, DateTime Updated_at)
	{
			this.CartId = CartId;
			this.UserId = UserId;
			this.Status = Status;
			this.Created_at = Created_at;
			this.Updated_at = Updated_at;
	}
    }
}