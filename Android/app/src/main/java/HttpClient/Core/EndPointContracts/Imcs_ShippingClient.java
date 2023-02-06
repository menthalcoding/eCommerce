package HttpClient.Core.EndPointContracts;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_ShippingDto;
import HttpClient.Messages.mcs_ShippingRequest;
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

public interface Imcs_ShippingClient {
    @GET("mcs_Shipping/{ShippingId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_ShippingDto> Get(@Header("Authorization") String token, @Path("ShippingId") Integer ShippingId);

    @GET("mcs_Shipping")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<List<mcs_ShippingDto>> Getmcs_ShippingList(@Header("Authorization") String token);

    @POST("mcs_Shipping/post")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_ShippingDto> Post(@Header("Authorization") String token, @Body mcs_ShippingDto data);

    @POST("mcs_Shipping/put")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_ShippingDto> Put(@Header("Authorization") String token, @Body mcs_ShippingDto data);

    @POST("mcs_Shipping/delete/{ShippingId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<Integer> Delete(@Header("Authorization") String token, @Path("ShippingId") Integer ShippingId);
}