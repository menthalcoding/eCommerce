package HttpClient.Repositories;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;
import HttpClient.Core.EndPointContracts.Imcs_CategoryClient;
import HttpClient.Core.WebClient;
import HttpClient.DataTransferObjects.mcs_CategoryDto;
import HttpClient.Messages.mcs_CategoryRequest;
import HttpClient.Messages.mcs_CategoryResponse;
import HttpClient.Messages.Criteria.mcs_CategoryCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.Core.Imcs_CategoryRepository;
import HttpClient.Repositories.Core.RepositoryBase;
import Util.Constant;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mcs_CategoryRepository extends RepositoryBase implements Imcs_CategoryRepository {
    Imcs_CategoryClient service;
    mcs_CategoryRequest request;
    String access_token;
    public int StatusCode;

    public mcs_CategoryRepository(String accessToken) {

        access_token = "Bearer " + accessToken;
        service = WebClient.createService(Imcs_CategoryClient.class, accessToken);
        request = new mcs_CategoryRequest();
        request.Criteria = new mcs_CategoryCriteria();
    }

    @Override
    public List<mcs_CategoryDto> GetList(Criteria criterion) {

        Call<List<mcs_CategoryDto>> call = service.Getmcs_CategoryList(access_token);
        List<mcs_CategoryDto> body = null;
        try {
            Response<List<mcs_CategoryDto>> response = call.execute();
            StatusCode = response.raw().code();
            body = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    @Override
    public mcs_CategoryDto Get(Criteria criterion) {

        mcs_CategoryRequest request = new mcs_CategoryRequest();
        request.LoadOptions = new String[] { "mcs_Category" };
        request.Criteria = (mcs_CategoryCriteria)criterion;

        mcs_CategoryDto result = null;
        Call<mcs_CategoryDto> call = service.Get(access_token, request.Criteria.CategoryId);
        try {
            Response<mcs_CategoryDto> response = call.execute();
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
    public int Post(mcs_CategoryDto _mcs_CategoryDto) {

        //mcs_CategoryRequest request = new mcs_CategoryRequest();
        //request.mcs_Category = _mcs_CategoryDto;

        mcs_CategoryDto result = null;
        Call<mcs_CategoryDto> call = service.Post(access_token, _mcs_CategoryDto);
        try {
            Response<mcs_CategoryDto> response = call.execute();
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
    public void Put(mcs_CategoryDto _mcs_CategoryDto) {

        //mcs_CategoryRequest request = new mcs_CategoryRequest();
        //request.mcs_Category = _mcs_CategoryDto;

        mcs_CategoryDto result = null;
        Call<mcs_CategoryDto> call = service.Put(access_token, _mcs_CategoryDto);
        try {
            Response<mcs_CategoryDto> response = call.execute();
            result = response.body();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Criteria criterion) {

        mcs_CategoryCriteria criteria = (mcs_CategoryCriteria)criterion;
        mcs_CategoryDto result = null;
        Call<Integer> call = service.Delete(access_token, criteria.CategoryId);
        try {
            Response<Integer> response = call.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}