package HttpClient.Messages.MessageBase;

import java.time.LocalDateTime;
import java.util.Date;

/// <summary>
/// Base class for all client request messages of the web service. It standardizes
/// communication between web services and clients with a series of common values.
/// Derived request message classes assign values to these variables. There are no
/// default values.
/// </summary>
public class RequestBase
{
    /// <summary>
    /// Each web service request carries a security token as an extra level of security.
    /// Tokens are issued when users are coming online. They can expire if necessary.
    /// Google.com and Amazon.com uses this in their API.
    /// </summary>
    public String ClientTag = "ABC123";

    /// <summary>
    /// Each web service request carries a security token as an extra level of security.
    /// Tokens are issued when users are coming online. They can expire if necessary.
    /// Google.com and Amazon.com uses this in their API.
    /// </summary>
    public String AccessToken;

    /// <summary>
    /// Minimum version number that client request is required to run under. This facilitates
    /// a certain level of backward compatibility for when the web service API evolves.
    /// Ebay.com uses the version number in their API.
    /// </summary>
    public String Version;

    /// <summary>
    /// A unique number (ideally a Guid) issued by the client representing the instance
    /// of the request. Avoids rapid-fire processing of the same request over and over
    /// in denial-of-service type attacks.
    /// </summary>
    public String RequestId;


    /// <summary>
    /// Load options indicated what types are to be returned in the request.
    /// </summary>
    public String[] LoadOptions;

    /// <summary>
    /// Crud action: Create, Read, Update, Delete
    /// </summary>
    public String Action;

    /// <summary>
    /// User Name
    /// </summary>
    public String UserId;
}
