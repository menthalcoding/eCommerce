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
    internal class mcs_CartRepository //: IRepository_mcs_Cart
    {
        #region Imcs_CartDao Members

        internal int Insert(mcs_Cart data)
        {
            string sql = @"exec sp_mcs_Cart_Insert @CartId, @UserId, @Status, @Created_at, @Updated_at"; 

            return Db.Insert(sql, Take(data)); 
        }

        internal mcs_Cart Get(int CartId)
        {
            string sql = @"exec sp_mcs_Cart_Select @CartId";

            return Db.Read(sql, Make, new object[] { "@CartId", CartId }); 
        }

		internal List<mcs_CartItem> Getmcs_CartItemList(int CartId)
		{
			string sql = @"exec sp_mcs_Cart_mcs_CartItem_List @CartId";

			return Db.ReadList(sql, mcs_CartItemRepository.Make, new object[] { "@CartId", CartId });
		}

		internal List<mcs_Order> Getmcs_OrderList(int CartId)
		{
			string sql = @"exec sp_mcs_Cart_mcs_Order_List @CartId";

			return Db.ReadList(sql, mcs_OrderRepository.Make, new object[] { "@CartId", CartId });
		}

        internal List<mcs_Cart> List(int startIndex, int itemCount)
        {
            string sql = @"exec sp_mcs_Cart_List @startIndex, @itemCount";

            return Db.ReadList(sql, Make, new object[] { "@startIndex", startIndex, "@itemCount", itemCount }); 
        }

        internal List<mcs_Cart> List(string UserId, int startIndex, int itemCount)
        {
            string sql = @"exec sp_mcs_Cart_List @startIndex, @itemCount, @UserId";

            return Db.ReadList(sql, Make, new object[] { "@startIndex", startIndex, "@itemCount", itemCount, "@UserId", UserId });
        }
        
        internal void Update(mcs_Cart data)
        {
            string sql = @"exec sp_mcs_Cart_Update @CartId, @UserId, @Status, @Created_at, @Updated_at";

            Db.Update(sql, Take(data));
        }

        internal void Delete(string UserId, int CartId)
        {
            string sql = @"exec sp_mcs_Cart_Delete @UserId, @CartId";

            Db.Delete(sql, new object[] { "@UserId", UserId, "@CartId", CartId});
        }
        
        internal void Delete(int CartId)
        {
            string sql = @"exec sp_mcs_Cart_Delete @CartId";

            Db.Delete(sql, new object[] { "@CartId", CartId });
        }

        #endregion
        
        /// <summary>
        /// Creates a mcs_Cart object based on DataReader.
        /// </summary>
        internal static Func<IDataReader, mcs_Cart> Make = reader =>
           new mcs_Cart
           {
			CartId = DbHelper.ConvertFromDBVal<int>(reader["CartId"]),
			UserId = DbHelper.ConvertFromDBVal<string>(reader["UserId"]),
			Status = DbHelper.ConvertFromDBVal<string>(reader["Status"]),
			Created_at = DbHelper.ConvertFromDBVal<DateTime>(reader["Created_at"]),
			Updated_at = DbHelper.ConvertFromDBVal<DateTime>(reader["Updated_at"])
           };

        /// <summary>
        /// Creates query parameters list from mcs_Cart object
        /// </summary>
        /// <param name="mcs_Cart">mcs_Cart.</param>
        /// <returns>Name value parameter list.</returns>
        private object[] Take(mcs_Cart data)
        {
            return new object[]  
            {
			"@CartId", data.CartId,
			"@UserId", data.UserId,
			"@Status", data.Status,
			"@Created_at", data.Created_at,
			"@Updated_at", data.Updated_at
            };
        }
    }
}
