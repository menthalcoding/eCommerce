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
    internal class mcs_CartItemRepository //: IRepository_mcs_CartItem
    {
        #region Imcs_CartItemDao Members

        internal int Insert(mcs_CartItem data)
        {
            string sql = @"exec sp_mcs_CartItem_Insert @CartItemId, @CartId, @ProductId, @Quantity, @Created_at, @Updated_at"; 

            return Db.Insert(sql, Take(data)); 
        }

        internal mcs_CartItem Get(int CartItemId)
        {
            string sql = @"exec sp_mcs_CartItem_Select @CartItemId";

            return Db.Read(sql, Make, new object[] { "@CartItemId", CartItemId }); 
        }

        internal List<mcs_CartItem> List(int startIndex, int itemCount)
        {
            string sql = @"exec sp_mcs_CartItem_List @startIndex, @itemCount";

            return Db.ReadList(sql, Make, new object[] { "@startIndex", startIndex, "@itemCount", itemCount }); 
        }

        internal void Update(mcs_CartItem data)
        {
            string sql = @"exec sp_mcs_CartItem_Update @CartItemId, @CartId, @ProductId, @Quantity, @Created_at, @Updated_at";

            Db.Update(sql, Take(data));
        }

        internal void Delete(int CartItemId)
        {
            string sql = @"exec sp_mcs_CartItem_Delete @CartItemId";

            Db.Delete(sql, new object[] { "@CartItemId", CartItemId });
        }

        #endregion
        
        /// <summary>
        /// Creates a mcs_CartItem object based on DataReader.
        /// </summary>
        internal static Func<IDataReader, mcs_CartItem> Make = reader =>
           new mcs_CartItem
           {
			CartItemId = DbHelper.ConvertFromDBVal<int>(reader["CartItemId"]),
			CartId = DbHelper.ConvertFromDBVal<int>(reader["CartId"]),
			ProductId = DbHelper.ConvertFromDBVal<int>(reader["ProductId"]),
			Quantity = DbHelper.ConvertFromDBVal<int>(reader["Quantity"]),
			Created_at = DbHelper.ConvertFromDBVal<DateTime>(reader["Created_at"]),
			Updated_at = DbHelper.ConvertFromDBVal<DateTime>(reader["Updated_at"])
           };

        /// <summary>
        /// Creates query parameters list from mcs_CartItem object
        /// </summary>
        /// <param name="mcs_CartItem">mcs_CartItem.</param>
        /// <returns>Name value parameter list.</returns>
        private object[] Take(mcs_CartItem data)
        {
            return new object[]  
            {
			"@CartItemId", data.CartItemId,
			"@CartId", data.CartId,
			"@ProductId", data.ProductId,
			"@Quantity", data.Quantity,
			"@Created_at", data.Created_at,
			"@Updated_at", data.Updated_at
            };
        }
    }
}
