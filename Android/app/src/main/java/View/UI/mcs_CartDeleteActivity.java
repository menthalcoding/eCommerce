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

import HttpClient.DataTransferObjects.mcs_CartDto;
import HttpClient.Messages.Criteria.mcs_CartCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_CartRepository;
import Util.Converter;
import Util.SessionManager;

public class mcs_CartDeleteActivity extends AppCompatActivity {
	TextView tvCartId;
	TextView tvUserId;
	TextView tvStatus;
	TextView tvCreated_at;
	TextView tvUpdated_at;
    Button btnDelete;
    private SessionManager session;
    private String access_token;
    mcs_CartRepository _mcs_CartRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_cart_delete);
        getSupportActionBar().setTitle("mcs_Cart Delete");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        _mcs_CartRepository = new mcs_CartRepository(access_token);

		tvCartId = (TextView) findViewById(R.id.tvCartId);
		tvUserId = (TextView) findViewById(R.id.tvUserId);
		tvStatus = (TextView) findViewById(R.id.tvStatus);
		tvCreated_at = (TextView) findViewById(R.id.tvCreated_at);
		tvUpdated_at = (TextView) findViewById(R.id.tvUpdated_at);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deletemcs_Cart(new Integer(tvCartId.getText().toString()));
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
			Integer keyCartId = (Integer)extras.get("CartId");
            Getmcs_Cart(keyCartId);
        }
    }

    private void Getmcs_Cart(Integer CartId) {
        mcs_CartCriteria c = new mcs_CartCriteria();
		c.CartId = CartId;
        mcs_CartDto data = _mcs_CartRepository.Get(c);
        if (data != null) {
			tvCartId.setText(String.valueOf(data.getCartId()));
			tvUserId.setText(String.valueOf(data.getUserId()));
			tvStatus.setText(String.valueOf(data.getStatus()));
			tvCreated_at.setText((data.getCreated_at() != null ? Converter.formatDate(data.getCreated_at()) : ""));
			tvUpdated_at.setText((data.getUpdated_at() != null ? Converter.formatDate(data.getUpdated_at()) : ""));
        }
        else {
            Toast.makeText(getApplicationContext(), "Bad Request", Toast.LENGTH_LONG).show();
        }
    }

    private void Deletemcs_Cart(Integer CartId) {
        mcs_CartCriteria criteria = new mcs_CartCriteria();
		criteria.CartId = CartId;
        _mcs_CartRepository.Delete(criteria);

        Intent intmcs_CartList = new Intent(getApplicationContext(), mcs_CartListActivity.class);
        startActivity(intmcs_CartList);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}