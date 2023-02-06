package HttpClient.Repositories.Core;

import java.util.List;

import HttpClient.Messages.Criteria.Criteria;

/// <summary>
/// Generic repository base interface.
/// Provides basic CRUD (Create, Read, Update, Delete) and a couple more methods.
/// </summary>
/// <typeparam name="T">The business object type.</typeparam>
public interface IRepository<T> {

    /// <summary>
    /// Reads a list of items.
    /// </summary>
    /// <param name="criterion">The order and filter criteria.</param>
    /// <returns></returns>
    List<T> GetList(Criteria criterion);

    /// <summary>
    /// Reads an individual item.
    /// </summary>
    /// <param name="id">The business object's id.</param>
    /// <returns></returns>
    T Get(Criteria criterion);

    /// <summary>
    /// Get a count of the number of items.
    /// </summary>
    /// <param name="criterion">The order and filter criteria</param>
    /// <returns></returns>
    int GetCount(Criteria criterion);

    /// <summary>
    /// Inserts a new item.
    /// </summary>
    /// <param name="t">The business object. </param>
    int Post(T t);

    /// <summary>
    /// Updates an existing item.
    /// </summary>
    /// <param name="t">The business object.</param>
    void Put(T t);

    /// <summary>
    /// Deletes an item.
    /// </summary>
    /// <param name="t">The business object's id</param>
    void Delete(Criteria criterion);
}
