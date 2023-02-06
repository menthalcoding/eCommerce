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
    internal class mcs_ProductPhotoRepository //: IRepository_mcs_ProductPhoto
    {
        #region Imcs_ProductPhotoDao Members

        internal int Insert(mcs_ProductPhoto data)
        {
            string sql = @"exec sp_mcs_ProductPhoto_Insert @ProductPhotoId, @ProductId, @Url"; 

            return Db.Insert(sql, Take(data)); 
        }

        internal mcs_ProductPhoto Get(Guid ProductPhotoId)
        {
            string sql = @"exec sp_mcs_ProductPhoto_Select @ProductPhotoId";

            return Db.Read(sql, Make, new object[] { "@ProductPhotoId", ProductPhotoId }); 
        }

        internal List<mcs_ProductPhoto> List(int startIndex, int itemCount)
        {
            string sql = @"exec sp_mcs_ProductPhoto_List @startIndex, @itemCount";

            return Db.ReadList(sql, Make, new object[] { "@startIndex", startIndex, "@itemCount", itemCount }); 
        }

        internal void Update(mcs_ProductPhoto data)
        {
            string sql = @"exec sp_mcs_ProductPhoto_Update @ProductPhotoId, @ProductId, @Url";

            Db.Update(sql, Take(data));
        }

        internal void Delete(Guid ProductPhotoId)
        {
            string sql = @"exec sp_mcs_ProductPhoto_Delete @ProductPhotoId";

            Db.Delete(sql, new object[] { "@ProductPhotoId", ProductPhotoId });
        }

        #endregion
        
        /// <summary>
        /// Creates a mcs_ProductPhoto object based on DataReader.
        /// </summary>
        internal static Func<IDataReader, mcs_ProductPhoto> Make = reader =>
           new mcs_ProductPhoto
           {
			ProductPhotoId = DbHelper.ConvertFromDBVal<Guid>(reader["ProductPhotoId"]),
			ProductId = DbHelper.ConvertFromDBVal<int>(reader["ProductId"]),
			Url = DbHelper.ConvertFromDBVal<string>(reader["Url"])
           };

        /// <summary>
        /// Creates query parameters list from mcs_ProductPhoto object
        /// </summary>
        /// <param name="mcs_ProductPhoto">mcs_ProductPhoto.</param>
        /// <returns>Name value parameter list.</returns>
        private object[] Take(mcs_ProductPhoto data)
        {
            return new object[]  
            {
			"@ProductPhotoId", data.ProductPhotoId,
			"@ProductId", data.ProductId,
			"@Url", data.Url
            };
        }
    }
}
