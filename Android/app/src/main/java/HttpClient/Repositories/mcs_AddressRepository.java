package HttpClient.Repositories;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;
import HttpClient.Core.EndPointContracts.Imcs_AddressClient;
import HttpClient.Core.WebClient;
import HttpClient.DataTransferObjects.mcs_AddressDto;
import HttpClient.Messages.mcs_AddressRequest;
import HttpClient.Messages.mcs_AddressResponse;
import HttpClient.Messages.Criteria.mcs_AddressCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.Core.Imcs_AddressRepository;
import HttpClient.Repositories.Core.RepositoryBase;
import Util.Constant;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mcs_AddressRepository extends RepositoryBase implements Imcs_AddressRepository {
    Imcs_AddressClient service;
    mcs_AddressRequest request;
    String access_token;
    public int StatusCode;

    public mcs_AddressRepository(String accessToken) {

        access_token = "Bearer " + accessToken;
        service = WebClient.createService(Imcs_AddressClient.class, accessToken);
        request = new mcs_AddressRequest();
        request.Criteria = new mcs_AddressCriteria();
    }

    @Override
    public List<mcs_AddressDto> GetList(Criteria criterion) {

        Call<List<mcs_AddressDto>> call = service.Getmcs_AddressList(access_token);
        List<mcs_AddressDto> body = null;
        try {
            Response<List<mcs_AddressDto>> response = call.execute();
            StatusCode = response.raw().code();
            body = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    @Override
    public mcs_AddressDto Get(Criteria criterion) {

        mcs_AddressRequest request = new mcs_AddressRequest();
        request.LoadOptions = new String[] { "mcs_Address" };
        request.Criteria = (mcs_AddressCriteria)criterion;

        mcs_AddressDto result = null;
        Call<mcs_AddressDto> call = service.Get(access_token, request.Criteria.AddressId);
        try {
            Response<mcs_AddressDto> response = call.execute();
            result = response.body();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return  result;
    }

    @Override
    public int GetCount(Criteria criterion) {
        return 0;
    }

    @Override
    public int Post(mcs_AddressDto _mcs_AddressDto) {

        //mcs_AddressRequest request = new mcs_AddressRequest();
        //request.mcs_Address = _mcs_AddressDto;

        mcs_AddressDto result = null;
        Call<mcs_AddressDto> call = service.Post(access_token, _mcs_AddressDto);
        try {
            Response<mcs_AddressDto> response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                return 1;
            }
            //result = response.body();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public void Put(mcs_AddressDto _mcs_AddressDto) {

        //mcs_AddressRequest request = new mcs_AddressRequest();
        //request.mcs_Address = _mcs_AddressDto;

        mcs_AddressDto result = null;
        Call<mcs_AddressDto> call = service.Put(access_token, _mcs_AddressDto);
        try {
            Response<mcs_AddressDto> response = call.execute();
            result = response.body();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Criteria criterion) {

        mcs_AddressCriteria criteria = (mcs_AddressCriteria)criterion;
        mcs_AddressDto result = null;
        Call<Integer> call = service.Delete(access_token, criteria.AddressId);
        try {
            Response<Integer> response = call.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}