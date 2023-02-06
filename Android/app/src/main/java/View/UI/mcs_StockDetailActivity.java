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

import HttpClient.DataTransferObjects.mcs_StockDto;
import HttpClient.Messages.Criteria.mcs_StockCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_StockRepository;
import Util.Converter;
import Util.SessionManager;

public class mcs_StockDetailActivity extends AppCompatActivity {
	TextView tvStockId;
	TextView tvProductId;
	TextView tvQuantity;
	TextView tvEntryPrice;
	TextView tvEntryDate;
    private SessionManager session;
    private String access_token;
    mcs_StockRepository _mcs_StockRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_stock_detail);
        getSupportActionBar().setTitle("mcs_Stock Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        _mcs_StockRepository = new mcs_StockRepository(access_token);

		tvStockId = (TextView) findViewById(R.id.tvStockId);
		tvProductId = (TextView) findViewById(R.id.tvProductId);
		tvQuantity = (TextView) findViewById(R.id.tvQuantity);
		tvEntryPrice = (TextView) findViewById(R.id.tvEntryPrice);
		tvEntryDate = (TextView) findViewById(R.id.tvEntryDate);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
			Integer keyStockId = (Integer)extras.get("StockId");
            Getmcs_Stock(keyStockId);
        }
    }

    private void Getmcs_Stock(Integer StockId) {
        mcs_StockCriteria c = new mcs_StockCriteria();
		c.StockId = StockId;
        mcs_StockDto data = _mcs_StockRepository.Get(c);
        if (data != null) {
			tvStockId.setText(String.valueOf(data.getStockId()));
			tvProductId.setText(String.valueOf(data.getProductId()));
			tvQuantity.setText(String.valueOf(data.getQuantity()));
			tvEntryPrice.setText(String.valueOf(data.getEntryPrice()));
			tvEntryDate.setText((data.getEntryDate() != null ? Converter.formatDate(data.getEntryDate()) : ""));
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