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
    public class mcs_Payment
    {
		[Required(ErrorMessage = "Required")]
		public int PaymentId { get; set; }

		[Required(ErrorMessage = "Required")]
		public string UserId { get; set; }

		
		public string? PaymentType { get; set; }

		
		public DateTime? Created_at { get; set; }

		
		public decimal? Amount { get; set; }

		
		public string? Currency { get; set; }

		[Required(ErrorMessage = "Required")]
		public int OrderId { get; set; }

		[Required(ErrorMessage = "Required")]
		public int AddressId { get; set; }



        public mcs_Payment()
        { 
        }

		public mcs_Payment(int PaymentId, string UserId, string? PaymentType, DateTime? Created_at, decimal? Amount, string? Currency, int OrderId, int AddressId)
	{
			this.PaymentId = PaymentId;
			this.UserId = UserId;
			this.PaymentType = PaymentType;
			this.Created_at = Created_at;
			this.Amount = Amount;
			this.Currency = Currency;
			this.OrderId = OrderId;
			this.AddressId = AddressId;
	}
    }
}