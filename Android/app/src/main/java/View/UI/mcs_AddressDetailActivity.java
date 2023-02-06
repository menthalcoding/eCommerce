package View.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.math.BigDecimal;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.R;

import HttpClient.DataTransferObjects.mcs_AddressDto;
import HttpClient.Messages.Criteria.mcs_AddressCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_AddressRepository;
import Util.Converter;
import Util.SessionManager;

public class mcs_AddressDetailActivity extends AppCompatActivity {
	TextView tvAddressId;
	TextView tvUserId;
	TextView tvName;
	TextView tvGPS;
	TextView tvCountry;
	TextView tvCity;
	TextView tvZipCode;
	TextView tvCountryCode;
	TextView tvDetail;
	TextView tvPrimary;
	TextView tvActive;
    private SessionManager session;
    private String access_token;
    mcs_AddressRepository _mcs_AddressRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_address_detail);
        getSupportActionBar().setTitle("mcs_Address Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        _mcs_AddressRepository = new mcs_AddressRepository(access_token);

		tvAddressId = (TextView) findViewById(R.id.tvAddressId);
		tvUserId = (TextView) findViewById(R.id.tvUserId);
		tvName = (TextView) findViewById(R.id.tvName);
		tvGPS = (TextView) findViewById(R.id.tvGPS);
		tvCountry = (TextView) findViewById(R.id.tvCountry);
		tvCity = (TextView) findViewById(R.id.tvCity);
		tvZipCode = (TextView) findViewById(R.id.tvZipCode);
		tvCountryCode = (TextView) findViewById(R.id.tvCountryCode);
		tvDetail = (TextView) findViewById(R.id.tvDetail);
		tvPrimary = (TextView) findViewById(R.id.tvPrimary);
		tvActive = (TextView) findViewById(R.id.tvActive);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
			Integer keyAddressId = (Integer)extras.get("AddressId");
            Getmcs_Address(keyAddressId);
        }
    }

    private void Getmcs_Address(Integer AddressId) {
        mcs_AddressCriteria c = new mcs_AddressCriteria();
		c.AddressId = AddressId;
        mcs_AddressDto data = _mcs_AddressRepository.Get(c);
        if (data != null) {
			tvAddressId.setText(String.valueOf(data.getAddressId()));
			tvUserId.setText(String.valueOf(data.getUserId()));
			tvName.setText(String.valueOf(data.getName()));
			tvGPS.setText(String.valueOf(data.getGPS()));
			tvCountry.setText(String.valueOf(data.getCountry()));
			tvCity.setText(String.valueOf(data.getCity()));
			tvZipCode.setText(String.valueOf(data.getZipCode()));
			tvCountryCode.setText(String.valueOf(data.getCountryCode()));
			tvDetail.setText(String.valueOf(data.getDetail()));
			tvPrimary.setText(String.valueOf(data.getPrimary()));
			tvActive.setText(String.valueOf(data.getActive()));
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