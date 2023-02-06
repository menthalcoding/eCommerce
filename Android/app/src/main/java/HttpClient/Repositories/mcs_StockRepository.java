package HttpClient.Repositories;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;
import HttpClient.Core.EndPointContracts.Imcs_StockClient;
import HttpClient.Core.WebClient;
import HttpClient.DataTransferObjects.mcs_StockDto;
import HttpClient.Messages.mcs_StockRequest;
import HttpClient.Messages.mcs_StockResponse;
import HttpClient.Messages.Criteria.mcs_StockCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.Core.Imcs_StockRepository;
import HttpClient.Repositories.Core.RepositoryBase;
import Util.Constant;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mcs_StockRepository extends RepositoryBase implements Imcs_StockRepository {
    Imcs_StockClient service;
    mcs_StockRequest request;
    String access_token;
    public int StatusCode;

    public mcs_StockRepository(String accessToken) {

        access_token = "Bearer " + accessToken;
        service = WebClient.createService(Imcs_StockClient.class, accessToken);
        request = new mcs_StockRequest();
        request.Criteria = new mcs_StockCriteria();
    }

    @Override
    public List<mcs_StockDto> GetList(Criteria criterion) {

        Call<List<mcs_StockDto>> call = service.Getmcs_StockList(access_token);
        List<mcs_StockDto> body = null;
        try {
            Response<List<mcs_StockDto>> response = call.execute();
            StatusCode = response.raw().code();
            body = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    @Override
    public mcs_StockDto Get(Criteria criterion) {

        mcs_StockRequest request = new mcs_StockRequest();
        request.LoadOptions = new String[] { "mcs_Stock" };
        request.Criteria = (mcs_StockCriteria)criterion;

        mcs_StockDto result = null;
        Call<mcs_StockDto> call = service.Get(access_token, request.Criteria.StockId);
        try {
            Response<mcs_StockDto> response = call.execute();
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
    public int Post(mcs_StockDto _mcs_StockDto) {

        //mcs_StockRequest request = new mcs_StockRequest();
        //request.mcs_Stock = _mcs_StockDto;

        mcs_StockDto result = null;
        Call<mcs_StockDto> call = service.Post(access_token, _mcs_StockDto);
        try {
            Response<mcs_StockDto> response = call.execute();
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
    public void Put(mcs_StockDto _mcs_StockDto) {

        //mcs_StockRequest request = new mcs_StockRequest();
        //request.mcs_Stock = _mcs_StockDto;

        mcs_StockDto result = null;
        Call<mcs_StockDto> call = service.Put(access_token, _mcs_StockDto);
        try {
            Response<mcs_StockDto> response = call.execute();
            result = response.body();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Criteria criterion) {

        mcs_StockCriteria criteria = (mcs_StockCriteria)criterion;
        mcs_StockDto result = null;
        Call<Integer> call = service.Delete(access_token, criteria.StockId);
        try {
            Response<Integer> response = call.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}