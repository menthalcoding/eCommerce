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
    public class mcs_Shipping
    {
		[Required(ErrorMessage = "Required")]
		public int ShippingId { get; set; }

		[Required(ErrorMessage = "Required")]
		public int OrderId { get; set; }

		[Required(ErrorMessage = "Required")]
		public int AddressId { get; set; }

		
		public string? ShippingMethod { get; set; }

		
		public string? Status { get; set; }

		
		public string? ShippingProvider { get; set; }

		
		public decimal? Cost { get; set; }



        public mcs_Shipping()
        { 
        }

		public mcs_Shipping(int ShippingId, int OrderId, int AddressId, string? ShippingMethod, string? Status, string? ShippingProvider, decimal? Cost)
	{
			this.ShippingId = ShippingId;
			this.OrderId = OrderId;
			this.AddressId = AddressId;
			this.ShippingMethod = ShippingMethod;
			this.Status = Status;
			this.ShippingProvider = ShippingProvider;
			this.Cost = Cost;
	}
    }
}