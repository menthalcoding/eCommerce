package Util;


public class Constant {

    public static final String MediaType_image = "image/*";
    public static final String MediaType_pdf_file = "application/pdf";

    public static final String WEB_BASE_URL = "http://192.168.1.22/";
    public static final String API_BASE_URL = "https://10.0.2.2:7184/api/";
    public static final String API_AD_SPEC_URL = API_BASE_URL + "ad/specificationfile/";
    public static final String API_BID_SPEC_URL = API_BASE_URL + "bid/item/attachment/";

    public enum AcknowledgeType
    {
        /// <summary>
        /// Respresents an failed response.
        /// </summary>
        Failure,

            /// <summary>
            /// Represents a successful response.
            /// </summary>
        Success
    }

    public enum ErrorType
    {
        /// <summary>
        /// Respresents an failed type response.
        /// </summary>
         ClientTag,

            /// <summary>
            /// Respresents an failed type response.
            /// </summary>
        AccessToken,

            /// <summary>
            /// Respresents an failed type response.
            /// </summary>
        UserCredentials
    }

    public static int StatusCode_OK = 200;
    public static int StatusCode_BadRequest = 400;
    public static int StatusCode_Unauthorized = 401;
    public static int StatusCode_Forbidden = 403;
    public static int StatusCode_NotFound = 404;
    public static int StatusCode_InternalServerError = 500;
}
