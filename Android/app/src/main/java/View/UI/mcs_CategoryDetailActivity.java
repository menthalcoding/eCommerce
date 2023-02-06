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

import HttpClient.DataTransferObjects.mcs_CategoryDto;
import HttpClient.Messages.Criteria.mcs_CategoryCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_CategoryRepository;
import Util.Converter;
import Util.SessionManager;

public class mcs_CategoryDetailActivity extends AppCompatActivity {
	TextView tvCategoryId;
	TextView tvCategoryParentId;
	TextView tvName;
	TextView tvDescription;
    private SessionManager session;
    private String access_token;
    mcs_CategoryRepository _mcs_CategoryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_category_detail);
        getSupportActionBar().setTitle("mcs_Category Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        _mcs_CategoryRepository = new mcs_CategoryRepository(access_token);

		tvCategoryId = (TextView) findViewById(R.id.tvCategoryId);
		tvCategoryParentId = (TextView) findViewById(R.id.tvCategoryParentId);
		tvName = (TextView) findViewById(R.id.tvName);
		tvDescription = (TextView) findViewById(R.id.tvDescription);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
			Integer keyCategoryId = (Integer)extras.get("CategoryId");
            Getmcs_Category(keyCategoryId);
        }
    }

    private void Getmcs_Category(Integer CategoryId) {
        mcs_CategoryCriteria c = new mcs_CategoryCriteria();
		c.CategoryId = CategoryId;
        mcs_CategoryDto data = _mcs_CategoryRepository.Get(c);
        if (data != null) {
			tvCategoryId.setText(String.valueOf(data.getCategoryId()));
			tvCategoryParentId.setText(String.valueOf(data.getCategoryParentId()));
			tvName.setText(String.valueOf(data.getName()));
			tvDescription.setText(String.valueOf(data.getDescription()));
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