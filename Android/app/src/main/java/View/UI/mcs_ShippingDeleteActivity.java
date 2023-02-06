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

import HttpClient.DataTransferObjects.mcs_ShippingDto;
import HttpClient.Messages.Criteria.mcs_ShippingCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_ShippingRepository;
import Util.Converter;
import Util.SessionManager;

public class mcs_ShippingDeleteActivity extends AppCompatActivity {
	TextView tvShippingId;
	TextView tvOrderId;
	TextView tvAddressId;
	TextView tvShippingMethod;
	TextView tvStatus;
	TextView tvShippingProvider;
	TextView tvCost;
    Button btnDelete;
    private SessionManager session;
    private String access_token;
    mcs_ShippingRepository _mcs_ShippingRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_shipping_delete);
        getSupportActionBar().setTitle("mcs_Shipping Delete");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        _mcs_ShippingRepository = new mcs_ShippingRepository(access_token);

		tvShippingId = (TextView) findViewById(R.id.tvShippingId);
		tvOrderId = (TextView) findViewById(R.id.tvOrderId);
		tvAddressId = (TextView) findViewById(R.id.tvAddressId);
		tvShippingMethod = (TextView) findViewById(R.id.tvShippingMethod);
		tvStatus = (TextView) findViewById(R.id.tvStatus);
		tvShippingProvider = (TextView) findViewById(R.id.tvShippingProvider);
		tvCost = (TextView) findViewById(R.id.tvCost);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deletemcs_Shipping(new Integer(tvShippingId.getText().toString()));
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
			tvOrderId.setText(String.valueOf(data.getOrderId()));
			tvAddressId.setText(String.valueOf(data.getAddressId()));
			tvShippingMethod.setText(String.valueOf(data.getShippingMethod()));
			tvStatus.setText(String.valueOf(data.getStatus()));
			tvShippingProvider.setText(String.valueOf(data.getShippingProvider()));
			tvCost.setText(String.valueOf(data.getCost()));
        }
        else {
            Toast.makeText(getApplicationContext(), "Bad Request", Toast.LENGTH_LONG).show();
        }
    }

    private void Deletemcs_Shipping(Integer ShippingId) {
        mcs_ShippingCriteria criteria = new mcs_ShippingCriteria();
		criteria.ShippingId = ShippingId;
        _mcs_ShippingRepository.Delete(criteria);

        Intent intmcs_ShippingList = new Intent(getApplicationContext(), mcs_ShippingListActivity.class);
        startActivity(intmcs_ShippingList);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}