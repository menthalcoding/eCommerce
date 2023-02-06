package View.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_ProductPhotoDto;
import HttpClient.Messages.Criteria.mcs_ProductPhotoCriteria;
import HttpClient.Repositories.mcs_ProductPhotoRepository;
import Util.SessionManager;

public class mcs_ProductPhotoViewModel extends AndroidViewModel {
    private mcs_ProductPhotoRepository repository;
    private LiveData<List<mcs_ProductPhotoDto>> data;

    public mcs_ProductPhotoViewModel(@NonNull Application application) {
        super(application);

        repository = new mcs_ProductPhotoRepository(SessionManager.ACCESS_TOKEN);
        mcs_ProductPhotoCriteria criteria = new mcs_ProductPhotoCriteria();
        data = (LiveData<List<mcs_ProductPhotoDto>>) repository.GetList(criteria);
    }

    public mcs_ProductPhotoDto get(String ProductPhotoId) {
        mcs_ProductPhotoCriteria criteria = new mcs_ProductPhotoCriteria();
		criteria.ProductPhotoId = ProductPhotoId;
        return repository.Get(criteria);
    }

    public long insert(mcs_ProductPhotoDto item) {
        return repository.Post(item);
    }

    public void update(mcs_ProductPhotoDto item) {
        repository.Put(item);
    }

    public void delete(mcs_ProductPhotoDto item) {
        mcs_ProductPhotoCriteria criteria = new mcs_ProductPhotoCriteria();
		criteria.ProductPhotoId = item.getProductPhotoId();
        repository.Delete(criteria);
    }

    public LiveData<List<mcs_ProductPhotoDto>> getItems() { return data; }
}