package HttpClient.Core.EndPointContracts;

import HttpClient.DataTransferObjects.ProfileCustomerDto;
import HttpClient.Messages.LoginRequest;
import HttpClient.Messages.LoginResponse;
import HttpClient.Messages.LoginWebApiRequest;
import HttpClient.Messages.SignUpRequest;
import HttpClient.Messages.SignUpResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IAuthClient {

/*    @Headers({
            "Accept: application/json;",
            "Content-Type:  application/x-www-form-urlencoded"
    })
    @FormUrlEncoded
    @POST("authentication")
    Call<LoginResponse> Login(@Field("grant_type") String grantType,
                              @Field("username") String username,
                              @Field("password") String password,
                              @Header("Authorization") String authorization);*/

    @Headers({"Accept: application/json;"})
    @POST("getaccestokenrpc")
    Call<String> GetAccesTokenRPC(@Header("Authorization") String token);

    @POST("authentication")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<LoginResponse> Login(@Body LoginWebApiRequest request);

    @POST("getuser")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<LoginResponse> GetUser(@Body LoginRequest request);

    @POST("signup")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<SignUpResponse> SignUp(@Body SignUpRequest request);

    @POST("profile")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<ProfileCustomerDto> GetProfile(@Header("Authorization") String token);

    @POST("profile/update")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<Void> SetProfile(@Header("Authorization") String token, @Body ProfileCustomerDto profileCustomerdto);
}
