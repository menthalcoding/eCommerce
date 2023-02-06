package HttpClient.Messages;

import HttpClient.Messages.MessageBase.ResponseBase;

public class SignUpResponse extends ResponseBase {

    /// <summary>
    /// default Constructor for LoginResponse.
    /// </summary>
    public SignUpResponse() { }

    /// <summary>
    /// Overloaded Constructor for LoginResponse. Sets CorrelationId.
    /// </summary>
    /// <param name="correlationId"></param>
    public SignUpResponse(String correlationId) { super(correlationId); }
}
