using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.Common;
using OnlineCoding;

namespace OnlineCoding.Code.Database
{
    /// <summary>
    /// Manages all lower level ADO.NET data base access.
    /// </summary>
    /// <remarks>
    /// GoF Design Patterns: Singleton, Factory, Proxy.
    /// 
    /// This class is the 'swiss army knife' of ADO.NET data access. It handles all  
    /// database access details and shields its complexity from its clients.
    /// 
    /// The Factory Design pattern is used to create database specific instances
    /// of Connection objects, Command objects, etc.
    /// 
    /// This class is like a Singleton -- it is a static class (Shared in VB) and 
    /// therefore only one class-'instance' ever will exist.
    /// 
    /// This class is a Proxy in that it 'stands in' for the actual DbProviderFactory.
    /// </remarks>
    public static class Db
    {
        // Note: Static initializers are thread safe.
        // If this class had a static constructor then these static variables 
        // would need to be initialized there.
        private static readonly string dataProvider = "System.Data.SqlClient";//ConfigurationManager.AppSettings.Get("DataProvider");
        private static readonly DbProviderFactory factory = DbProviderFactories.GetFactory(dataProvider);

        //private static readonly string connectionString = "Data Source=DESKTOP-CG4PNIF\\NEW_MSSQLSERVER;Initial Catalog=NorthwindSampleDb;User Id=sa; Password=YOUR_PASSWORD_HERE";
        private static readonly string connectionString = getConnectionString();

        private static string getConnectionString()
        {
            var builder = new ConfigurationBuilder()
                        .SetBasePath(Directory.GetCurrentDirectory())
                        .AddJsonFile("appSettings.json", optional: true, reloadOnChange: true);
            IConfiguration _configuration = builder.Build();
            return _configuration.GetConnectionString("DefaultConnection");
        }

        #region Fast data readers

        /// <summary>
        /// Fast read of individual item.
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="sql"></param>
        /// <param name="make"></param>
        /// <param name="parms"></param>
        /// <returns></returns>
        public static T Read<T>(string sql, Func<IDataReader, T> make, object[] parms = null)
        {
            var dbProvider = DbProviderFactories.GetFactory("System.Data.SqlClient");
            using (var connection = dbProvider.CreateConnection())
            {
                connection.ConnectionString = connectionString;

                using (var command = factory.CreateCommand())
                {
                    command.Connection = connection;
                    command.CommandText = sql;
                    command.SetParameters(parms);  // Extension method

                    connection.Open();

                    T t = default(T);
                    var reader = command.ExecuteReader();
                    if (reader.Read())
                        t = make(reader);

                    return t;
                }
            }
        }

        /// <summary>
        /// Fast read of list of items.
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="sql"></param>
        /// <param name="make"></param>
        /// <param name="parms"></param>
        /// <returns></returns>
        public static List<T> ReadList<T>(string sql, Func<IDataReader, T> make, object[] parms = null)
        {
            //DbProviderFactories.RegisterFactory("System.Data.SqlClient", System.Data.SqlClient.SqlClientFactory.Instance);
            var dbProvider = DbProviderFactories.GetFactory("System.Data.SqlClient");
            using (var connection = dbProvider.CreateConnection())
            {
                connection.ConnectionString = connectionString;

                using (var command = factory.CreateCommand())
                {
                    command.Connection = connection;
                    command.CommandText = sql;
                    command.SetParameters(parms);

                    connection.Open();

                    var list = new List<T>();
                    var reader = command.ExecuteReader();

                    while (reader.Read())
                        list.Add(make(reader));

                    return list;
                }
            }
        }

        /// <summary>
        /// Gets a record count.
        /// </summary>
        /// <param name="sql"></param>
        /// <param name="parms"></param>
        /// <returns></returns>
        public static int GetCount(string sql, object[] parms = null)
        {
            int count = 0;
            int.TryParse(GetScalar(sql, parms).ToString(), out count);

            return count;
        }

        /// <summary>
        /// Gets any scalar value from the database.
        /// </summary>
        /// <param name="sql"></param>
        /// <param name="parms"></param>
        /// <returns></returns>
        public static object GetScalar(string sql, object[] parms = null)
        {
            using (var connection = factory.CreateConnection())
            {
                connection.ConnectionString = connectionString;

                using (var command = factory.CreateCommand())
                {
                    command.Connection = connection;
                    command.CommandText = sql;
                    command.SetParameters(parms);

                    connection.Open();
                    return command.ExecuteScalar();
                }
            }
        }

        #endregion

        #region Data update section

        /// <summary>
        /// Inserts an item into the database
        /// </summary>
        /// <param name="sql"></param>
        /// <param name="parms"></param>
        /// <returns></returns>
        public static int Insert(string sql, object[] parms = null)
        {
            using (var connection = factory.CreateConnection())
            {
                connection.ConnectionString = connectionString;

                using (var command = factory.CreateCommand())
                {
                    command.Connection = connection;
                    command.SetParameters(parms);                     // Extension method  
                    command.CommandText = sql.AppendIdentitySelect(); // Extension method

                    connection.Open();

                    // MS Access does not support multistatement batch commands. Issue a separate query.
                    if (dataProvider == "System.Data.OleDb")
                    {
                        command.ExecuteNonQuery();
                        command.CommandText = "SELECT @@IDENTITY";
                    }
                    
                    int newId = 0;
                    int.TryParse(command.ExecuteScalar().ToString(), out newId);

                    return newId;
                }
            }
        }

        /// <summary>
        /// Updates an item in the database
        /// </summary>
        /// <param name="sql"></param>
        /// <param name="parms"></param>
        public static void Update(string sql, object[] parms = null)
        {
            using (var connection = factory.CreateConnection())
            {
                connection.ConnectionString = connectionString;

                using (var command = factory.CreateCommand())
                {
                    command.Connection = connection;
                    command.CommandText = sql;
                    command.SetParameters(parms);

                    connection.Open();
                    command.ExecuteNonQuery();
                }
            }
        }

        /// <summary>
        /// Deletes an item from the database.
        /// </summary>
        /// <param name="sql"></param>
        /// <param name="parms"></param>
        public static void Delete(string sql, object[] parms = null)
        {
            Update(sql, parms);
        }

        #endregion

        #region Extension methods

        /// <summary>
        /// Extension method: Appends the db specific syntax to sql string 
        /// for retrieving newly generated identity (autonumber) value.
        /// </summary>
        /// <param name="sql">The sql string to which to append syntax.</param>
        /// <returns>Sql string with identity select.</returns>
        private static string AppendIdentitySelect(this string sql)
        {
            switch (dataProvider)
            {
                // Microsoft Access does not support multistatement batch commands
                case "System.Data.OleDb": return sql;
                case "System.Data.SqlClient": return sql + ";SELECT SCOPE_IDENTITY()";
                case "System.Data.OracleClient": return sql + ";SELECT MySequence.NEXTVAL FROM DUAL";
                default: return sql + ";SELECT @@IDENTITY";
            }
        }

        /// <summary>
        /// Extention method. Adds query parameters to command object.
        /// </summary>
        /// <param name="command">Command object.</param>
        /// <param name="parms">Array of name-value query parameters.</param>
        private static void SetParameters(this DbCommand command, object[] parms)
        {
            if (parms != null && parms.Length > 0)
            {
                // NOTE: Processes a name/value pair at each iteration
                for (int i = 0; i < parms.Length; i += 2)
                {
                    string name = parms[i].ToString();

                    // No empty strings to the database
                    if (parms[i + 1] is string && (string)parms[i + 1] == "")
                        parms[i + 1] = null;

                    // If null, set to DbNull
                    object value = parms[i + 1] ?? DBNull.Value;

                    var dbParameter = command.CreateParameter();
                    dbParameter.ParameterName = name;
                    dbParameter.Value = value;

                    command.Parameters.Add(dbParameter);
                }
            }
        }

        #endregion
    }
}
