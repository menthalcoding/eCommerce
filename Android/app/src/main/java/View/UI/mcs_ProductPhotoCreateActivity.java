package View.UI;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.R;

import HttpClient.DataTransferObjects.mcs_ProductPhotoDto;
import HttpClient.Messages.Criteria.mcs_ProductPhotoCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_ProductPhotoRepository;
import HttpClient.Repositories.mcs_ProductRepository;
import HttpClient.DataTransferObjects.mcs_ProductDto;
import Util.SessionManager;
import Util.Validation;

public class mcs_ProductPhotoCreateActivity extends AppCompatActivity {

	EditText etUrl;
	Spinner spnmcs_ProductProductId;
	ArrayAdapter<String> mcs_ProductProductIdAdapter;
	List<mcs_ProductDto> _mcs_ProductList;
    Button btnCreate;
    private SessionManager session;
    private String access_token;
    private Validation validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_productphoto_create);
        getSupportActionBar().setTitle("mcs_ProductPhoto Create");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        validation = new Validation(getApplicationContext());
		etUrl = (EditText) findViewById(R.id.etUrl);
		Getmcs_ProductList();
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IsValid()) {
                    mcs_ProductPhotoDto model = new mcs_ProductPhotoDto();
					if (spnmcs_ProductProductId.getSelectedItemPosition() > 0)
						model.setProductId(_mcs_ProductList.get(spnmcs_ProductProductId.getSelectedItemPosition() - 1).getProductId());
					model.setUrl(new String(etUrl.getText().toString()));
                    Createmcs_ProductPhoto(model);
                }
            }
        });
    }

	boolean IsValid() {
		boolean result = true;
		if(!validation.isValid("Required", (spnmcs_ProductProductId.getSelectedItemPosition() > 0 ? String.valueOf(_mcs_ProductList.get(spnmcs_ProductProductId.getSelectedItemPosition() - 1).getProductId()) : null), false, "Integer", null)) {
			spnmcs_ProductProductId.setFocusable(true);
			spnmcs_ProductProductId.setFocusableInTouchMode(true);
			spnmcs_ProductProductId.requestFocus();
			((TextView)spnmcs_ProductProductId.getSelectedView()).setError(validation.Message);
			result = false;
		}
		else {
			spnmcs_ProductProductId.clearFocus();
			((TextView)spnmcs_ProductProductId.getSelectedView()).setError(null);
		}
		if(!validation.isValid("Required", etUrl.getText().toString(), false, "String", "500")) {
			etUrl.requestFocus();
			etUrl.setError(validation.Message);
			result = false;
		}
		else {
			etUrl.clearFocus();
			etUrl.setError(null);
		}

		return result;
	}

    void Createmcs_ProductPhoto(mcs_ProductPhotoDto item) {
        mcs_ProductPhotoRepository _mcs_ProductPhotoRepository = new mcs_ProductPhotoRepository(access_token);
        if (_mcs_ProductPhotoRepository.Post(item) > 0) {
            Intent intmcs_ProductPhotoList = new Intent(getApplicationContext(), mcs_ProductPhotoListActivity.class);
            startActivity(intmcs_ProductPhotoList);
        }
        else {
            Toast.makeText(getApplicationContext(), "Bad Request", Toast.LENGTH_LONG).show();
        }
    }

	void Getmcs_ProductList() {
		mcs_ProductRepository _mcs_ProductRepository = new mcs_ProductRepository(access_token);
		_mcs_ProductList =  _mcs_ProductRepository.GetList(new Criteria());
		String[] arrmcs_ProductProductId = new String[_mcs_ProductList.size() + 1];
		arrmcs_ProductProductId[0] = getResources().getString(R.string.prompt_spinner_select);
		for (int e = 0; e < _mcs_ProductList.size(); e++) {
			arrmcs_ProductProductId[e + 1] = String.valueOf(_mcs_ProductList.get(e).getName());
		}
		spnmcs_ProductProductId = (Spinner) findViewById(R.id.spnmcs_ProductProductId);
		mcs_ProductProductIdAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrmcs_ProductProductId);
		mcs_ProductProductIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnmcs_ProductProductId.setPrompt("Please Select!");
		spnmcs_ProductProductId.setAdapter(mcs_ProductProductIdAdapter);
	}

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
