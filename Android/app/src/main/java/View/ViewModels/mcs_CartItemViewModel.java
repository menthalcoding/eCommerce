package View.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_CartItemDto;
import HttpClient.Messages.Criteria.mcs_CartItemCriteria;
import HttpClient.Repositories.mcs_CartItemRepository;
import Util.SessionManager;

public class mcs_CartItemViewModel extends AndroidViewModel {
    private mcs_CartItemRepository repository;
    private LiveData<List<mcs_CartItemDto>> data;

    public mcs_CartItemViewModel(@NonNull Application application) {
        super(application);

        repository = new mcs_CartItemRepository(SessionManager.ACCESS_TOKEN);
        mcs_CartItemCriteria criteria = new mcs_CartItemCriteria();
        data = (LiveData<List<mcs_CartItemDto>>) repository.GetList(criteria);
    }

    public mcs_CartItemDto get(Integer CartItemId) {
        mcs_CartItemCriteria criteria = new mcs_CartItemCriteria();
		criteria.CartItemId = CartItemId;
        return repository.Get(criteria);
    }

    public long insert(mcs_CartItemDto item) {
        return repository.Post(item);
    }

    public void update(mcs_CartItemDto item) {
        repository.Put(item);
    }

    public void delete(mcs_CartItemDto item) {
        mcs_CartItemCriteria criteria = new mcs_CartItemCriteria();
		criteria.CartItemId = item.getCartItemId();
        repository.Delete(criteria);
    }

    public LiveData<List<mcs_CartItemDto>> getItems() { return data; }
}