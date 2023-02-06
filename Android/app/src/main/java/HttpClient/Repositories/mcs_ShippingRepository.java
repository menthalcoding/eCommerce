package HttpClient.Repositories;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;
import HttpClient.Core.EndPointContracts.Imcs_ShippingClient;
import HttpClient.Core.WebClient;
import HttpClient.DataTransferObjects.mcs_ShippingDto;
import HttpClient.Messages.mcs_ShippingRequest;
import HttpClient.Messages.mcs_ShippingResponse;
import HttpClient.Messages.Criteria.mcs_ShippingCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.Core.Imcs_ShippingRepository;
import HttpClient.Repositories.Core.RepositoryBase;
import Util.Constant;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mcs_ShippingRepository extends RepositoryBase implements Imcs_ShippingRepository {
    Imcs_ShippingClient service;
    mcs_ShippingRequest request;
    String access_token;
    public int StatusCode;

    public mcs_ShippingRepository(String accessToken) {

        access_token = "Bearer " + accessToken;
        service = WebClient.createService(Imcs_ShippingClient.class, accessToken);
        request = new mcs_ShippingRequest();
        request.Criteria = new mcs_ShippingCriteria();
    }

    @Override
    public List<mcs_ShippingDto> GetList(Criteria criterion) {

        Call<List<mcs_ShippingDto>> call = service.Getmcs_ShippingList(access_token);
        List<mcs_ShippingDto> body = null;
        try {
            Response<List<mcs_ShippingDto>> response = call.execute();
            StatusCode = response.raw().code();
            body = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    @Override
    public mcs_ShippingDto Get(Criteria criterion) {

        mcs_ShippingRequest request = new mcs_ShippingRequest();
        request.LoadOptions = new String[] { "mcs_Shipping" };
        request.Criteria = (mcs_ShippingCriteria)criterion;

        mcs_ShippingDto result = null;
        Call<mcs_ShippingDto> call = service.Get(access_token, request.Criteria.ShippingId);
        try {
            Response<mcs_ShippingDto> response = call.execute();
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
    public int Post(mcs_ShippingDto _mcs_ShippingDto) {

        //mcs_ShippingRequest request = new mcs_ShippingRequest();
        //request.mcs_Shipping = _mcs_ShippingDto;

        mcs_ShippingDto result = null;
        Call<mcs_ShippingDto> call = service.Post(access_token, _mcs_ShippingDto);
        try {
            Response<mcs_ShippingDto> response = call.execute();
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
    public void Put(mcs_ShippingDto _mcs_ShippingDto) {

        //mcs_ShippingRequest request = new mcs_ShippingRequest();
        //request.mcs_Shipping = _mcs_ShippingDto;

        mcs_ShippingDto result = null;
        Call<mcs_ShippingDto> call = service.Put(access_token, _mcs_ShippingDto);
        try {
            Response<mcs_ShippingDto> response = call.execute();
            result = response.body();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Criteria criterion) {

        mcs_ShippingCriteria criteria = (mcs_ShippingCriteria)criterion;
        mcs_ShippingDto result = null;
        Call<Integer> call = service.Delete(access_token, criteria.ShippingId);
        try {
            Response<Integer> response = call.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}