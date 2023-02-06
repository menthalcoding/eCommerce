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

import HttpClient.DataTransferObjects.mcs_ProductPhotoDto;
import HttpClient.Messages.Criteria.mcs_ProductPhotoCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_ProductPhotoRepository;
import Util.Converter;
import Util.SessionManager;

public class mcs_ProductPhotoDetailActivity extends AppCompatActivity {
	TextView tvProductPhotoId;
	TextView tvProductId;
	TextView tvUrl;
    private SessionManager session;
    private String access_token;
    mcs_ProductPhotoRepository _mcs_ProductPhotoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_productphoto_detail);
        getSupportActionBar().setTitle("mcs_ProductPhoto Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        _mcs_ProductPhotoRepository = new mcs_ProductPhotoRepository(access_token);

		tvProductPhotoId = (TextView) findViewById(R.id.tvProductPhotoId);
		tvProductId = (TextView) findViewById(R.id.tvProductId);
		tvUrl = (TextView) findViewById(R.id.tvUrl);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
			String keyProductPhotoId = (String)extras.get("ProductPhotoId");
            Getmcs_ProductPhoto(keyProductPhotoId);
        }
    }

    private void Getmcs_ProductPhoto(String ProductPhotoId) {
        mcs_ProductPhotoCriteria c = new mcs_ProductPhotoCriteria();
		c.ProductPhotoId = ProductPhotoId;
        mcs_ProductPhotoDto data = _mcs_ProductPhotoRepository.Get(c);
        if (data != null) {
			tvProductPhotoId.setText(String.valueOf(data.getProductPhotoId()));
			tvProductId.setText(String.valueOf(data.getProductId()));
			tvUrl.setText(String.valueOf(data.getUrl()));
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