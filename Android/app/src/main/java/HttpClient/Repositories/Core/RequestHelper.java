package HttpClient.Repositories.Core;

import java.util.UUID;

public class RequestHelper {
    public static class Helper
    {
        // The application ClientTag
        private static String ClientTag;

        /// <summary>
        /// Static constructor. Sets the ClientTag (read from web.config).
        /// </summary>
        Helper()
        {
            setClientTag("ConfigurationManager.AppSettings.Get(ClientTag)");
        }

        /// <summary>
        /// Gets or sets the Access Token value (provided by Server and stored in Session).
        /// </summary>
        private static String AccessToken;

        /// <summary>
        /// Helper extension method that adds RequestId, ClientTag, and AccessToken to all request types.
        /// </summary>
        /// <typeparam name="T">The request type.</typeparam>
        /// <param name="request">The request</param>
        /// <returns>Fully prepared request, ready to use.</returns>
/*        public static T Prepare<T>(this T request) where T : RequestBase
        {
            request.RequestId = getRequestId();
            request.ClientTag = getClientTag();
            request.AccessToken = getAccessToken();

            return request;
        }
*/

        /// <summary>
        /// Generates unique request GUID identifier.
        /// </summary>
        private static String RequestId;

        public static String getClientTag() {
            return ClientTag;
        }

        public static void setClientTag(String clientTag) {
            ClientTag = clientTag;
        }

        public static String getAccessToken() {
            //var repository = new AuthRepository();
            //return (String) repository.GetToken();

            return  null;
        }

        public static void setAccessToken(String accessToken) {
            AccessToken = accessToken;
        }

        public static String getRequestId() {
            return UUID.randomUUID().toString();
        }

        public static void setRequestId(String requestId) {
            RequestId = requestId;
        }
    }
}
