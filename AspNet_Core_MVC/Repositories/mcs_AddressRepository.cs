///////////////////////////////////////
// Author: Orhan Erdebil
// CreatedDate: 6 Şubat 2023 Pazartesi
// License terms are specified in the "license.txt" file in the root directory.
///////////////////////////////////////
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using eCommerce.Models;
using OnlineCoding.Code.Database;
using OnlineCoding.Code.Helper;

namespace eCommerce.Repositories
{
    internal class mcs_AddressRepository //: IRepository_mcs_Address
    {
        #region Imcs_AddressDao Members

        internal int Insert(mcs_Address data)
        {
            string sql = @"exec sp_mcs_Address_Insert @AddressId, @UserId, @Name, @GPS, @Country, @City, @ZipCode, @CountryCode, @Detail, @Primary, @Active"; 

            return Db.Insert(sql, Take(data)); 
        }

        internal mcs_Address Get(int AddressId)
        {
            string sql = @"exec sp_mcs_Address_Select @AddressId";

            return Db.Read(sql, Make, new object[] { "@AddressId", AddressId }); 
        }

		internal List<mcs_Shipping> Getmcs_ShippingList(int AddressId)
		{
			string sql = @"exec sp_mcs_Address_mcs_Shipping_List @AddressId";

			return Db.ReadList(sql, mcs_ShippingRepository.Make, new object[] { "@AddressId", AddressId });
		}

        internal List<mcs_Address> List(int startIndex, int itemCount)
        {
            string sql = @"exec sp_mcs_Address_List @startIndex, @itemCount";

            return Db.ReadList(sql, Make, new object[] { "@startIndex", startIndex, "@itemCount", itemCount }); 
        }

        internal List<mcs_Address> List(string UserId, int startIndex, int itemCount)
        {
            string sql = @"exec sp_mcs_Address_List @startIndex, @itemCount, @UserId";

            return Db.ReadList(sql, Make, new object[] { "@startIndex", startIndex, "@itemCount", itemCount, "@UserId", UserId });
        }
        
        internal void Update(mcs_Address data)
        {
            string sql = @"exec sp_mcs_Address_Update @AddressId, @UserId, @Name, @GPS, @Country, @City, @ZipCode, @CountryCode, @Detail, @Primary, @Active";

            Db.Update(sql, Take(data));
        }

        internal void Delete(string UserId, int AddressId)
        {
            string sql = @"exec sp_mcs_Address_Delete @UserId, @AddressId";

            Db.Delete(sql, new object[] { "@UserId", UserId, "@AddressId", AddressId});
        }
        
        internal void Delete(int AddressId)
        {
            string sql = @"exec sp_mcs_Address_Delete @AddressId";

            Db.Delete(sql, new object[] { "@AddressId", AddressId });
        }

        #endregion
        
        /// <summary>
        /// Creates a mcs_Address object based on DataReader.
        /// </summary>
        internal static Func<IDataReader, mcs_Address> Make = reader =>
           new mcs_Address
           {
			AddressId = DbHelper.ConvertFromDBVal<int>(reader["AddressId"]),
			UserId = DbHelper.ConvertFromDBVal<string>(reader["UserId"]),
			Name = DbHelper.ConvertFromDBVal<string>(reader["Name"]),
			GPS = DbHelper.ConvertFromDBVal<string>(reader["GPS"]),
			Country = DbHelper.ConvertFromDBVal<string>(reader["Country"]),
			City = DbHelper.ConvertFromDBVal<string>(reader["City"]),
			ZipCode = DbHelper.ConvertFromDBVal<string>(reader["ZipCode"]),
			CountryCode = DbHelper.ConvertFromDBVal<string>(reader["CountryCode"]),
			Detail = DbHelper.ConvertFromDBVal<string>(reader["Detail"]),
			Primary = DbHelper.ConvertFromDBVal<bool>(reader["Primary"]),
			Active = DbHelper.ConvertFromDBVal<bool>(reader["Active"])
           };

        /// <summary>
        /// Creates query parameters list from mcs_Address object
        /// </summary>
        /// <param name="mcs_Address">mcs_Address.</param>
        /// <returns>Name value parameter list.</returns>
        private object[] Take(mcs_Address data)
        {
            return new object[]  
            {
			"@AddressId", data.AddressId,
			"@UserId", data.UserId,
			"@Name", data.Name,
			"@GPS", data.GPS,
			"@Country", data.Country,
			"@City", data.City,
			"@ZipCode", data.ZipCode,
			"@CountryCode", data.CountryCode,
			"@Detail", data.Detail,
			"@Primary", data.Primary,
			"@Active", data.Active
            };
        }
    }
}
