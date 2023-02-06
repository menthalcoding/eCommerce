package HttpClient.Messages;

public class LoginWebApiRequest {
    /// <summary>
    /// Email credential.
    /// </summary>
    public String Email = "";

    /// <summary>
    /// Password credential..
    /// </summary>
    public String Password = "";

    public boolean Persistent;

    public boolean Lock;
}
