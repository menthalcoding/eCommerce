package HttpClient.Repositories;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;
import HttpClient.Core.EndPointContracts.Imcs_OrderClient;
import HttpClient.Core.WebClient;
import HttpClient.DataTransferObjects.mcs_OrderDto;
import HttpClient.Messages.mcs_OrderRequest;
import HttpClient.Messages.mcs_OrderResponse;
import HttpClient.Messages.Criteria.mcs_OrderCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.Core.Imcs_OrderRepository;
import HttpClient.Repositories.Core.RepositoryBase;
import Util.Constant;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mcs_OrderRepository extends RepositoryBase implements Imcs_OrderRepository {
    Imcs_OrderClient service;
    mcs_OrderRequest request;
    String access_token;
    public int StatusCode;

    public mcs_OrderRepository(String accessToken) {

        access_token = "Bearer " + accessToken;
        service = WebClient.createService(Imcs_OrderClient.class, accessToken);
        request = new mcs_OrderRequest();
        request.Criteria = new mcs_OrderCriteria();
    }

    @Override
    public List<mcs_OrderDto> GetList(Criteria criterion) {

        Call<List<mcs_OrderDto>> call = service.Getmcs_OrderList(access_token);
        List<mcs_OrderDto> body = null;
        try {
            Response<List<mcs_OrderDto>> response = call.execute();
            StatusCode = response.raw().code();
            body = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    @Override
    public mcs_OrderDto Get(Criteria criterion) {

        mcs_OrderRequest request = new mcs_OrderRequest();
        request.LoadOptions = new String[] { "mcs_Order" };
        request.Criteria = (mcs_OrderCriteria)criterion;

        mcs_OrderDto result = null;
        Call<mcs_OrderDto> call = service.Get(access_token, request.Criteria.OrderId);
        try {
            Response<mcs_OrderDto> response = call.execute();
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
    public int Post(mcs_OrderDto _mcs_OrderDto) {

        //mcs_OrderRequest request = new mcs_OrderRequest();
        //request.mcs_Order = _mcs_OrderDto;

        mcs_OrderDto result = null;
        Call<mcs_OrderDto> call = service.Post(access_token, _mcs_OrderDto);
        try {
            Response<mcs_OrderDto> response = call.execute();
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
    public void Put(mcs_OrderDto _mcs_OrderDto) {

        //mcs_OrderRequest request = new mcs_OrderRequest();
        //request.mcs_Order = _mcs_OrderDto;

        mcs_OrderDto result = null;
        Call<mcs_OrderDto> call = service.Put(access_token, _mcs_OrderDto);
        try {
            Response<mcs_OrderDto> response = call.execute();
            result = response.body();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Criteria criterion) {

        mcs_OrderCriteria criteria = (mcs_OrderCriteria)criterion;
        mcs_OrderDto result = null;
        Call<Integer> call = service.Delete(access_token, criteria.OrderId);
        try {
            Response<Integer> response = call.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}