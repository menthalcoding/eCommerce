package View.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_PaymentDto;
import HttpClient.Messages.Criteria.mcs_PaymentCriteria;
import HttpClient.Repositories.mcs_PaymentRepository;
import Util.SessionManager;

public class mcs_PaymentViewModel extends AndroidViewModel {
    private mcs_PaymentRepository repository;
    private LiveData<List<mcs_PaymentDto>> data;

    public mcs_PaymentViewModel(@NonNull Application application) {
        super(application);

        repository = new mcs_PaymentRepository(SessionManager.ACCESS_TOKEN);
        mcs_PaymentCriteria criteria = new mcs_PaymentCriteria();
        data = (LiveData<List<mcs_PaymentDto>>) repository.GetList(criteria);
    }

    public mcs_PaymentDto get(Integer PaymentId) {
        mcs_PaymentCriteria criteria = new mcs_PaymentCriteria();
		criteria.PaymentId = PaymentId;
        return repository.Get(criteria);
    }

    public long insert(mcs_PaymentDto item) {
        return repository.Post(item);
    }

    public void update(mcs_PaymentDto item) {
        repository.Put(item);
    }

    public void delete(mcs_PaymentDto item) {
        mcs_PaymentCriteria criteria = new mcs_PaymentCriteria();
		criteria.PaymentId = item.getPaymentId();
        repository.Delete(criteria);
    }

    public LiveData<List<mcs_PaymentDto>> getItems() { return data; }
}