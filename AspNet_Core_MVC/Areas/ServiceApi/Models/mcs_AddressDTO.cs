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
    public class mcs_AddressDTO
    {
		[Required(ErrorMessage = "Required")]
		public int AddressId { get; set; }
		[Required(ErrorMessage = "Required")]
		public string UserId { get; set; }
		
		public string? Name { get; set; }
		
		public string? GPS { get; set; }
		
		public string? Country { get; set; }
		
		public string? City { get; set; }
		
		public string? ZipCode { get; set; }
		
		public string? CountryCode { get; set; }
		
		public string? Detail { get; set; }
		
		public bool? Primary { get; set; }
		
		public bool? Active { get; set; }
        public static mcs_AddressDTO ToDataTransferObject(mcs_Address item)
        {
            if (item == null)
                return null;

            return new mcs_AddressDTO
            {
				AddressId = item.AddressId,
				UserId = item.UserId,
				Name = item.Name,
				GPS = item.GPS,
				Country = item.Country,
				City = item.City,
				ZipCode = item.ZipCode,
				CountryCode = item.CountryCode,
				Detail = item.Detail,
				Primary = item.Primary,
				Active = item.Active,
            };
        }

        public static mcs_Address FromDataTransferObject(mcs_AddressDTO item)
        {
            if (item == null)
                return null;

            return new mcs_Address
            {
				AddressId = item.AddressId,
				UserId = item.UserId,
				Name = item.Name,
				GPS = item.GPS,
				Country = item.Country,
				City = item.City,
				ZipCode = item.ZipCode,
				CountryCode = item.CountryCode,
				Detail = item.Detail,
				Primary = item.Primary,
				Active = item.Active,
            };
        }
    }
}