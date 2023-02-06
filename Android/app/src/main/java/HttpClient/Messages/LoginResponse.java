package HttpClient.Messages;

import HttpClient.Messages.MessageBase.ResponseBase;

public class LoginResponse { //extends ResponseBase {
    /// <summary>
    /// default Constructor for LoginResponse.
    /// </summary>
    //public LoginResponse() { super(); }
    //public LoginResponse() { }

    /// <summary>
    /// Overloaded Constructor for LoginResponse. Sets CorrelationId.
    /// </summary>
    /// <param name="correlationId"></param>
    //public LoginResponse(String correlationId)  { super(correlationId); }

    /// <summary>
    /// Uri to which client should redirect following successful login.
    /// This would be necessary if authentication is handled centrally
    /// and other services are distributed accross multiple servers.
    /// Not used in this sample application.
    /// SalesForce.com uses this in their API.
    /// </summary>
    public String uri = "";

    /// <summary>
    /// Session identifier. Useful when sessions are maintained using
    /// SOAP headers (rather than cookies). Not used in this sample application.
    /// SalesForce.com uses this in their SOAP header model.
    /// </summary>
    public String sessionId = "";

    public String accessToken = "";

    public String tokenType = "";

    public String expiresIn = "";
}
