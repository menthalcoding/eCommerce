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
import Util.Converter;
import Util.SessionManager;
import Util.Validation;

public class mcs_ProductPhotoEditActivity extends AppCompatActivity {
	TextView tvProductPhotoId;
	EditText etUrl;
	Spinner spnmcs_ProductProductId;
	ArrayAdapter<String> mcs_ProductProductIdAdapter;
	List<mcs_ProductDto> _mcs_ProductList;
    Button btnEdit;
    private SessionManager session;
    private String access_token;
    private Validation validation;
    mcs_ProductPhotoRepository _mcs_ProductPhotoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_productphoto_edit);
        getSupportActionBar().setTitle("mcs_ProductPhoto Edit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        validation = new Validation(getApplicationContext());

        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        _mcs_ProductPhotoRepository = new mcs_ProductPhotoRepository(access_token);

		tvProductPhotoId = (TextView) findViewById(R.id.tvProductPhotoId);
		etUrl = (EditText) findViewById(R.id.etUrl);
		Getmcs_ProductList();
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IsValid()) {
                    mcs_ProductPhotoDto model = new mcs_ProductPhotoDto();
					model.setProductPhotoId(new String(tvProductPhotoId.getText().toString()));
					if (spnmcs_ProductProductId.getSelectedItemPosition() > 0)
						model.setProductId(_mcs_ProductList.get(spnmcs_ProductProductId.getSelectedItemPosition() - 1).getProductId());
					model.setUrl(new String(etUrl.getText().toString()));
                    Editmcs_ProductPhoto(model);
                }
            }
        });

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
			String selValProductId = "";
            for (mcs_ProductDto item : _mcs_ProductList) {
				if (item.getProductId().equals(data.getProductId())) {
					selValProductId = String.valueOf(item.getName());
			        spnmcs_ProductProductId.setSelection(mcs_ProductProductIdAdapter.getPosition(selValProductId));
                    break;
                }
			}
			etUrl.setText(String.valueOf(data.getUrl()));
        }
        else {
            Toast.makeText(getApplicationContext(), "Bad Request", Toast.LENGTH_LONG).show();
        }
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

    void Editmcs_ProductPhoto(mcs_ProductPhotoDto item) {
        if (item != null) {
            _mcs_ProductPhotoRepository.Put(item);
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
