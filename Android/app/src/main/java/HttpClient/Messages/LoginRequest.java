package HttpClient.Messages;

import HttpClient.Messages.MessageBase.RequestBase;

public class LoginRequest extends RequestBase {

    public String grant_type = "password";

    /// <summary>
    /// User name credential.
    /// </summary>
    public String username = "";

    /// <summary>
    /// Password credential..
    /// </summary>
    public String password = "";
}
