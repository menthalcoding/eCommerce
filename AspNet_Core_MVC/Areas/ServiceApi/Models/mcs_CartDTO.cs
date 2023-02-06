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
    public class mcs_CartDTO
    {
		[Required(ErrorMessage = "Required")]
		public int CartId { get; set; }
		[Required(ErrorMessage = "Required")]
		public string UserId { get; set; }
		
		public string Status { get; set; }
		
		public DateTime Created_at { get; set; }
		
		public DateTime Updated_at { get; set; }
        public static mcs_CartDTO ToDataTransferObject(mcs_Cart item)
        {
            if (item == null)
                return null;

            return new mcs_CartDTO
            {
				CartId = item.CartId,
				UserId = item.UserId,
				Status = item.Status,
				Created_at = item.Created_at,
				Updated_at = item.Updated_at,
            };
        }

        public static mcs_Cart FromDataTransferObject(mcs_CartDTO item)
        {
            if (item == null)
                return null;

            return new mcs_Cart
            {
				CartId = item.CartId,
				UserId = item.UserId,
				Status = item.Status,
				Created_at = item.Created_at,
				Updated_at = item.Updated_at,
            };
        }
    }
}