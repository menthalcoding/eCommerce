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
    public class mcs_Stock
    {
		[Required(ErrorMessage = "Required")]
		public int StockId { get; set; }

		[Required(ErrorMessage = "Required")]
		public int ProductId { get; set; }

		[Required(ErrorMessage = "Required")]
		public int Quantity { get; set; }

		
		public decimal? EntryPrice { get; set; }

		
		public DateTime? EntryDate { get; set; }



        public mcs_Stock()
        { 
        }

		public mcs_Stock(int StockId, int ProductId, int Quantity, decimal? EntryPrice, DateTime? EntryDate)
	{
			this.StockId = StockId;
			this.ProductId = ProductId;
			this.Quantity = Quantity;
			this.EntryPrice = EntryPrice;
			this.EntryDate = EntryDate;
	}
    }
}