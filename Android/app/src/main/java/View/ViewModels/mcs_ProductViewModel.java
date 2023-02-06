package View.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_ProductDto;
import HttpClient.Messages.Criteria.mcs_ProductCriteria;
import HttpClient.Repositories.mcs_ProductRepository;
import Util.SessionManager;

public class mcs_ProductViewModel extends AndroidViewModel {
    private mcs_ProductRepository repository;
    private LiveData<List<mcs_ProductDto>> data;

    public mcs_ProductViewModel(@NonNull Application application) {
        super(application);

        repository = new mcs_ProductRepository(SessionManager.ACCESS_TOKEN);
        mcs_ProductCriteria criteria = new mcs_ProductCriteria();
        data = (LiveData<List<mcs_ProductDto>>) repository.GetList(criteria);
    }

    public mcs_ProductDto get(Integer ProductId) {
        mcs_ProductCriteria criteria = new mcs_ProductCriteria();
		criteria.ProductId = ProductId;
        return repository.Get(criteria);
    }

    public long insert(mcs_ProductDto item) {
        return repository.Post(item);
    }

    public void update(mcs_ProductDto item) {
        repository.Put(item);
    }

    public void delete(mcs_ProductDto item) {
        mcs_ProductCriteria criteria = new mcs_ProductCriteria();
		criteria.ProductId = item.getProductId();
        repository.Delete(criteria);
    }

    public LiveData<List<mcs_ProductDto>> getItems() { return data; }
}