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
    public class mcs_CategoryDTO
    {
		[Required(ErrorMessage = "Required")]
		public int CategoryId { get; set; }
		public int? CategoryParentId { get; set; }
		[Required(ErrorMessage = "Required")]
		public string Name { get; set; }
		public string? Description { get; set; }
        public static mcs_CategoryDTO ToDataTransferObject(mcs_Category item)
        {
            if (item == null)
                return null;

            return new mcs_CategoryDTO
            {
				CategoryId = item.CategoryId,
				CategoryParentId = item.CategoryParentId,
				Name = item.Name,
				Description = item.Description,
            };
        }

        public static mcs_Category FromDataTransferObject(mcs_CategoryDTO item)
        {
            if (item == null)
                return null;

            return new mcs_Category
            {
				CategoryId = item.CategoryId,
				CategoryParentId = item.CategoryParentId,
				Name = item.Name,
				Description = item.Description,
            };
        }
    }
}