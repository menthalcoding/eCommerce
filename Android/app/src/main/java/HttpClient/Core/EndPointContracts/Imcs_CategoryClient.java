package HttpClient.Core.EndPointContracts;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_CategoryDto;
import HttpClient.Messages.mcs_CategoryRequest;
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

public interface Imcs_CategoryClient {
    @GET("mcs_Category/{CategoryId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_CategoryDto> Get(@Header("Authorization") String token, @Path("CategoryId") Integer CategoryId);

    @GET("mcs_Category")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<List<mcs_CategoryDto>> Getmcs_CategoryList(@Header("Authorization") String token);

    @POST("mcs_Category/post")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_CategoryDto> Post(@Header("Authorization") String token, @Body mcs_CategoryDto data);

    @POST("mcs_Category/put")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_CategoryDto> Put(@Header("Authorization") String token, @Body mcs_CategoryDto data);

    @POST("mcs_Category/delete/{CategoryId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<Integer> Delete(@Header("Authorization") String token, @Path("CategoryId") Integer CategoryId);
}