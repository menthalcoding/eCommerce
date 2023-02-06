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

import HttpClient.DataTransferObjects.mcs_PaymentDto;
import HttpClient.Messages.Criteria.mcs_PaymentCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_PaymentRepository;
import Util.Converter;
import Util.SessionManager;

public class mcs_PaymentDeleteActivity extends AppCompatActivity {
	TextView tvPaymentId;
	TextView tvUserId;
	TextView tvPaymentType;
	TextView tvCreated_at;
	TextView tvAmount;
	TextView tvCurrency;
	TextView tvOrderId;
	TextView tvAddressId;
    Button btnDelete;
    private SessionManager session;
    private String access_token;
    mcs_PaymentRepository _mcs_PaymentRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_payment_delete);
        getSupportActionBar().setTitle("mcs_Payment Delete");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        _mcs_PaymentRepository = new mcs_PaymentRepository(access_token);

		tvPaymentId = (TextView) findViewById(R.id.tvPaymentId);
		tvUserId = (TextView) findViewById(R.id.tvUserId);
		tvPaymentType = (TextView) findViewById(R.id.tvPaymentType);
		tvCreated_at = (TextView) findViewById(R.id.tvCreated_at);
		tvAmount = (TextView) findViewById(R.id.tvAmount);
		tvCurrency = (TextView) findViewById(R.id.tvCurrency);
		tvOrderId = (TextView) findViewById(R.id.tvOrderId);
		tvAddressId = (TextView) findViewById(R.id.tvAddressId);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deletemcs_Payment(new Integer(tvPaymentId.getText().toString()));
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
			Integer keyPaymentId = (Integer)extras.get("PaymentId");
            Getmcs_Payment(keyPaymentId);
        }
    }

    private void Getmcs_Payment(Integer PaymentId) {
        mcs_PaymentCriteria c = new mcs_PaymentCriteria();
		c.PaymentId = PaymentId;
        mcs_PaymentDto data = _mcs_PaymentRepository.Get(c);
        if (data != null) {
			tvPaymentId.setText(String.valueOf(data.getPaymentId()));
			tvUserId.setText(String.valueOf(data.getUserId()));
			tvPaymentType.setText(String.valueOf(data.getPaymentType()));
			tvCreated_at.setText((data.getCreated_at() != null ? Converter.formatDate(data.getCreated_at()) : ""));
			tvAmount.setText(String.valueOf(data.getAmount()));
			tvCurrency.setText(String.valueOf(data.getCurrency()));
			tvOrderId.setText(String.valueOf(data.getOrderId()));
			tvAddressId.setText(String.valueOf(data.getAddressId()));
        }
        else {
            Toast.makeText(getApplicationContext(), "Bad Request", Toast.LENGTH_LONG).show();
        }
    }

    private void Deletemcs_Payment(Integer PaymentId) {
        mcs_PaymentCriteria criteria = new mcs_PaymentCriteria();
		criteria.PaymentId = PaymentId;
        _mcs_PaymentRepository.Delete(criteria);

        Intent intmcs_PaymentList = new Intent(getApplicationContext(), mcs_PaymentListActivity.class);
        startActivity(intmcs_PaymentList);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}