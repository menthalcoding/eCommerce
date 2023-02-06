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
    internal class mcs_ShippingRepository //: IRepository_mcs_Shipping
    {
        #region Imcs_ShippingDao Members

        internal int Insert(mcs_Shipping data)
        {
            string sql = @"exec sp_mcs_Shipping_Insert @ShippingId, @OrderId, @AddressId, @ShippingMethod, @Status, @ShippingProvider, @Cost"; 

            return Db.Insert(sql, Take(data)); 
        }

        internal mcs_Shipping Get(int ShippingId)
        {
            string sql = @"exec sp_mcs_Shipping_Select @ShippingId";

            return Db.Read(sql, Make, new object[] { "@ShippingId", ShippingId }); 
        }

        internal List<mcs_Shipping> List(int startIndex, int itemCount)
        {
            string sql = @"exec sp_mcs_Shipping_List @startIndex, @itemCount";

            return Db.ReadList(sql, Make, new object[] { "@startIndex", startIndex, "@itemCount", itemCount }); 
        }

        internal void Update(mcs_Shipping data)
        {
            string sql = @"exec sp_mcs_Shipping_Update @ShippingId, @OrderId, @AddressId, @ShippingMethod, @Status, @ShippingProvider, @Cost";

            Db.Update(sql, Take(data));
        }

        internal void Delete(int ShippingId)
        {
            string sql = @"exec sp_mcs_Shipping_Delete @ShippingId";

            Db.Delete(sql, new object[] { "@ShippingId", ShippingId });
        }

        #endregion
        
        /// <summary>
        /// Creates a mcs_Shipping object based on DataReader.
        /// </summary>
        internal static Func<IDataReader, mcs_Shipping> Make = reader =>
           new mcs_Shipping
           {
			ShippingId = DbHelper.ConvertFromDBVal<int>(reader["ShippingId"]),
			OrderId = DbHelper.ConvertFromDBVal<int>(reader["OrderId"]),
			AddressId = DbHelper.ConvertFromDBVal<int>(reader["AddressId"]),
			ShippingMethod = DbHelper.ConvertFromDBVal<string>(reader["ShippingMethod"]),
			Status = DbHelper.ConvertFromDBVal<string>(reader["Status"]),
			ShippingProvider = DbHelper.ConvertFromDBVal<string>(reader["ShippingProvider"]),
			Cost = DbHelper.ConvertFromDBVal<decimal>(reader["Cost"])
           };

        /// <summary>
        /// Creates query parameters list from mcs_Shipping object
        /// </summary>
        /// <param name="mcs_Shipping">mcs_Shipping.</param>
        /// <returns>Name value parameter list.</returns>
        private object[] Take(mcs_Shipping data)
        {
            return new object[]  
            {
			"@ShippingId", data.ShippingId,
			"@OrderId", data.OrderId,
			"@AddressId", data.AddressId,
			"@ShippingMethod", data.ShippingMethod,
			"@Status", data.Status,
			"@ShippingProvider", data.ShippingProvider,
			"@Cost", data.Cost
            };
        }
    }
}
