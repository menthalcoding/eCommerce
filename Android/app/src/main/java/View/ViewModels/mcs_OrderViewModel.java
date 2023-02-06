package View.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_OrderDto;
import HttpClient.Messages.Criteria.mcs_OrderCriteria;
import HttpClient.Repositories.mcs_OrderRepository;
import Util.SessionManager;

public class mcs_OrderViewModel extends AndroidViewModel {
    private mcs_OrderRepository repository;
    private LiveData<List<mcs_OrderDto>> data;

    public mcs_OrderViewModel(@NonNull Application application) {
        super(application);

        repository = new mcs_OrderRepository(SessionManager.ACCESS_TOKEN);
        mcs_OrderCriteria criteria = new mcs_OrderCriteria();
        data = (LiveData<List<mcs_OrderDto>>) repository.GetList(criteria);
    }

    public mcs_OrderDto get(Integer OrderId) {
        mcs_OrderCriteria criteria = new mcs_OrderCriteria();
		criteria.OrderId = OrderId;
        return repository.Get(criteria);
    }

    public long insert(mcs_OrderDto item) {
        return repository.Post(item);
    }

    public void update(mcs_OrderDto item) {
        repository.Put(item);
    }

    public void delete(mcs_OrderDto item) {
        mcs_OrderCriteria criteria = new mcs_OrderCriteria();
		criteria.OrderId = item.getOrderId();
        repository.Delete(criteria);
    }

    public LiveData<List<mcs_OrderDto>> getItems() { return data; }
}