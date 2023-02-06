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
    public class mcs_Product
    {
		[Required(ErrorMessage = "Required")]
		public int ProductId { get; set; }

		[Required(ErrorMessage = "Required")]
		public string Name { get; set; }

		
		public string? Title { get; set; }

		
		public string? Description { get; set; }

		[Required(ErrorMessage = "Required")]
		public int CategoryId { get; set; }

		
		public decimal Price { get; set; }

		
		public string? DefaultPhoto { get; set; }

		
		public string? Status { get; set; }

		
		public DateTime Created_at { get; set; }

		
		public DateTime? Updated_at { get; set; }


		public List<mcs_Stock> mcs_StockList { get; set; }
		public List<mcs_CartItem> mcs_CartItemList { get; set; }
		public List<mcs_ProductPhoto> mcs_ProductPhotoList { get; set; }

        public mcs_Product()
        { 
			mcs_StockList = new List<mcs_Stock>();
			mcs_CartItemList = new List<mcs_CartItem>();
			mcs_ProductPhotoList = new List<mcs_ProductPhoto>();
        }

		public mcs_Product(int ProductId, string Name, string? Title, string? Description, int CategoryId, decimal Price, string? DefaultPhoto, string? Status, DateTime Created_at, DateTime? Updated_at)
	{
			this.ProductId = ProductId;
			this.Name = Name;
			this.Title = Title;
			this.Description = Description;
			this.CategoryId = CategoryId;
			this.Price = Price;
			this.DefaultPhoto = DefaultPhoto;
			this.Status = Status;
			this.Created_at = Created_at;
			this.Updated_at = Updated_at;
	}
    }
}