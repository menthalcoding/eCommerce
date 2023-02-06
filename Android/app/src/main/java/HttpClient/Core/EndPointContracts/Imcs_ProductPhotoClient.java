package HttpClient.Core.EndPointContracts;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_ProductPhotoDto;
import HttpClient.Messages.mcs_ProductPhotoRequest;
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

public interface Imcs_ProductPhotoClient {
    @GET("mcs_ProductPhoto/{ProductPhotoId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_ProductPhotoDto> Get(@Header("Authorization") String token, @Path("ProductPhotoId") String ProductPhotoId);

    @GET("mcs_ProductPhoto")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<List<mcs_ProductPhotoDto>> Getmcs_ProductPhotoList(@Header("Authorization") String token);

    @POST("mcs_ProductPhoto/post")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_ProductPhotoDto> Post(@Header("Authorization") String token, @Body mcs_ProductPhotoDto data);

    @POST("mcs_ProductPhoto/put")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_ProductPhotoDto> Put(@Header("Authorization") String token, @Body mcs_ProductPhotoDto data);

    @POST("mcs_ProductPhoto/delete/{ProductPhotoId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<Integer> Delete(@Header("Authorization") String token, @Path("ProductPhotoId") String ProductPhotoId);
}