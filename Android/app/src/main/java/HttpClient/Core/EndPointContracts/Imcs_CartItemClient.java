package HttpClient.Core.EndPointContracts;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_CartItemDto;
import HttpClient.Messages.mcs_CartItemRequest;
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

public interface Imcs_CartItemClient {
    @GET("mcs_CartItem/{CartItemId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_CartItemDto> Get(@Header("Authorization") String token, @Path("CartItemId") Integer CartItemId);

    @GET("mcs_CartItem")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<List<mcs_CartItemDto>> Getmcs_CartItemList(@Header("Authorization") String token);

    @POST("mcs_CartItem/post")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_CartItemDto> Post(@Header("Authorization") String token, @Body mcs_CartItemDto data);

    @POST("mcs_CartItem/put")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_CartItemDto> Put(@Header("Authorization") String token, @Body mcs_CartItemDto data);

    @POST("mcs_CartItem/delete/{CartItemId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<Integer> Delete(@Header("Authorization") String token, @Path("CartItemId") Integer CartItemId);
}