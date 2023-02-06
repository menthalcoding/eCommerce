package HttpClient.Repositories;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;
import HttpClient.Core.EndPointContracts.Imcs_ProductPhotoClient;
import HttpClient.Core.WebClient;
import HttpClient.DataTransferObjects.mcs_ProductPhotoDto;
import HttpClient.Messages.mcs_ProductPhotoRequest;
import HttpClient.Messages.mcs_ProductPhotoResponse;
import HttpClient.Messages.Criteria.mcs_ProductPhotoCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.Core.Imcs_ProductPhotoRepository;
import HttpClient.Repositories.Core.RepositoryBase;
import Util.Constant;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mcs_ProductPhotoRepository extends RepositoryBase implements Imcs_ProductPhotoRepository {
    Imcs_ProductPhotoClient service;
    mcs_ProductPhotoRequest request;
    String access_token;
    public int StatusCode;

    public mcs_ProductPhotoRepository(String accessToken) {

        access_token = "Bearer " + accessToken;
        service = WebClient.createService(Imcs_ProductPhotoClient.class, accessToken);
        request = new mcs_ProductPhotoRequest();
        request.Criteria = new mcs_ProductPhotoCriteria();
    }

    @Override
    public List<mcs_ProductPhotoDto> GetList(Criteria criterion) {

        Call<List<mcs_ProductPhotoDto>> call = service.Getmcs_ProductPhotoList(access_token);
        List<mcs_ProductPhotoDto> body = null;
        try {
            Response<List<mcs_ProductPhotoDto>> response = call.execute();
            StatusCode = response.raw().code();
            body = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    @Override
    public mcs_ProductPhotoDto Get(Criteria criterion) {

        mcs_ProductPhotoRequest request = new mcs_ProductPhotoRequest();
        request.LoadOptions = new String[] { "mcs_ProductPhoto" };
        request.Criteria = (mcs_ProductPhotoCriteria)criterion;

        mcs_ProductPhotoDto result = null;
        Call<mcs_ProductPhotoDto> call = service.Get(access_token, request.Criteria.ProductPhotoId);
        try {
            Response<mcs_ProductPhotoDto> response = call.execute();
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
    public int Post(mcs_ProductPhotoDto _mcs_ProductPhotoDto) {

        //mcs_ProductPhotoRequest request = new mcs_ProductPhotoRequest();
        //request.mcs_ProductPhoto = _mcs_ProductPhotoDto;

        mcs_ProductPhotoDto result = null;
        Call<mcs_ProductPhotoDto> call = service.Post(access_token, _mcs_ProductPhotoDto);
        try {
            Response<mcs_ProductPhotoDto> response = call.execute();
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
    public void Put(mcs_ProductPhotoDto _mcs_ProductPhotoDto) {

        //mcs_ProductPhotoRequest request = new mcs_ProductPhotoRequest();
        //request.mcs_ProductPhoto = _mcs_ProductPhotoDto;

        mcs_ProductPhotoDto result = null;
        Call<mcs_ProductPhotoDto> call = service.Put(access_token, _mcs_ProductPhotoDto);
        try {
            Response<mcs_ProductPhotoDto> response = call.execute();
            result = response.body();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Criteria criterion) {

        mcs_ProductPhotoCriteria criteria = (mcs_ProductPhotoCriteria)criterion;
        mcs_ProductPhotoDto result = null;
        Call<Integer> call = service.Delete(access_token, criteria.ProductPhotoId);
        try {
            Response<Integer> response = call.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}