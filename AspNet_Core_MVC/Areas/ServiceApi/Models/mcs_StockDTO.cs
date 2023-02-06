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
    public class mcs_StockDTO
    {
		[Required(ErrorMessage = "Required")]
		public int StockId { get; set; }
		[Required(ErrorMessage = "Required")]
		public int ProductId { get; set; }
		[Required(ErrorMessage = "Required")]
		public int Quantity { get; set; }
		
		public decimal? EntryPrice { get; set; }
		
		public DateTime? EntryDate { get; set; }
        public static mcs_StockDTO ToDataTransferObject(mcs_Stock item)
        {
            if (item == null)
                return null;

            return new mcs_StockDTO
            {
				StockId = item.StockId,
				ProductId = item.ProductId,
				Quantity = item.Quantity,
				EntryPrice = item.EntryPrice,
				EntryDate = item.EntryDate,
            };
        }

        public static mcs_Stock FromDataTransferObject(mcs_StockDTO item)
        {
            if (item == null)
                return null;

            return new mcs_Stock
            {
				StockId = item.StockId,
				ProductId = item.ProductId,
				Quantity = item.Quantity,
				EntryPrice = item.EntryPrice,
				EntryDate = item.EntryDate,
            };
        }
    }
}