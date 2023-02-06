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

import HttpClient.DataTransferObjects.mcs_ProductDto;
import HttpClient.Messages.Criteria.mcs_ProductCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_ProductRepository;
import Util.Converter;
import Util.SessionManager;

public class mcs_ProductDeleteActivity extends AppCompatActivity {
	TextView tvProductId;
	TextView tvName;
	TextView tvTitle;
	TextView tvDescription;
	TextView tvCategoryId;
	TextView tvPrice;
	TextView tvDefaultPhoto;
	TextView tvStatus;
	TextView tvCreated_at;
	TextView tvUpdated_at;
    Button btnDelete;
    private SessionManager session;
    private String access_token;
    mcs_ProductRepository _mcs_ProductRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_product_delete);
        getSupportActionBar().setTitle("mcs_Product Delete");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        _mcs_ProductRepository = new mcs_ProductRepository(access_token);

		tvProductId = (TextView) findViewById(R.id.tvProductId);
		tvName = (TextView) findViewById(R.id.tvName);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvDescription = (TextView) findViewById(R.id.tvDescription);
		tvCategoryId = (TextView) findViewById(R.id.tvCategoryId);
		tvPrice = (TextView) findViewById(R.id.tvPrice);
		tvDefaultPhoto = (TextView) findViewById(R.id.tvDefaultPhoto);
		tvStatus = (TextView) findViewById(R.id.tvStatus);
		tvCreated_at = (TextView) findViewById(R.id.tvCreated_at);
		tvUpdated_at = (TextView) findViewById(R.id.tvUpdated_at);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deletemcs_Product(new Integer(tvProductId.getText().toString()));
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
			Integer keyProductId = (Integer)extras.get("ProductId");
            Getmcs_Product(keyProductId);
        }
    }

    private void Getmcs_Product(Integer ProductId) {
        mcs_ProductCriteria c = new mcs_ProductCriteria();
		c.ProductId = ProductId;
        mcs_ProductDto data = _mcs_ProductRepository.Get(c);
        if (data != null) {
			tvProductId.setText(String.valueOf(data.getProductId()));
			tvName.setText(String.valueOf(data.getName()));
			tvTitle.setText(String.valueOf(data.getTitle()));
			tvDescription.setText(String.valueOf(data.getDescription()));
			tvCategoryId.setText(String.valueOf(data.getCategoryId()));
			tvPrice.setText(String.valueOf(data.getPrice()));
			tvDefaultPhoto.setText(String.valueOf(data.getDefaultPhoto()));
			tvStatus.setText(String.valueOf(data.getStatus()));
			tvCreated_at.setText((data.getCreated_at() != null ? Converter.formatDate(data.getCreated_at()) : ""));
			tvUpdated_at.setText((data.getUpdated_at() != null ? Converter.formatDate(data.getUpdated_at()) : ""));
        }
        else {
            Toast.makeText(getApplicationContext(), "Bad Request", Toast.LENGTH_LONG).show();
        }
    }

    private void Deletemcs_Product(Integer ProductId) {
        mcs_ProductCriteria criteria = new mcs_ProductCriteria();
		criteria.ProductId = ProductId;
        _mcs_ProductRepository.Delete(criteria);

        Intent intmcs_ProductList = new Intent(getApplicationContext(), mcs_ProductListActivity.class);
        startActivity(intmcs_ProductList);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}