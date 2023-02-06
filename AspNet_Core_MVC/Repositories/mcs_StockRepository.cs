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
    internal class mcs_StockRepository //: IRepository_mcs_Stock
    {
        #region Imcs_StockDao Members

        internal int Insert(mcs_Stock data)
        {
            string sql = @"exec sp_mcs_Stock_Insert @StockId, @ProductId, @Quantity, @EntryPrice, @EntryDate"; 

            return Db.Insert(sql, Take(data)); 
        }

        internal mcs_Stock Get(int StockId)
        {
            string sql = @"exec sp_mcs_Stock_Select @StockId";

            return Db.Read(sql, Make, new object[] { "@StockId", StockId }); 
        }

        internal List<mcs_Stock> List(int startIndex, int itemCount)
        {
            string sql = @"exec sp_mcs_Stock_List @startIndex, @itemCount";

            return Db.ReadList(sql, Make, new object[] { "@startIndex", startIndex, "@itemCount", itemCount }); 
        }

        internal void Update(mcs_Stock data)
        {
            string sql = @"exec sp_mcs_Stock_Update @StockId, @ProductId, @Quantity, @EntryPrice, @EntryDate";

            Db.Update(sql, Take(data));
        }

        internal void Delete(int StockId)
        {
            string sql = @"exec sp_mcs_Stock_Delete @StockId";

            Db.Delete(sql, new object[] { "@StockId", StockId });
        }

        #endregion
        
        /// <summary>
        /// Creates a mcs_Stock object based on DataReader.
        /// </summary>
        internal static Func<IDataReader, mcs_Stock> Make = reader =>
           new mcs_Stock
           {
			StockId = DbHelper.ConvertFromDBVal<int>(reader["StockId"]),
			ProductId = DbHelper.ConvertFromDBVal<int>(reader["ProductId"]),
			Quantity = DbHelper.ConvertFromDBVal<int>(reader["Quantity"]),
			EntryPrice = DbHelper.ConvertFromDBVal<decimal>(reader["EntryPrice"]),
			EntryDate = DbHelper.ConvertFromDBVal<DateTime>(reader["EntryDate"])
           };

        /// <summary>
        /// Creates query parameters list from mcs_Stock object
        /// </summary>
        /// <param name="mcs_Stock">mcs_Stock.</param>
        /// <returns>Name value parameter list.</returns>
        private object[] Take(mcs_Stock data)
        {
            return new object[]  
            {
			"@StockId", data.StockId,
			"@ProductId", data.ProductId,
			"@Quantity", data.Quantity,
			"@EntryPrice", data.EntryPrice,
			"@EntryDate", data.EntryDate
            };
        }
    }
}
