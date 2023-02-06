package HttpClient.Core.EndPointContracts;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_AddressDto;
import HttpClient.Messages.mcs_AddressRequest;
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

public interface Imcs_AddressClient {
    @GET("mcs_Address/{AddressId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_AddressDto> Get(@Header("Authorization") String token, @Path("AddressId") Integer AddressId);

    @GET("mcs_Address")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<List<mcs_AddressDto>> Getmcs_AddressList(@Header("Authorization") String token);

    @POST("mcs_Address/post")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_AddressDto> Post(@Header("Authorization") String token, @Body mcs_AddressDto data);

    @POST("mcs_Address/put")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<mcs_AddressDto> Put(@Header("Authorization") String token, @Body mcs_AddressDto data);

    @POST("mcs_Address/delete/{AddressId}")
    @Headers({ "Content-Type: application/json;charset=UTF-8" })
    Call<Integer> Delete(@Header("Authorization") String token, @Path("AddressId") Integer AddressId);
}