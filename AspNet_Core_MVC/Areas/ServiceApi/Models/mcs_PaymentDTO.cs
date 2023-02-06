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
    public class mcs_PaymentDTO
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
        public static mcs_PaymentDTO ToDataTransferObject(mcs_Payment item)
        {
            if (item == null)
                return null;

            return new mcs_PaymentDTO
            {
				PaymentId = item.PaymentId,
				UserId = item.UserId,
				PaymentType = item.PaymentType,
				Created_at = item.Created_at,
				Amount = item.Amount,
				Currency = item.Currency,
				OrderId = item.OrderId,
				AddressId = item.AddressId,
            };
        }

        public static mcs_Payment FromDataTransferObject(mcs_PaymentDTO item)
        {
            if (item == null)
                return null;

            return new mcs_Payment
            {
				PaymentId = item.PaymentId,
				UserId = item.UserId,
				PaymentType = item.PaymentType,
				Created_at = item.Created_at,
				Amount = item.Amount,
				Currency = item.Currency,
				OrderId = item.OrderId,
				AddressId = item.AddressId,
            };
        }
    }
}