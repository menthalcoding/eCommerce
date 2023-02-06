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
    internal class mcs_PaymentRepository //: IRepository_mcs_Payment
    {
        #region Imcs_PaymentDao Members

        internal int Insert(mcs_Payment data)
        {
            string sql = @"exec sp_mcs_Payment_Insert @PaymentId, @UserId, @PaymentType, @Created_at, @Amount, @Currency, @OrderId, @AddressId"; 

            return Db.Insert(sql, Take(data)); 
        }

        internal mcs_Payment Get(int PaymentId)
        {
            string sql = @"exec sp_mcs_Payment_Select @PaymentId";

            return Db.Read(sql, Make, new object[] { "@PaymentId", PaymentId }); 
        }

        internal List<mcs_Payment> List(int startIndex, int itemCount)
        {
            string sql = @"exec sp_mcs_Payment_List @startIndex, @itemCount";

            return Db.ReadList(sql, Make, new object[] { "@startIndex", startIndex, "@itemCount", itemCount }); 
        }

        internal List<mcs_Payment> List(string UserId, int startIndex, int itemCount)
        {
            string sql = @"exec sp_mcs_Payment_List @startIndex, @itemCount, @UserId";

            return Db.ReadList(sql, Make, new object[] { "@startIndex", startIndex, "@itemCount", itemCount, "@UserId", UserId });
        }
        
        internal void Update(mcs_Payment data)
        {
            string sql = @"exec sp_mcs_Payment_Update @PaymentId, @UserId, @PaymentType, @Created_at, @Amount, @Currency, @OrderId, @AddressId";

            Db.Update(sql, Take(data));
        }

        internal void Delete(string UserId, int PaymentId)
        {
            string sql = @"exec sp_mcs_Payment_Delete @UserId, @PaymentId";

            Db.Delete(sql, new object[] { "@UserId", UserId, "@PaymentId", PaymentId});
        }
        
        internal void Delete(int PaymentId)
        {
            string sql = @"exec sp_mcs_Payment_Delete @PaymentId";

            Db.Delete(sql, new object[] { "@PaymentId", PaymentId });
        }

        #endregion
        
        /// <summary>
        /// Creates a mcs_Payment object based on DataReader.
        /// </summary>
        internal static Func<IDataReader, mcs_Payment> Make = reader =>
           new mcs_Payment
           {
			PaymentId = DbHelper.ConvertFromDBVal<int>(reader["PaymentId"]),
			UserId = DbHelper.ConvertFromDBVal<string>(reader["UserId"]),
			PaymentType = DbHelper.ConvertFromDBVal<string>(reader["PaymentType"]),
			Created_at = DbHelper.ConvertFromDBVal<DateTime>(reader["Created_at"]),
			Amount = DbHelper.ConvertFromDBVal<decimal>(reader["Amount"]),
			Currency = DbHelper.ConvertFromDBVal<string>(reader["Currency"]),
			OrderId = DbHelper.ConvertFromDBVal<int>(reader["OrderId"]),
			AddressId = DbHelper.ConvertFromDBVal<int>(reader["AddressId"])
           };

        /// <summary>
        /// Creates query parameters list from mcs_Payment object
        /// </summary>
        /// <param name="mcs_Payment">mcs_Payment.</param>
        /// <returns>Name value parameter list.</returns>
        private object[] Take(mcs_Payment data)
        {
            return new object[]  
            {
			"@PaymentId", data.PaymentId,
			"@UserId", data.UserId,
			"@PaymentType", data.PaymentType,
			"@Created_at", data.Created_at,
			"@Amount", data.Amount,
			"@Currency", data.Currency,
			"@OrderId", data.OrderId,
			"@AddressId", data.AddressId
            };
        }
    }
}
