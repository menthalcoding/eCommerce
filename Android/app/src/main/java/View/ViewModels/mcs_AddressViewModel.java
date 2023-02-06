package View.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import HttpClient.DataTransferObjects.mcs_AddressDto;
import HttpClient.Messages.Criteria.mcs_AddressCriteria;
import HttpClient.Repositories.mcs_AddressRepository;
import Util.SessionManager;

public class mcs_AddressViewModel extends AndroidViewModel {
    private mcs_AddressRepository repository;
    private LiveData<List<mcs_AddressDto>> data;

    public mcs_AddressViewModel(@NonNull Application application) {
        super(application);

        repository = new mcs_AddressRepository(SessionManager.ACCESS_TOKEN);
        mcs_AddressCriteria criteria = new mcs_AddressCriteria();
        data = (LiveData<List<mcs_AddressDto>>) repository.GetList(criteria);
    }

    public mcs_AddressDto get(Integer AddressId) {
        mcs_AddressCriteria criteria = new mcs_AddressCriteria();
		criteria.AddressId = AddressId;
        return repository.Get(criteria);
    }

    public long insert(mcs_AddressDto item) {
        return repository.Post(item);
    }

    public void update(mcs_AddressDto item) {
        repository.Put(item);
    }

    public void delete(mcs_AddressDto item) {
        mcs_AddressCriteria criteria = new mcs_AddressCriteria();
		criteria.AddressId = item.getAddressId();
        repository.Delete(criteria);
    }

    public LiveData<List<mcs_AddressDto>> getItems() { return data; }
}