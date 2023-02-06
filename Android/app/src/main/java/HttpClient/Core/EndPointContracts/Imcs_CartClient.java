package HttpClient.Core.EndPointContracts;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_CartDto;
import HttpClient.Messages.mcs_CartRequest;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Imcs_CartClient {
    @GET("mcs_Cart/{CartId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_CartDto> Get(@Header("Authorization") String token, @Path("CartId") Integer CartId);

    @GET("mcs_Cart")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<List<mcs_CartDto>> Getmcs_CartList(@Header("Authorization") String token);

    @POST("mcs_Cart/post")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_CartDto> Post(@Header("Authorization") String token, @Body mcs_CartDto data);

    @POST("mcs_Cart/put")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_CartDto> Put(@Header("Authorization") String token, @Body mcs_CartDto data);

    @POST("mcs_Cart/delete/{CartId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<Integer> Delete(@Header("Authorization") String token, @Path("CartId") Integer CartId);
}