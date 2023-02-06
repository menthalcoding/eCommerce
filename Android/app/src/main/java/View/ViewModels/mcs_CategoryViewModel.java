package View.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_CategoryDto;
import HttpClient.Messages.Criteria.mcs_CategoryCriteria;
import HttpClient.Repositories.mcs_CategoryRepository;
import Util.SessionManager;

public class mcs_CategoryViewModel extends AndroidViewModel {
    private mcs_CategoryRepository repository;
    private LiveData<List<mcs_CategoryDto>> data;

    public mcs_CategoryViewModel(@NonNull Application application) {
        super(application);

        repository = new mcs_CategoryRepository(SessionManager.ACCESS_TOKEN);
        mcs_CategoryCriteria criteria = new mcs_CategoryCriteria();
        data = (LiveData<List<mcs_CategoryDto>>) repository.GetList(criteria);
    }

    public mcs_CategoryDto get(Integer CategoryId) {
        mcs_CategoryCriteria criteria = new mcs_CategoryCriteria();
		criteria.CategoryId = CategoryId;
        return repository.Get(criteria);
    }

    public long insert(mcs_CategoryDto item) {
        return repository.Post(item);
    }

    public void update(mcs_CategoryDto item) {
        repository.Put(item);
    }

    public void delete(mcs_CategoryDto item) {
        mcs_CategoryCriteria criteria = new mcs_CategoryCriteria();
		criteria.CategoryId = item.getCategoryId();
        repository.Delete(criteria);
    }

    public LiveData<List<mcs_CategoryDto>> getItems() { return data; }
}