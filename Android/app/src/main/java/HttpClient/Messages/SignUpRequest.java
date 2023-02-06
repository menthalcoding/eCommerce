package HttpClient.Messages;

import HttpClient.Messages.MessageBase.RequestBase;

public class SignUpRequest extends RequestBase {
    /// <summary>
    /// User name credential.
    /// </summary>
    public String UserName = "";

    /// <summary>
    /// Password credential.
    /// </summary>
    public String Password = "";

    public String ConfirmPassword = "";

    public String Email = "";

    public String Role = "";
}
