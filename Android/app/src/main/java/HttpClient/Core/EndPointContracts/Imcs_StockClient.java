package HttpClient.Core.EndPointContracts;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_StockDto;
import HttpClient.Messages.mcs_StockRequest;
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

public interface Imcs_StockClient {
    @GET("mcs_Stock/{StockId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_StockDto> Get(@Header("Authorization") String token, @Path("StockId") Integer StockId);

    @GET("mcs_Stock")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<List<mcs_StockDto>> Getmcs_StockList(@Header("Authorization") String token);

    @POST("mcs_Stock/post")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_StockDto> Post(@Header("Authorization") String token, @Body mcs_StockDto data);

    @POST("mcs_Stock/put")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_StockDto> Put(@Header("Authorization") String token, @Body mcs_StockDto data);

    @POST("mcs_Stock/delete/{StockId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<Integer> Delete(@Header("Authorization") String token, @Path("StockId") Integer StockId);
}