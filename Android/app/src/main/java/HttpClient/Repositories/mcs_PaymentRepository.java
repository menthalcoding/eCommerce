package HttpClient.Repositories;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;
import HttpClient.Core.EndPointContracts.Imcs_PaymentClient;
import HttpClient.Core.WebClient;
import HttpClient.DataTransferObjects.mcs_PaymentDto;
import HttpClient.Messages.mcs_PaymentRequest;
import HttpClient.Messages.mcs_PaymentResponse;
import HttpClient.Messages.Criteria.mcs_PaymentCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.Core.Imcs_PaymentRepository;
import HttpClient.Repositories.Core.RepositoryBase;
import Util.Constant;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mcs_PaymentRepository extends RepositoryBase implements Imcs_PaymentRepository {
    Imcs_PaymentClient service;
    mcs_PaymentRequest request;
    String access_token;
    public int StatusCode;

    public mcs_PaymentRepository(String accessToken) {

        access_token = "Bearer " + accessToken;
        service = WebClient.createService(Imcs_PaymentClient.class, accessToken);
        request = new mcs_PaymentRequest();
        request.Criteria = new mcs_PaymentCriteria();
    }

    @Override
    public List<mcs_PaymentDto> GetList(Criteria criterion) {

        Call<List<mcs_PaymentDto>> call = service.Getmcs_PaymentList(access_token);
        List<mcs_PaymentDto> body = null;
        try {
            Response<List<mcs_PaymentDto>> response = call.execute();
            StatusCode = response.raw().code();
            body = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    @Override
    public mcs_PaymentDto Get(Criteria criterion) {

        mcs_PaymentRequest request = new mcs_PaymentRequest();
        request.LoadOptions = new String[] { "mcs_Payment" };
        request.Criteria = (mcs_PaymentCriteria)criterion;

        mcs_PaymentDto result = null;
        Call<mcs_PaymentDto> call = service.Get(access_token, request.Criteria.PaymentId);
        try {
            Response<mcs_PaymentDto> response = call.execute();
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
    public int Post(mcs_PaymentDto _mcs_PaymentDto) {

        //mcs_PaymentRequest request = new mcs_PaymentRequest();
        //request.mcs_Payment = _mcs_PaymentDto;

        mcs_PaymentDto result = null;
        Call<mcs_PaymentDto> call = service.Post(access_token, _mcs_PaymentDto);
        try {
            Response<mcs_PaymentDto> response = call.execute();
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
    public void Put(mcs_PaymentDto _mcs_PaymentDto) {

        //mcs_PaymentRequest request = new mcs_PaymentRequest();
        //request.mcs_Payment = _mcs_PaymentDto;

        mcs_PaymentDto result = null;
        Call<mcs_PaymentDto> call = service.Put(access_token, _mcs_PaymentDto);
        try {
            Response<mcs_PaymentDto> response = call.execute();
            result = response.body();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Criteria criterion) {

        mcs_PaymentCriteria criteria = (mcs_PaymentCriteria)criterion;
        mcs_PaymentDto result = null;
        Call<Integer> call = service.Delete(access_token, criteria.PaymentId);
        try {
            Response<Integer> response = call.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}