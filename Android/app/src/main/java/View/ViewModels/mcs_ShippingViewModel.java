package View.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_ShippingDto;
import HttpClient.Messages.Criteria.mcs_ShippingCriteria;
import HttpClient.Repositories.mcs_ShippingRepository;
import Util.SessionManager;

public class mcs_ShippingViewModel extends AndroidViewModel {
    private mcs_ShippingRepository repository;
    private LiveData<List<mcs_ShippingDto>> data;

    public mcs_ShippingViewModel(@NonNull Application application) {
        super(application);

        repository = new mcs_ShippingRepository(SessionManager.ACCESS_TOKEN);
        mcs_ShippingCriteria criteria = new mcs_ShippingCriteria();
        data = (LiveData<List<mcs_ShippingDto>>) repository.GetList(criteria);
    }

    public mcs_ShippingDto get(Integer ShippingId) {
        mcs_ShippingCriteria criteria = new mcs_ShippingCriteria();
		criteria.ShippingId = ShippingId;
        return repository.Get(criteria);
    }

    public long insert(mcs_ShippingDto item) {
        return repository.Post(item);
    }

    public void update(mcs_ShippingDto item) {
        repository.Put(item);
    }

    public void delete(mcs_ShippingDto item) {
        mcs_ShippingCriteria criteria = new mcs_ShippingCriteria();
		criteria.ShippingId = item.getShippingId();
        repository.Delete(criteria);
    }

    public LiveData<List<mcs_ShippingDto>> getItems() { return data; }
}