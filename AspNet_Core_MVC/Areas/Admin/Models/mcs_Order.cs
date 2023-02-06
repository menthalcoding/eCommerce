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
    public class mcs_Order
    {
		[Required(ErrorMessage = "Required")]
		public int OrderId { get; set; }

		[Required(ErrorMessage = "Required")]
		public string UserId { get; set; }

		[Required(ErrorMessage = "Required")]
		public int CartId { get; set; }

		
		public DateTime? Created_at { get; set; }

		
		public decimal? Cost { get; set; }

		
		public decimal? Tax { get; set; }

		
		public decimal? Total { get; set; }

		
		public bool? Paid { get; set; } = false;

		
		public string? Currency { get; set; }


		public List<mcs_Shipping> mcs_ShippingList { get; set; }

        public mcs_Order()
        { 
			mcs_ShippingList = new List<mcs_Shipping>();
        }

		public mcs_Order(int OrderId, string UserId, int CartId, DateTime? Created_at, decimal? Cost, decimal? Tax, decimal? Total, bool? Paid, string? Currency)
	{
			this.OrderId = OrderId;
			this.UserId = UserId;
			this.CartId = CartId;
			this.Created_at = Created_at;
			this.Cost = Cost;
			this.Tax = Tax;
			this.Total = Total;
			this.Paid = Paid;
			this.Currency = Currency;
	}
    }
}