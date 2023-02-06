package View.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_StockDto;
import HttpClient.Messages.Criteria.mcs_StockCriteria;
import HttpClient.Repositories.mcs_StockRepository;
import Util.SessionManager;

public class mcs_StockViewModel extends AndroidViewModel {
    private mcs_StockRepository repository;
    private LiveData<List<mcs_StockDto>> data;

    public mcs_StockViewModel(@NonNull Application application) {
        super(application);

        repository = new mcs_StockRepository(SessionManager.ACCESS_TOKEN);
        mcs_StockCriteria criteria = new mcs_StockCriteria();
        data = (LiveData<List<mcs_StockDto>>) repository.GetList(criteria);
    }

    public mcs_StockDto get(Integer StockId) {
        mcs_StockCriteria criteria = new mcs_StockCriteria();
		criteria.StockId = StockId;
        return repository.Get(criteria);
    }

    public long insert(mcs_StockDto item) {
        return repository.Post(item);
    }

    public void update(mcs_StockDto item) {
        repository.Put(item);
    }

    public void delete(mcs_StockDto item) {
        mcs_StockCriteria criteria = new mcs_StockCriteria();
		criteria.StockId = item.getStockId();
        repository.Delete(criteria);
    }

    public LiveData<List<mcs_StockDto>> getItems() { return data; }
}