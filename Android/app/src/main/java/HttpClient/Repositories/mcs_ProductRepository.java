package HttpClient.Repositories;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;
import HttpClient.Core.EndPointContracts.Imcs_ProductClient;
import HttpClient.Core.WebClient;
import HttpClient.DataTransferObjects.mcs_ProductDto;
import HttpClient.Messages.mcs_ProductRequest;
import HttpClient.Messages.mcs_ProductResponse;
import HttpClient.Messages.Criteria.mcs_ProductCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.Core.Imcs_ProductRepository;
import HttpClient.Repositories.Core.RepositoryBase;
import Util.Constant;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mcs_ProductRepository extends RepositoryBase implements Imcs_ProductRepository {
    Imcs_ProductClient service;
    mcs_ProductRequest request;
    String access_token;
    public int StatusCode;

    public mcs_ProductRepository(String accessToken) {

        access_token = "Bearer " + accessToken;
        service = WebClient.createService(Imcs_ProductClient.class, accessToken);
        request = new mcs_ProductRequest();
        request.Criteria = new mcs_ProductCriteria();
    }

    @Override
    public List<mcs_ProductDto> GetList(Criteria criterion) {

        Call<List<mcs_ProductDto>> call = service.Getmcs_ProductList(access_token);
        List<mcs_ProductDto> body = null;
        try {
            Response<List<mcs_ProductDto>> response = call.execute();
            StatusCode = response.raw().code();
            body = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    @Override
    public mcs_ProductDto Get(Criteria criterion) {

        mcs_ProductRequest request = new mcs_ProductRequest();
        request.LoadOptions = new String[] { "mcs_Product" };
        request.Criteria = (mcs_ProductCriteria)criterion;

        mcs_ProductDto result = null;
        Call<mcs_ProductDto> call = service.Get(access_token, request.Criteria.ProductId);
        try {
            Response<mcs_ProductDto> response = call.execute();
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
    public int Post(mcs_ProductDto _mcs_ProductDto) {

        //mcs_ProductRequest request = new mcs_ProductRequest();
        //request.mcs_Product = _mcs_ProductDto;

        mcs_ProductDto result = null;
        Call<mcs_ProductDto> call = service.Post(access_token, _mcs_ProductDto);
        try {
            Response<mcs_ProductDto> response = call.execute();
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
    public void Put(mcs_ProductDto _mcs_ProductDto) {

        //mcs_ProductRequest request = new mcs_ProductRequest();
        //request.mcs_Product = _mcs_ProductDto;

        mcs_ProductDto result = null;
        Call<mcs_ProductDto> call = service.Put(access_token, _mcs_ProductDto);
        try {
            Response<mcs_ProductDto> response = call.execute();
            result = response.body();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Criteria criterion) {

        mcs_ProductCriteria criteria = (mcs_ProductCriteria)criterion;
        mcs_ProductDto result = null;
        Call<Integer> call = service.Delete(access_token, criteria.ProductId);
        try {
            Response<Integer> response = call.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}