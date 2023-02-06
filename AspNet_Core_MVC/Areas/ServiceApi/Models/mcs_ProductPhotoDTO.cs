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
    public class mcs_ProductPhotoDTO
    {
		[Required(ErrorMessage = "Required")]
		public Guid ProductPhotoId { get; set; }
		[Required(ErrorMessage = "Required")]
		public int ProductId { get; set; }
		[Required(ErrorMessage = "Required")]
		public string Url { get; set; }
        public static mcs_ProductPhotoDTO ToDataTransferObject(mcs_ProductPhoto item)
        {
            if (item == null)
                return null;

            return new mcs_ProductPhotoDTO
            {
				ProductPhotoId = item.ProductPhotoId,
				ProductId = item.ProductId,
				Url = item.Url,
            };
        }

        public static mcs_ProductPhoto FromDataTransferObject(mcs_ProductPhotoDTO item)
        {
            if (item == null)
                return null;

            return new mcs_ProductPhoto
            {
				ProductPhotoId = item.ProductPhotoId,
				ProductId = item.ProductId,
				Url = item.Url,
            };
        }
    }
}