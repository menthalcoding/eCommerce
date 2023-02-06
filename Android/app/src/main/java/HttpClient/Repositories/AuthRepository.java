package HttpClient.Repositories;

import android.widget.Toast;

import java.util.Base64;

import HttpClient.Core.EndPointContracts.IAuthClient;
import HttpClient.Core.WebClient;
import HttpClient.DataTransferObjects.ProfileCustomerDto;
import HttpClient.Messages.LoginRequest;
import HttpClient.Messages.LoginResponse;
import HttpClient.Messages.LoginWebApiRequest;
import HttpClient.Messages.SignUpRequest;
import HttpClient.Messages.SignUpResponse;
import HttpClient.Repositories.Core.RepositoryBase;
import Util.SessionManager;
import retrofit2.Call;
import retrofit2.Response;

public class AuthRepository extends RepositoryBase {

    IAuthClient service;

    public AuthRepository() {

        service = WebClient.createService(IAuthClient.class, SessionManager.ACCESS_TOKEN);
    }

    public LoginResponse Login(LoginWebApiRequest request) {

        byte[] credentials = "clientId:clientSecret".getBytes();
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString(credentials);
        //Call<LoginResponse> call = service.Login("password", request.Email, request.Password, basicAuth);
        Call<LoginResponse> call = service.Login(request);
        LoginResponse body = null;
        try {
            Response<LoginResponse> response = call.execute();
            body = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    public String GetAccesTokenRPC(String accessToken) {

        Call<String> call = service.GetAccesTokenRPC( "Bearer " + accessToken);
        String body = null;
        try {
            Response<String> response = call.execute();
            body = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    public LoginResponse GetUser(LoginRequest request) {

        byte[] credentials = "clientId:clientSecret".getBytes();
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString(credentials);
        Call<LoginResponse> call = service.GetUser(request);
        LoginResponse body = null;
        try {
            Response<LoginResponse> response = call.execute();
            body = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    public SignUpResponse SignUp(SignUpRequest request) {

        Call<SignUpResponse> call = service.SignUp(request);
        SignUpResponse body = null;
        try {
            Response<SignUpResponse> response = call.execute();
            body = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    public ProfileCustomerDto GetProfile(String accessToken) {

        Call<ProfileCustomerDto> call = service.GetProfile("Bearer " + accessToken);
        ProfileCustomerDto body = null;
        try {
            Response<ProfileCustomerDto> response = call.execute();
            body = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    public void SetProfile(String accessToken, ProfileCustomerDto profileCustomerdto) {

        Call<Void> call = service.SetProfile("Bearer " + accessToken, profileCustomerdto);
        ProfileCustomerDto body = null;
        try {
            Response<Void> response = call.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
