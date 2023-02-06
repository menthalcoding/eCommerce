package HttpClient.Repositories;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;
import HttpClient.Core.EndPointContracts.Imcs_CartClient;
import HttpClient.Core.WebClient;
import HttpClient.DataTransferObjects.mcs_CartDto;
import HttpClient.Messages.mcs_CartRequest;
import HttpClient.Messages.mcs_CartResponse;
import HttpClient.Messages.Criteria.mcs_CartCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.Core.Imcs_CartRepository;
import HttpClient.Repositories.Core.RepositoryBase;
import Util.Constant;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mcs_CartRepository extends RepositoryBase implements Imcs_CartRepository {
    Imcs_CartClient service;
    mcs_CartRequest request;
    String access_token;
    public int StatusCode;

    public mcs_CartRepository(String accessToken) {

        access_token = "Bearer " + accessToken;
        service = WebClient.createService(Imcs_CartClient.class, accessToken);
        request = new mcs_CartRequest();
        request.Criteria = new mcs_CartCriteria();
    }

    @Override
    public List<mcs_CartDto> GetList(Criteria criterion) {

        Call<List<mcs_CartDto>> call = service.Getmcs_CartList(access_token);
        List<mcs_CartDto> body = null;
        try {
            Response<List<mcs_CartDto>> response = call.execute();
            StatusCode = response.raw().code();
            body = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    @Override
    public mcs_CartDto Get(Criteria criterion) {

        mcs_CartRequest request = new mcs_CartRequest();
        request.LoadOptions = new String[] { "mcs_Cart" };
        request.Criteria = (mcs_CartCriteria)criterion;

        mcs_CartDto result = null;
        Call<mcs_CartDto> call = service.Get(access_token, request.Criteria.CartId);
        try {
            Response<mcs_CartDto> response = call.execute();
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
    public int Post(mcs_CartDto _mcs_CartDto) {

        //mcs_CartRequest request = new mcs_CartRequest();
        //request.mcs_Cart = _mcs_CartDto;

        mcs_CartDto result = null;
        Call<mcs_CartDto> call = service.Post(access_token, _mcs_CartDto);
        try {
            Response<mcs_CartDto> response = call.execute();
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
    public void Put(mcs_CartDto _mcs_CartDto) {

        //mcs_CartRequest request = new mcs_CartRequest();
        //request.mcs_Cart = _mcs_CartDto;

        mcs_CartDto result = null;
        Call<mcs_CartDto> call = service.Put(access_token, _mcs_CartDto);
        try {
            Response<mcs_CartDto> response = call.execute();
            result = response.body();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Criteria criterion) {

        mcs_CartCriteria criteria = (mcs_CartCriteria)criterion;
        mcs_CartDto result = null;
        Call<Integer> call = service.Delete(access_token, criteria.CartId);
        try {
            Response<Integer> response = call.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}