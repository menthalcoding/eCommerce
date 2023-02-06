package View.UI;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.R;

import HttpClient.DataTransferObjects.mcs_ShippingDto;
import HttpClient.Messages.Criteria.mcs_ShippingCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_ShippingRepository;
import HttpClient.Repositories.mcs_OrderRepository;
import HttpClient.DataTransferObjects.mcs_OrderDto;
import HttpClient.Repositories.mcs_AddressRepository;
import HttpClient.DataTransferObjects.mcs_AddressDto;
import Util.Converter;
import Util.SessionManager;
import Util.Validation;

public class mcs_ShippingEditActivity extends AppCompatActivity {
	TextView tvShippingId;
	EditText etShippingMethod;
	EditText etStatus;
	EditText etShippingProvider;
	EditText etCost;
	Spinner spnmcs_OrderOrderId;
	Spinner spnmcs_AddressAddressId;
	ArrayAdapter<String> mcs_OrderOrderIdAdapter;
	ArrayAdapter<String> mcs_AddressAddressIdAdapter;
	List<mcs_OrderDto> _mcs_OrderList;
	List<mcs_AddressDto> _mcs_AddressList;
    Button btnEdit;
    private SessionManager session;
    private String access_token;
    private Validation validation;
    mcs_ShippingRepository _mcs_ShippingRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_shipping_edit);
        getSupportActionBar().setTitle("mcs_Shipping Edit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        validation = new Validation(getApplicationContext());

        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        _mcs_ShippingRepository = new mcs_ShippingRepository(access_token);

		tvShippingId = (TextView) findViewById(R.id.tvShippingId);
		etShippingMethod = (EditText) findViewById(R.id.etShippingMethod);
		etStatus = (EditText) findViewById(R.id.etStatus);
		etShippingProvider = (EditText) findViewById(R.id.etShippingProvider);
		etCost = (EditText) findViewById(R.id.etCost);
		Getmcs_OrderList();
		Getmcs_AddressList();
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IsValid()) {
                    mcs_ShippingDto model = new mcs_ShippingDto();
					model.setShippingId(new Integer(tvShippingId.getText().toString()));
					if (spnmcs_OrderOrderId.getSelectedItemPosition() > 0)
						model.setOrderId(_mcs_OrderList.get(spnmcs_OrderOrderId.getSelectedItemPosition() - 1).getOrderId());
					if (spnmcs_AddressAddressId.getSelectedItemPosition() > 0)
						model.setAddressId(_mcs_AddressList.get(spnmcs_AddressAddressId.getSelectedItemPosition() - 1).getAddressId());
					model.setShippingMethod(new String(etShippingMethod.getText().toString()));
					model.setStatus(new String(etStatus.getText().toString()));
					model.setShippingProvider(new String(etShippingProvider.getText().toString()));
					if (etCost.getText().length() > 0)
						model.setCost(new BigDecimal(etCost.getText().toString()));
                    Editmcs_Shipping(model);
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
			Integer keyShippingId = (Integer)extras.get("ShippingId");
            Getmcs_Shipping(keyShippingId);
        }
    }

    private void Getmcs_Shipping(Integer ShippingId) {
        mcs_ShippingCriteria c = new mcs_ShippingCriteria();
		c.ShippingId = ShippingId;
        mcs_ShippingDto data = _mcs_ShippingRepository.Get(c);
        if (data != null) {
			tvShippingId.setText(String.valueOf(data.getShippingId()));
			String selValOrderId = "";
            for (mcs_OrderDto item : _mcs_OrderList) {
				if (item.getOrderId().equals(data.getOrderId())) {
					selValOrderId = String.valueOf(item.getOrderId());
			        spnmcs_OrderOrderId.setSelection(mcs_OrderOrderIdAdapter.getPosition(selValOrderId));
                    break;
                }
			}
			String selValAddressId = "";
            for (mcs_AddressDto item : _mcs_AddressList) {
				if (item.getAddressId().equals(data.getAddressId())) {
					selValAddressId = String.valueOf(item.getName());
			        spnmcs_AddressAddressId.setSelection(mcs_AddressAddressIdAdapter.getPosition(selValAddressId));
                    break;
                }
			}
			etShippingMethod.setText(String.valueOf(data.getShippingMethod()));
			etStatus.setText(String.valueOf(data.getStatus()));
			etShippingProvider.setText(String.valueOf(data.getShippingProvider()));
			etCost.setText(String.valueOf(data.getCost()));
        }
        else {
            Toast.makeText(getApplicationContext(), "Bad Request", Toast.LENGTH_LONG).show();
        }
    }

	boolean IsValid() {
		boolean result = true;
		if(!validation.isValid("Required", (spnmcs_OrderOrderId.getSelectedItemPosition() > 0 ? String.valueOf(_mcs_OrderList.get(spnmcs_OrderOrderId.getSelectedItemPosition() - 1).getOrderId()) : null), false, "Integer", null)) {
			spnmcs_OrderOrderId.setFocusable(true);
			spnmcs_OrderOrderId.setFocusableInTouchMode(true);
			spnmcs_OrderOrderId.requestFocus();
			((TextView)spnmcs_OrderOrderId.getSelectedView()).setError(validation.Message);
			result = false;
		}
		else {
			spnmcs_OrderOrderId.clearFocus();
			((TextView)spnmcs_OrderOrderId.getSelectedView()).setError(null);
		}
		if(!validation.isValid("Required", (spnmcs_AddressAddressId.getSelectedItemPosition() > 0 ? String.valueOf(_mcs_AddressList.get(spnmcs_AddressAddressId.getSelectedItemPosition() - 1).getAddressId()) : null), false, "Integer", null)) {
			spnmcs_AddressAddressId.setFocusable(true);
			spnmcs_AddressAddressId.setFocusableInTouchMode(true);
			spnmcs_AddressAddressId.requestFocus();
			((TextView)spnmcs_AddressAddressId.getSelectedView()).setError(validation.Message);
			result = false;
		}
		else {
			spnmcs_AddressAddressId.clearFocus();
			((TextView)spnmcs_AddressAddressId.getSelectedView()).setError(null);
		}
		if(!validation.isValid("None", etShippingMethod.getText().toString(), true, "String", "50")) {
			etShippingMethod.requestFocus();
			etShippingMethod.setError(validation.Message);
			result = false;
		}
		else {
			etShippingMethod.clearFocus();
			etShippingMethod.setError(null);
		}
		if(!validation.isValid("None", etStatus.getText().toString(), true, "String", "50")) {
			etStatus.requestFocus();
			etStatus.setError(validation.Message);
			result = false;
		}
		else {
			etStatus.clearFocus();
			etStatus.setError(null);
		}
		if(!validation.isValid("None", etShippingProvider.getText().toString(), true, "String", "50")) {
			etShippingProvider.requestFocus();
			etShippingProvider.setError(validation.Message);
			result = false;
		}
		else {
			etShippingProvider.clearFocus();
			etShippingProvider.setError(null);
		}
		if(!validation.isValid("None", etCost.getText().toString(), true, "BigDecimal", null)) {
			etCost.requestFocus();
			etCost.setError(validation.Message);
			result = false;
		}
		else {
			etCost.clearFocus();
			etCost.setError(null);
		}

		return result;
	}

    void Editmcs_Shipping(mcs_ShippingDto item) {
        if (item != null) {
            _mcs_ShippingRepository.Put(item);
            Intent intmcs_ShippingList = new Intent(getApplicationContext(), mcs_ShippingListActivity.class);
            startActivity(intmcs_ShippingList);
        }
        else {
            Toast.makeText(getApplicationContext(), "Bad Request", Toast.LENGTH_LONG).show();
        }
    }

	void Getmcs_OrderList() {
		mcs_OrderRepository _mcs_OrderRepository = new mcs_OrderRepository(access_token);
		_mcs_OrderList =  _mcs_OrderRepository.GetList(new Criteria());
		String[] arrmcs_OrderOrderId = new String[_mcs_OrderList.size() + 1];
		arrmcs_OrderOrderId[0] = getResources().getString(R.string.prompt_spinner_select);
		for (int e = 0; e < _mcs_OrderList.size(); e++) {
			arrmcs_OrderOrderId[e + 1] = String.valueOf(_mcs_OrderList.get(e).getOrderId());
		}
		spnmcs_OrderOrderId = (Spinner) findViewById(R.id.spnmcs_OrderOrderId);
		mcs_OrderOrderIdAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrmcs_OrderOrderId);
		mcs_OrderOrderIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnmcs_OrderOrderId.setPrompt("Please Select!");
		spnmcs_OrderOrderId.setAdapter(mcs_OrderOrderIdAdapter);
	}

	void Getmcs_AddressList() {
		mcs_AddressRepository _mcs_AddressRepository = new mcs_AddressRepository(access_token);
		_mcs_AddressList =  _mcs_AddressRepository.GetList(new Criteria());
		String[] arrmcs_AddressAddressId = new String[_mcs_AddressList.size() + 1];
		arrmcs_AddressAddressId[0] = getResources().getString(R.string.prompt_spinner_select);
		for (int e = 0; e < _mcs_AddressList.size(); e++) {
			arrmcs_AddressAddressId[e + 1] = String.valueOf(_mcs_AddressList.get(e).getName());
		}
		spnmcs_AddressAddressId = (Spinner) findViewById(R.id.spnmcs_AddressAddressId);
		mcs_AddressAddressIdAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrmcs_AddressAddressId);
		mcs_AddressAddressIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnmcs_AddressAddressId.setPrompt("Please Select!");
		spnmcs_AddressAddressId.setAdapter(mcs_AddressAddressIdAdapter);
	}

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
