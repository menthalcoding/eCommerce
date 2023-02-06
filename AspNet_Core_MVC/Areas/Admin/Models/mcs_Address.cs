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
    public class mcs_Address
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

		
		public bool? Primary { get; set; } = false;

		
		public bool? Active { get; set; } = false;


		public List<mcs_Shipping> mcs_ShippingList { get; set; }

        public mcs_Address()
        { 
			mcs_ShippingList = new List<mcs_Shipping>();
        }

		public mcs_Address(int AddressId, string UserId, string? Name, string? GPS, string? Country, string? City, string? ZipCode, string? CountryCode, string? Detail, bool? Primary, bool? Active)
	{
			this.AddressId = AddressId;
			this.UserId = UserId;
			this.Name = Name;
			this.GPS = GPS;
			this.Country = Country;
			this.City = City;
			this.ZipCode = ZipCode;
			this.CountryCode = CountryCode;
			this.Detail = Detail;
			this.Primary = Primary;
			this.Active = Active;
	}
    }
}