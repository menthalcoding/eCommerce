package HttpClient.Repositories;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;
import HttpClient.Core.EndPointContracts.Imcs_CartItemClient;
import HttpClient.Core.WebClient;
import HttpClient.DataTransferObjects.mcs_CartItemDto;
import HttpClient.Messages.mcs_CartItemRequest;
import HttpClient.Messages.mcs_CartItemResponse;
import HttpClient.Messages.Criteria.mcs_CartItemCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.Core.Imcs_CartItemRepository;
import HttpClient.Repositories.Core.RepositoryBase;
import Util.Constant;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mcs_CartItemRepository extends RepositoryBase implements Imcs_CartItemRepository {
    Imcs_CartItemClient service;
    mcs_CartItemRequest request;
    String access_token;
    public int StatusCode;

    public mcs_CartItemRepository(String accessToken) {

        access_token = "Bearer " + accessToken;
        service = WebClient.createService(Imcs_CartItemClient.class, accessToken);
        request = new mcs_CartItemRequest();
        request.Criteria = new mcs_CartItemCriteria();
    }

    @Override
    public List<mcs_CartItemDto> GetList(Criteria criterion) {

        Call<List<mcs_CartItemDto>> call = service.Getmcs_CartItemList(access_token);
        List<mcs_CartItemDto> body = null;
        try {
            Response<List<mcs_CartItemDto>> response = call.execute();
            StatusCode = response.raw().code();
            body = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    @Override
    public mcs_CartItemDto Get(Criteria criterion) {

        mcs_CartItemRequest request = new mcs_CartItemRequest();
        request.LoadOptions = new String[] { "mcs_CartItem" };
        request.Criteria = (mcs_CartItemCriteria)criterion;

        mcs_CartItemDto result = null;
        Call<mcs_CartItemDto> call = service.Get(access_token, request.Criteria.CartItemId);
        try {
            Response<mcs_CartItemDto> response = call.execute();
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
    public int Post(mcs_CartItemDto _mcs_CartItemDto) {

        //mcs_CartItemRequest request = new mcs_CartItemRequest();
        //request.mcs_CartItem = _mcs_CartItemDto;

        mcs_CartItemDto result = null;
        Call<mcs_CartItemDto> call = service.Post(access_token, _mcs_CartItemDto);
        try {
            Response<mcs_CartItemDto> response = call.execute();
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
    public void Put(mcs_CartItemDto _mcs_CartItemDto) {

        //mcs_CartItemRequest request = new mcs_CartItemRequest();
        //request.mcs_CartItem = _mcs_CartItemDto;

        mcs_CartItemDto result = null;
        Call<mcs_CartItemDto> call = service.Put(access_token, _mcs_CartItemDto);
        try {
            Response<mcs_CartItemDto> response = call.execute();
            result = response.body();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Criteria criterion) {

        mcs_CartItemCriteria criteria = (mcs_CartItemCriteria)criterion;
        mcs_CartItemDto result = null;
        Call<Integer> call = service.Delete(access_token, criteria.CartItemId);
        try {
            Response<Integer> response = call.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}