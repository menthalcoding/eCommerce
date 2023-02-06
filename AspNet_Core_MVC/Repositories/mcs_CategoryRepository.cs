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
    internal class mcs_CategoryRepository //: IRepository_mcs_Category
    {
        #region Imcs_CategoryDao Members

        internal int Insert(mcs_Category data)
        {
            string sql = @"exec sp_mcs_Category_Insert @CategoryId, @CategoryParentId, @Name, @Description"; 

            return Db.Insert(sql, Take(data)); 
        }

        internal mcs_Category Get(int CategoryId)
        {
            string sql = @"exec sp_mcs_Category_Select @CategoryId";

            return Db.Read(sql, Make, new object[] { "@CategoryId", CategoryId }); 
        }

		internal List<mcs_Product> Getmcs_ProductList(int CategoryId)
		{
			string sql = @"exec sp_mcs_Category_mcs_Product_List @CategoryId";

			return Db.ReadList(sql, mcs_ProductRepository.Make, new object[] { "@CategoryId", CategoryId });
		}

		internal List<mcs_Category> Getmcs_CategoryList(int CategoryId)
		{
			string sql = @"exec sp_mcs_Category_mcs_Category_List @CategoryId";

			return Db.ReadList(sql, mcs_CategoryRepository.Make, new object[] { "@CategoryId", CategoryId });
		}

        internal List<mcs_Category> List(int startIndex, int itemCount)
        {
            string sql = @"exec sp_mcs_Category_List @startIndex, @itemCount";

            return Db.ReadList(sql, Make, new object[] { "@startIndex", startIndex, "@itemCount", itemCount }); 
        }

        internal void Update(mcs_Category data)
        {
            string sql = @"exec sp_mcs_Category_Update @CategoryId, @CategoryParentId, @Name, @Description";

            Db.Update(sql, Take(data));
        }

        internal void Delete(int CategoryId)
        {
            string sql = @"exec sp_mcs_Category_Delete @CategoryId";

            Db.Delete(sql, new object[] { "@CategoryId", CategoryId });
        }

        #endregion
        
        /// <summary>
        /// Creates a mcs_Category object based on DataReader.
        /// </summary>
        internal static Func<IDataReader, mcs_Category> Make = reader =>
           new mcs_Category
           {
			CategoryId = DbHelper.ConvertFromDBVal<int>(reader["CategoryId"]),
			CategoryParentId = DbHelper.ConvertFromDBVal<int>(reader["CategoryParentId"]),
			Name = DbHelper.ConvertFromDBVal<string>(reader["Name"]),
			Description = DbHelper.ConvertFromDBVal<string>(reader["Description"])
           };

        /// <summary>
        /// Creates query parameters list from mcs_Category object
        /// </summary>
        /// <param name="mcs_Category">mcs_Category.</param>
        /// <returns>Name value parameter list.</returns>
        private object[] Take(mcs_Category data)
        {
            return new object[]  
            {
			"@CategoryId", data.CategoryId,
			"@CategoryParentId", data.CategoryParentId,
			"@Name", data.Name,
			"@Description", data.Description
            };
        }
    }
}
