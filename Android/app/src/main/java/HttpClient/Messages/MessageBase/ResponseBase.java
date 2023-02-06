package HttpClient.Messages.MessageBase;

import java.time.LocalDateTime;
import java.util.Date;

public class ResponseBase {
    /// <summary>
    /// Default Constructor for ResponseBase.
    /// </summary>
    public ResponseBase() { }

    /// <summary>
    /// Overloaded Constructor for ResponseBase.
    /// Sets CorrelationId from incoming Request.
    /// </summary>
    /// <param name="correlationId">The correlation identifier for request and response.</param>
    public ResponseBase(String correlationId)
    {
        CorrelationId = correlationId;
    }

    /// <summary>
    /// A flag indicating success or failure of the web service response back to the
    /// client. Default is success. Ebay.com uses this model.
    /// </summary>
    public AcknowledgeType Acknowledge = AcknowledgeType.Success;

    /// <summary>
    /// CorrelationId mostly returns the RequestId back to client.
    /// </summary>
    public String CorrelationId;

    /// <summary>
    /// Message back to client. Mostly used when a web service failure occurs.
    /// </summary>
    public ErrorType Error = ErrorType.UserCredentials;

    /// <summary>
    /// Message back to client. Mostly used when a web service failure occurs.
    /// </summary>
    public String Message;

    /// <summary>
    /// Reservation number issued by the web service. Used in long running requests.
    /// Also sometimes referred to as Correlation Id. This number is a way for both the client
    /// and web service to keep track of long running requests (for example, a request
    /// to make a reservation for a airplane flight).
    /// </summary>
    public String ReservationId;

    /// <summary>
    /// Date when reservation number expires.
    /// </summary>
    public String ReservationExpires;

    /// <summary>
    /// Version number (in major.minor format) of currently executing web service.
    /// Used to offer a level of understanding (related to compatibility issues) between
    /// the client and the web service as the web services evolve over time.
    /// Ebay.com uses this in their API.
    /// </summary>
    public String Version = "BuildConfig.VERSION_NAME";

    /// <summary>
    /// Build number of currently executing web service. Used as an indicator
    /// to client whether certain code fixes are included or not.
    /// Ebay.com uses this in their API.
    /// </summary>
    public String Build = "BuildConfig.BUILD_TYPE";


    /// <summary>
    /// Number of rows affected by "Create", "Update", or "Delete" action.
    /// </summary>
    public int RowsAffected;
}
