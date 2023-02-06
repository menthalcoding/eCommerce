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
    public class mcs_ProductDTO
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
        public static mcs_ProductDTO ToDataTransferObject(mcs_Product item)
        {
            if (item == null)
                return null;

            return new mcs_ProductDTO
            {
				ProductId = item.ProductId,
				Name = item.Name,
				Title = item.Title,
				Description = item.Description,
				CategoryId = item.CategoryId,
				Price = item.Price,
				DefaultPhoto = item.DefaultPhoto,
				Status = item.Status,
				Created_at = item.Created_at,
				Updated_at = item.Updated_at,
            };
        }

        public static mcs_Product FromDataTransferObject(mcs_ProductDTO item)
        {
            if (item == null)
                return null;

            return new mcs_Product
            {
				ProductId = item.ProductId,
				Name = item.Name,
				Title = item.Title,
				Description = item.Description,
				CategoryId = item.CategoryId,
				Price = item.Price,
				DefaultPhoto = item.DefaultPhoto,
				Status = item.Status,
				Created_at = item.Created_at,
				Updated_at = item.Updated_at,
            };
        }
    }
}