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

import HttpClient.DataTransferObjects.mcs_OrderDto;
import HttpClient.Messages.Criteria.mcs_OrderCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_OrderRepository;
import Util.Converter;
import Util.SessionManager;

public class mcs_OrderDeleteActivity extends AppCompatActivity {
	TextView tvOrderId;
	TextView tvUserId;
	TextView tvCartId;
	TextView tvCreated_at;
	TextView tvCost;
	TextView tvTax;
	TextView tvTotal;
	TextView tvPaid;
	TextView tvCurrency;
    Button btnDelete;
    private SessionManager session;
    private String access_token;
    mcs_OrderRepository _mcs_OrderRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_order_delete);
        getSupportActionBar().setTitle("mcs_Order Delete");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        _mcs_OrderRepository = new mcs_OrderRepository(access_token);

		tvOrderId = (TextView) findViewById(R.id.tvOrderId);
		tvUserId = (TextView) findViewById(R.id.tvUserId);
		tvCartId = (TextView) findViewById(R.id.tvCartId);
		tvCreated_at = (TextView) findViewById(R.id.tvCreated_at);
		tvCost = (TextView) findViewById(R.id.tvCost);
		tvTax = (TextView) findViewById(R.id.tvTax);
		tvTotal = (TextView) findViewById(R.id.tvTotal);
		tvPaid = (TextView) findViewById(R.id.tvPaid);
		tvCurrency = (TextView) findViewById(R.id.tvCurrency);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deletemcs_Order(new Integer(tvOrderId.getText().toString()));
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
			Integer keyOrderId = (Integer)extras.get("OrderId");
            Getmcs_Order(keyOrderId);
        }
    }

    private void Getmcs_Order(Integer OrderId) {
        mcs_OrderCriteria c = new mcs_OrderCriteria();
		c.OrderId = OrderId;
        mcs_OrderDto data = _mcs_OrderRepository.Get(c);
        if (data != null) {
			tvOrderId.setText(String.valueOf(data.getOrderId()));
			tvUserId.setText(String.valueOf(data.getUserId()));
			tvCartId.setText(String.valueOf(data.getCartId()));
			tvCreated_at.setText((data.getCreated_at() != null ? Converter.formatDate(data.getCreated_at()) : ""));
			tvCost.setText(String.valueOf(data.getCost()));
			tvTax.setText(String.valueOf(data.getTax()));
			tvTotal.setText(String.valueOf(data.getTotal()));
			tvPaid.setText(String.valueOf(data.getPaid()));
			tvCurrency.setText(String.valueOf(data.getCurrency()));
        }
        else {
            Toast.makeText(getApplicationContext(), "Bad Request", Toast.LENGTH_LONG).show();
        }
    }

    private void Deletemcs_Order(Integer OrderId) {
        mcs_OrderCriteria criteria = new mcs_OrderCriteria();
		criteria.OrderId = OrderId;
        _mcs_OrderRepository.Delete(criteria);

        Intent intmcs_OrderList = new Intent(getApplicationContext(), mcs_OrderListActivity.class);
        startActivity(intmcs_OrderList);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}