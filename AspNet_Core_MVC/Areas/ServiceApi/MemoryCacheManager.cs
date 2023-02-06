using Microsoft.Extensions.Caching.Memory;
using Microsoft.Extensions.Primitives;

namespace Northwind.Areas.ServiceApi
{
    public class MemoryCacheManager
    {
        private static CancellationTokenSource _resetCacheToken = new CancellationTokenSource();
        private readonly IMemoryCache _memoryCache;
        /// <summary>
        /// Cache Manager
        /// </summary>
        /// <param name="memoryCache"></param>
        public MemoryCacheManager(IMemoryCache memoryCache)
        {
            _memoryCache = memoryCache;
        }
        /// <summary>
        /// sets the cache entry T
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="key"></param>
        /// <param name="value"></param>
        /// <param name="expirationInMinutes"></param>
        /// <returns></returns>
        public T Set<T>(object key, T value, int expirationInMinutes = 1)
        {
            MemoryCacheEntryOptions options = new MemoryCacheEntryOptions().SetPriority(CacheItemPriority.Normal).SetAbsoluteExpiration(TimeSpan.FromMinutes(expirationInMinutes));
            options.AddExpirationToken(new CancellationChangeToken(_resetCacheToken.Token));
            _memoryCache.Set(key, value, options);
            return value;
        }
        /// <summary>
        /// checks for cache entry existence
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>
        public bool Contains(object key)
        {
            return _memoryCache.TryGetValue(key, out object result);
        }
        /// <summary>
        /// returns cache entry T
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="key"></param>
        /// <returns></returns>
        public T Get<T>(object key)
        {
            return _memoryCache.TryGetValue(key, out T result) ? result : default(T);
        }
        /// <summary>
        /// clear cache entry
        /// </summary>
        /// <param name="key"></param>
        public void Clear(object key)
        {
            _memoryCache.Remove(key);
        }
        /// <summary>
        /// expires cache entries T based on CancellationTokenSource cancel 
        /// </summary>
        public void Reset()
        {
            if (_resetCacheToken != null && !_resetCacheToken.IsCancellationRequested &&
                _resetCacheToken.Token.CanBeCanceled)
            {
                _resetCacheToken.Cancel();
                _resetCacheToken.Dispose();
            }
            _resetCacheToken = new CancellationTokenSource();
        }
    }
}