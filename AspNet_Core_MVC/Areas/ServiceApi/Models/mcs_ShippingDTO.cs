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
    public class mcs_ShippingDTO
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
        public static mcs_ShippingDTO ToDataTransferObject(mcs_Shipping item)
        {
            if (item == null)
                return null;

            return new mcs_ShippingDTO
            {
				ShippingId = item.ShippingId,
				OrderId = item.OrderId,
				AddressId = item.AddressId,
				ShippingMethod = item.ShippingMethod,
				Status = item.Status,
				ShippingProvider = item.ShippingProvider,
				Cost = item.Cost,
            };
        }

        public static mcs_Shipping FromDataTransferObject(mcs_ShippingDTO item)
        {
            if (item == null)
                return null;

            return new mcs_Shipping
            {
				ShippingId = item.ShippingId,
				OrderId = item.OrderId,
				AddressId = item.AddressId,
				ShippingMethod = item.ShippingMethod,
				Status = item.Status,
				ShippingProvider = item.ShippingProvider,
				Cost = item.Cost,
            };
        }
    }
}