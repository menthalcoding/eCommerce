package HttpClient.Core.EndPointContracts;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_ProductDto;
import HttpClient.Messages.mcs_ProductRequest;
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

public interface Imcs_ProductClient {
    @GET("mcs_Product/{ProductId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_ProductDto> Get(@Header("Authorization") String token, @Path("ProductId") Integer ProductId);

    @GET("mcs_Product")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<List<mcs_ProductDto>> Getmcs_ProductList(@Header("Authorization") String token);

    @POST("mcs_Product/post")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_ProductDto> Post(@Header("Authorization") String token, @Body mcs_ProductDto data);

    @POST("mcs_Product/put")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_ProductDto> Put(@Header("Authorization") String token, @Body mcs_ProductDto data);

    @POST("mcs_Product/delete/{ProductId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<Integer> Delete(@Header("Authorization") String token, @Path("ProductId") Integer ProductId);
}