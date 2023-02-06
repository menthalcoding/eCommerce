package View.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_CartDto;
import HttpClient.Messages.Criteria.mcs_CartCriteria;
import HttpClient.Repositories.mcs_CartRepository;
import Util.SessionManager;

public class mcs_CartViewModel extends AndroidViewModel {
    private mcs_CartRepository repository;
    private LiveData<List<mcs_CartDto>> data;

    public mcs_CartViewModel(@NonNull Application application) {
        super(application);

        repository = new mcs_CartRepository(SessionManager.ACCESS_TOKEN);
        mcs_CartCriteria criteria = new mcs_CartCriteria();
        data = (LiveData<List<mcs_CartDto>>) repository.GetList(criteria);
    }

    public mcs_CartDto get(Integer CartId) {
        mcs_CartCriteria criteria = new mcs_CartCriteria();
		criteria.CartId = CartId;
        return repository.Get(criteria);
    }

    public long insert(mcs_CartDto item) {
        return repository.Post(item);
    }

    public void update(mcs_CartDto item) {
        repository.Put(item);
    }

    public void delete(mcs_CartDto item) {
        mcs_CartCriteria criteria = new mcs_CartCriteria();
		criteria.CartId = item.getCartId();
        repository.Delete(criteria);
    }

    public LiveData<List<mcs_CartDto>> getItems() { return data; }
}