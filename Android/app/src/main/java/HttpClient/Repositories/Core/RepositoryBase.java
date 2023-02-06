package HttpClient.Repositories.Core;

import retrofit2.Retrofit;

public abstract class RepositoryBase {

    protected Retrofit Client;

    public Retrofit getClient() {

        if (Client == null)
        {
            //Client = WebApiClient.getClient();
        }

        return Client;
    }
}