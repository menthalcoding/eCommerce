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

import HttpClient.DataTransferObjects.mcs_CartItemDto;
import HttpClient.Messages.Criteria.mcs_CartItemCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_CartItemRepository;
import Util.Converter;
import Util.SessionManager;

public class mcs_CartItemDeleteActivity extends AppCompatActivity {
	TextView tvCartItemId;
	TextView tvCartId;
	TextView tvProductId;
	TextView tvQuantity;
	TextView tvCreated_at;
	TextView tvUpdated_at;
    Button btnDelete;
    private SessionManager session;
    private String access_token;
    mcs_CartItemRepository _mcs_CartItemRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_cartıtem_delete);
        getSupportActionBar().setTitle("mcs_CartItem Delete");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        _mcs_CartItemRepository = new mcs_CartItemRepository(access_token);

		tvCartItemId = (TextView) findViewById(R.id.tvCartItemId);
		tvCartId = (TextView) findViewById(R.id.tvCartId);
		tvProductId = (TextView) findViewById(R.id.tvProductId);
		tvQuantity = (TextView) findViewById(R.id.tvQuantity);
		tvCreated_at = (TextView) findViewById(R.id.tvCreated_at);
		tvUpdated_at = (TextView) findViewById(R.id.tvUpdated_at);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deletemcs_CartItem(new Integer(tvCartItemId.getText().toString()));
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
			Integer keyCartItemId = (Integer)extras.get("CartItemId");
            Getmcs_CartItem(keyCartItemId);
        }
    }

    private void Getmcs_CartItem(Integer CartItemId) {
        mcs_CartItemCriteria c = new mcs_CartItemCriteria();
		c.CartItemId = CartItemId;
        mcs_CartItemDto data = _mcs_CartItemRepository.Get(c);
        if (data != null) {
			tvCartItemId.setText(String.valueOf(data.getCartItemId()));
			tvCartId.setText(String.valueOf(data.getCartId()));
			tvProductId.setText(String.valueOf(data.getProductId()));
			tvQuantity.setText(String.valueOf(data.getQuantity()));
			tvCreated_at.setText((data.getCreated_at() != null ? Converter.formatDate(data.getCreated_at()) : ""));
			tvUpdated_at.setText((data.getUpdated_at() != null ? Converter.formatDate(data.getUpdated_at()) : ""));
        }
        else {
            Toast.makeText(getApplicationContext(), "Bad Request", Toast.LENGTH_LONG).show();
        }
    }

    private void Deletemcs_CartItem(Integer CartItemId) {
        mcs_CartItemCriteria criteria = new mcs_CartItemCriteria();
		criteria.CartItemId = CartItemId;
        _mcs_CartItemRepository.Delete(criteria);

        Intent intmcs_CartItemList = new Intent(getApplicationContext(), mcs_CartItemListActivity.class);
        startActivity(intmcs_CartItemList);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}