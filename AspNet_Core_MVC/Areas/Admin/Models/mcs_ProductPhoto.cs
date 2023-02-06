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
    public class mcs_ProductPhoto
    {
		[Required(ErrorMessage = "Required")]
		public Guid ProductPhotoId { get; set; }

		[Required(ErrorMessage = "Required")]
		public int ProductId { get; set; }

		[Required(ErrorMessage = "Required")]
		public string Url { get; set; }



        public mcs_ProductPhoto()
        { 
        }

		public mcs_ProductPhoto(Guid ProductPhotoId, int ProductId, string Url)
	{
			this.ProductPhotoId = ProductPhotoId;
			this.ProductId = ProductId;
			this.Url = Url;
	}
    }
}