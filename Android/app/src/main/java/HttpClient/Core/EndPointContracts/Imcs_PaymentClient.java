package HttpClient.Core.EndPointContracts;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_PaymentDto;
import HttpClient.Messages.mcs_PaymentRequest;
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

public interface Imcs_PaymentClient {
    @GET("mcs_Payment/{PaymentId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_PaymentDto> Get(@Header("Authorization") String token, @Path("PaymentId") Integer PaymentId);

    @GET("mcs_Payment")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<List<mcs_PaymentDto>> Getmcs_PaymentList(@Header("Authorization") String token);

    @POST("mcs_Payment/post")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_PaymentDto> Post(@Header("Authorization") String token, @Body mcs_PaymentDto data);

    @POST("mcs_Payment/put")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_PaymentDto> Put(@Header("Authorization") String token, @Body mcs_PaymentDto data);

    @POST("mcs_Payment/delete/{PaymentId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<Integer> Delete(@Header("Authorization") String token, @Path("PaymentId") Integer PaymentId);
}