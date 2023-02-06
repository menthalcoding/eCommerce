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

import HttpClient.DataTransferObjects.mcs_AddressDto;
import HttpClient.Messages.Criteria.mcs_AddressCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_AddressRepository;
import Util.SessionManager;
import Util.Validation;

public class mcs_AddressCreateActivity extends AppCompatActivity {

	EditText etUserId;
	EditText etName;
	EditText etGPS;
	EditText etCountry;
	EditText etCity;
	EditText etZipCode;
	EditText etCountryCode;
	EditText etDetail;
	EditText etPrimary;
	EditText etActive;
    Button btnCreate;
    private SessionManager session;
    private String access_token;
    private Validation validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_address_create);
        getSupportActionBar().setTitle("mcs_Address Create");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        validation = new Validation(getApplicationContext());
		etUserId = (EditText) findViewById(R.id.etUserId);
		etName = (EditText) findViewById(R.id.etName);
		etGPS = (EditText) findViewById(R.id.etGPS);
		etCountry = (EditText) findViewById(R.id.etCountry);
		etCity = (EditText) findViewById(R.id.etCity);
		etZipCode = (EditText) findViewById(R.id.etZipCode);
		etCountryCode = (EditText) findViewById(R.id.etCountryCode);
		etDetail = (EditText) findViewById(R.id.etDetail);
		etPrimary = (EditText) findViewById(R.id.etPrimary);
		etActive = (EditText) findViewById(R.id.etActive);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IsValid()) {
                    mcs_AddressDto model = new mcs_AddressDto();
					model.setUserId(new String(etUserId.getText().toString()));
					model.setName(new String(etName.getText().toString()));
					model.setGPS(new String(etGPS.getText().toString()));
					model.setCountry(new String(etCountry.getText().toString()));
					model.setCity(new String(etCity.getText().toString()));
					model.setZipCode(new String(etZipCode.getText().toString()));
					model.setCountryCode(new String(etCountryCode.getText().toString()));
					model.setDetail(new String(etDetail.getText().toString()));
					if (etPrimary.getText().length() > 0)
						model.setPrimary(new Boolean(etPrimary.getText().toString()));
					if (etActive.getText().length() > 0)
						model.setActive(new Boolean(etActive.getText().toString()));
                    Createmcs_Address(model);
                }
            }
        });
    }

	boolean IsValid() {
		boolean result = true;
		if(!validation.isValid("Required", etUserId.getText().toString(), false, "String", "450")) {
			etUserId.requestFocus();
			etUserId.setError(validation.Message);
			result = false;
		}
		else {
			etUserId.clearFocus();
			etUserId.setError(null);
		}
		if(!validation.isValid("None", etName.getText().toString(), true, "String", "50")) {
			etName.requestFocus();
			etName.setError(validation.Message);
			result = false;
		}
		else {
			etName.clearFocus();
			etName.setError(null);
		}
		if(!validation.isValid("None", etGPS.getText().toString(), true, "String", "255")) {
			etGPS.requestFocus();
			etGPS.setError(validation.Message);
			result = false;
		}
		else {
			etGPS.clearFocus();
			etGPS.setError(null);
		}
		if(!validation.isValid("None", etCountry.getText().toString(), true, "String", "50")) {
			etCountry.requestFocus();
			etCountry.setError(validation.Message);
			result = false;
		}
		else {
			etCountry.clearFocus();
			etCountry.setError(null);
		}
		if(!validation.isValid("None", etCity.getText().toString(), true, "String", "50")) {
			etCity.requestFocus();
			etCity.setError(validation.Message);
			result = false;
		}
		else {
			etCity.clearFocus();
			etCity.setError(null);
		}
		if(!validation.isValid("None", etZipCode.getText().toString(), true, "String", "50")) {
			etZipCode.requestFocus();
			etZipCode.setError(validation.Message);
			result = false;
		}
		else {
			etZipCode.clearFocus();
			etZipCode.setError(null);
		}
		if(!validation.isValid("None", etCountryCode.getText().toString(), true, "String", "50")) {
			etCountryCode.requestFocus();
			etCountryCode.setError(validation.Message);
			result = false;
		}
		else {
			etCountryCode.clearFocus();
			etCountryCode.setError(null);
		}
		if(!validation.isValid("None", etDetail.getText().toString(), true, "String", "255")) {
			etDetail.requestFocus();
			etDetail.setError(validation.Message);
			result = false;
		}
		else {
			etDetail.clearFocus();
			etDetail.setError(null);
		}
		if(!validation.isValid("None", etPrimary.getText().toString(), true, "Boolean", null)) {
			etPrimary.requestFocus();
			etPrimary.setError(validation.Message);
			result = false;
		}
		else {
			etPrimary.clearFocus();
			etPrimary.setError(null);
		}
		if(!validation.isValid("None", etActive.getText().toString(), true, "Boolean", null)) {
			etActive.requestFocus();
			etActive.setError(validation.Message);
			result = false;
		}
		else {
			etActive.clearFocus();
			etActive.setError(null);
		}

		return result;
	}

    void Createmcs_Address(mcs_AddressDto item) {
        mcs_AddressRepository _mcs_AddressRepository = new mcs_AddressRepository(access_token);
        if (_mcs_AddressRepository.Post(item) > 0) {
            Intent intmcs_AddressList = new Intent(getApplicationContext(), mcs_AddressListActivity.class);
            startActivity(intmcs_AddressList);
        }
        else {
            Toast.makeText(getApplicationContext(), "Bad Request", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
