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
    public class mcs_OrderDTO
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
		
		public bool? Paid { get; set; }
		
		public string? Currency { get; set; }
        public static mcs_OrderDTO ToDataTransferObject(mcs_Order item)
        {
            if (item == null)
                return null;

            return new mcs_OrderDTO
            {
				OrderId = item.OrderId,
				UserId = item.UserId,
				CartId = item.CartId,
				Created_at = item.Created_at,
				Cost = item.Cost,
				Tax = item.Tax,
				Total = item.Total,
				Paid = item.Paid,
				Currency = item.Currency,
            };
        }

        public static mcs_Order FromDataTransferObject(mcs_OrderDTO item)
        {
            if (item == null)
                return null;

            return new mcs_Order
            {
				OrderId = item.OrderId,
				UserId = item.UserId,
				CartId = item.CartId,
				Created_at = item.Created_at,
				Cost = item.Cost,
				Tax = item.Tax,
				Total = item.Total,
				Paid = item.Paid,
				Currency = item.Currency,
            };
        }
    }
}