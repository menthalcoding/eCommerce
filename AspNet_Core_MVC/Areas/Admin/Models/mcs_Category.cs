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
    public class mcs_Category
    {
		[Required(ErrorMessage = "Required")]
		public int CategoryId { get; set; }

		public int? CategoryParentId { get; set; }

		[Required(ErrorMessage = "Required")]
		public string Name { get; set; }

		public string? Description { get; set; }


		public List<mcs_Product> mcs_ProductList { get; set; }
		public List<mcs_Category> mcs_CategoryList { get; set; }

        public mcs_Category()
        { 
			mcs_ProductList = new List<mcs_Product>();
			mcs_CategoryList = new List<mcs_Category>();
        }

		public mcs_Category(int CategoryId, int? CategoryParentId, string Name, string? Description)
	{
			this.CategoryId = CategoryId;
			this.CategoryParentId = CategoryParentId;
			this.Name = Name;
			this.Description = Description;
	}
    }
}