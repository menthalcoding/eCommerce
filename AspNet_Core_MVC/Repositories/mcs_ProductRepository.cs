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
    internal class mcs_ProductRepository //: IRepository_mcs_Product
    {
        #region Imcs_ProductDao Members

        internal int Insert(mcs_Product data)
        {
            string sql = @"exec sp_mcs_Product_Insert @ProductId, @Name, @Title, @Description, @CategoryId, @Price, @DefaultPhoto, @Status, @Created_at, @Updated_at"; 

            return Db.Insert(sql, Take(data)); 
        }

        internal mcs_Product Get(int ProductId)
        {
            string sql = @"exec sp_mcs_Product_Select @ProductId";

            return Db.Read(sql, Make, new object[] { "@ProductId", ProductId }); 
        }

		internal List<mcs_Stock> Getmcs_StockList(int ProductId)
		{
			string sql = @"exec sp_mcs_Product_mcs_Stock_List @ProductId";

			return Db.ReadList(sql, mcs_StockRepository.Make, new object[] { "@ProductId", ProductId });
		}

		internal List<mcs_CartItem> Getmcs_CartItemList(int ProductId)
		{
			string sql = @"exec sp_mcs_Product_mcs_CartItem_List @ProductId";

			return Db.ReadList(sql, mcs_CartItemRepository.Make, new object[] { "@ProductId", ProductId });
		}

		internal List<mcs_ProductPhoto> Getmcs_ProductPhotoList(int ProductId)
		{
			string sql = @"exec sp_mcs_Product_mcs_ProductPhoto_List @ProductId";

			return Db.ReadList(sql, mcs_ProductPhotoRepository.Make, new object[] { "@ProductId", ProductId });
		}

        internal List<mcs_Product> List(int startIndex, int itemCount)
        {
            string sql = @"exec sp_mcs_Product_List @startIndex, @itemCount";

            return Db.ReadList(sql, Make, new object[] { "@startIndex", startIndex, "@itemCount", itemCount }); 
        }

        
        internal List<mcs_Product> List(ProductRequest productRequest, int startIndex, int itemCount)
        {
            string sql = @"exec sp_mcs_ProductByCategory_List @CategoryId, @Sort, @OrderDirection, @Status, @startIndex, @itemCount";

            return Db.ReadList(sql, Make, new object[] { "@CategoryId", productRequest.CategoryId, "@Sort", productRequest.Sort, "@OrderDirection", productRequest.OrderDirection, "@Status", productRequest.Status, "@startIndex", startIndex, "@itemCount", itemCount });
        }
                    
		internal void Update(mcs_Product data)
        {
            string sql = @"exec sp_mcs_Product_Update @ProductId, @Name, @Title, @Description, @CategoryId, @Price, @DefaultPhoto, @Status, @Created_at, @Updated_at";

            Db.Update(sql, Take(data));
        }

        internal void Delete(int ProductId)
        {
            string sql = @"exec sp_mcs_Product_Delete @ProductId";

            Db.Delete(sql, new object[] { "@ProductId", ProductId });
        }

        #endregion
        
        /// <summary>
        /// Creates a mcs_Product object based on DataReader.
        /// </summary>
        internal static Func<IDataReader, mcs_Product> Make = reader =>
           new mcs_Product
           {
			ProductId = DbHelper.ConvertFromDBVal<int>(reader["ProductId"]),
			Name = DbHelper.ConvertFromDBVal<string>(reader["Name"]),
			Title = DbHelper.ConvertFromDBVal<string>(reader["Title"]),
			Description = DbHelper.ConvertFromDBVal<string>(reader["Description"]),
			CategoryId = DbHelper.ConvertFromDBVal<int>(reader["CategoryId"]),
			Price = DbHelper.ConvertFromDBVal<decimal>(reader["Price"]),
			DefaultPhoto = DbHelper.ConvertFromDBVal<string>(reader["DefaultPhoto"]),
			Status = DbHelper.ConvertFromDBVal<string>(reader["Status"]),
			Created_at = DbHelper.ConvertFromDBVal<DateTime>(reader["Created_at"]),
			Updated_at = DbHelper.ConvertFromDBVal<DateTime>(reader["Updated_at"])
           };

        /// <summary>
        /// Creates query parameters list from mcs_Product object
        /// </summary>
        /// <param name="mcs_Product">mcs_Product.</param>
        /// <returns>Name value parameter list.</returns>
        private object[] Take(mcs_Product data)
        {
            return new object[]  
            {
			"@ProductId", data.ProductId,
			"@Name", data.Name,
			"@Title", data.Title,
			"@Description", data.Description,
			"@CategoryId", data.CategoryId,
			"@Price", data.Price,
			"@DefaultPhoto", data.DefaultPhoto,
			"@Status", data.Status,
			"@Created_at", data.Created_at,
			"@Updated_at", data.Updated_at
            };
        }
    }
}
