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
    internal class mcs_OrderRepository //: IRepository_mcs_Order
    {
        #region Imcs_OrderDao Members

        internal int Insert(mcs_Order data)
        {
            string sql = @"exec sp_mcs_Order_Insert @OrderId, @UserId, @CartId, @Created_at, @Cost, @Tax, @Total, @Paid, @Currency"; 

            return Db.Insert(sql, Take(data)); 
        }

        internal mcs_Order Get(int OrderId)
        {
            string sql = @"exec sp_mcs_Order_Select @OrderId";

            return Db.Read(sql, Make, new object[] { "@OrderId", OrderId }); 
        }

		internal List<mcs_Shipping> Getmcs_ShippingList(int OrderId)
		{
			string sql = @"exec sp_mcs_Order_mcs_Shipping_List @OrderId";

			return Db.ReadList(sql, mcs_ShippingRepository.Make, new object[] { "@OrderId", OrderId });
		}

        internal List<mcs_Order> List(int startIndex, int itemCount)
        {
            string sql = @"exec sp_mcs_Order_List @startIndex, @itemCount";

            return Db.ReadList(sql, Make, new object[] { "@startIndex", startIndex, "@itemCount", itemCount }); 
        }

        internal List<mcs_Order> List(string UserId, int startIndex, int itemCount)
        {
            string sql = @"exec sp_mcs_Order_List @startIndex, @itemCount, @UserId";

            return Db.ReadList(sql, Make, new object[] { "@startIndex", startIndex, "@itemCount", itemCount, "@UserId", UserId });
        }
        
        internal void Update(mcs_Order data)
        {
            string sql = @"exec sp_mcs_Order_Update @OrderId, @UserId, @CartId, @Created_at, @Cost, @Tax, @Total, @Paid, @Currency";

            Db.Update(sql, Take(data));
        }

        internal void Delete(string UserId, int OrderId)
        {
            string sql = @"exec sp_mcs_Order_Delete @UserId, @OrderId";

            Db.Delete(sql, new object[] { "@UserId", UserId, "@OrderId", OrderId});
        }
        
        internal void Delete(int OrderId)
        {
            string sql = @"exec sp_mcs_Order_Delete @OrderId";

            Db.Delete(sql, new object[] { "@OrderId", OrderId });
        }

        #endregion
        
        /// <summary>
        /// Creates a mcs_Order object based on DataReader.
        /// </summary>
        internal static Func<IDataReader, mcs_Order> Make = reader =>
           new mcs_Order
           {
			OrderId = DbHelper.ConvertFromDBVal<int>(reader["OrderId"]),
			UserId = DbHelper.ConvertFromDBVal<string>(reader["UserId"]),
			CartId = DbHelper.ConvertFromDBVal<int>(reader["CartId"]),
			Created_at = DbHelper.ConvertFromDBVal<DateTime>(reader["Created_at"]),
			Cost = DbHelper.ConvertFromDBVal<decimal>(reader["Cost"]),
			Tax = DbHelper.ConvertFromDBVal<decimal>(reader["Tax"]),
			Total = DbHelper.ConvertFromDBVal<decimal>(reader["Total"]),
			Paid = DbHelper.ConvertFromDBVal<bool>(reader["Paid"]),
			Currency = DbHelper.ConvertFromDBVal<string>(reader["Currency"])
           };

        /// <summary>
        /// Creates query parameters list from mcs_Order object
        /// </summary>
        /// <param name="mcs_Order">mcs_Order.</param>
        /// <returns>Name value parameter list.</returns>
        private object[] Take(mcs_Order data)
        {
            return new object[]  
            {
			"@OrderId", data.OrderId,
			"@UserId", data.UserId,
			"@CartId", data.CartId,
			"@Created_at", data.Created_at,
			"@Cost", data.Cost,
			"@Tax", data.Tax,
			"@Total", data.Total,
			"@Paid", data.Paid,
			"@Currency", data.Currency
            };
        }
    }
}
