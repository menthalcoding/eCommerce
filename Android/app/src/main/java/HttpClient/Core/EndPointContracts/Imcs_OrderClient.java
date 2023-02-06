package HttpClient.Core.EndPointContracts;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_OrderDto;
import HttpClient.Messages.mcs_OrderRequest;
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

public interface Imcs_OrderClient {
    @GET("mcs_Order/{OrderId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_OrderDto> Get(@Header("Authorization") String token, @Path("OrderId") Integer OrderId);

    @GET("mcs_Order")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<List<mcs_OrderDto>> Getmcs_OrderList(@Header("Authorization") String token);

    @POST("mcs_Order/post")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_OrderDto> Post(@Header("Authorization") String token, @Body mcs_OrderDto data);

    @POST("mcs_Order/put")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_OrderDto> Put(@Header("Authorization") String token, @Body mcs_OrderDto data);

    @POST("mcs_Order/delete/{OrderId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<Integer> Delete(@Header("Authorization") String token, @Path("OrderId") Integer OrderId);
}