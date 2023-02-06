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

import HttpClient.DataTransferObjects.mcs_CategoryDto;
import HttpClient.Messages.Criteria.mcs_CategoryCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_CategoryRepository;
import HttpClient.Repositories.mcs_CategoryRepository;
import HttpClient.DataTransferObjects.mcs_CategoryDto;
import Util.SessionManager;
import Util.Validation;

public class mcs_CategoryCreateActivity extends AppCompatActivity {

	EditText etName;
	EditText etDescription;
	Spinner spnmcs_CategoryCategoryId;
	ArrayAdapter<String> mcs_CategoryCategoryIdAdapter;
	List<mcs_CategoryDto> _mcs_CategoryList;
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
        setContentView(R.layout.activity_mcs_category_create);
        getSupportActionBar().setTitle("mcs_Category Create");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        validation = new Validation(getApplicationContext());
		etName = (EditText) findViewById(R.id.etName);
		etDescription = (EditText) findViewById(R.id.etDescription);
		Getmcs_CategoryList();
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IsValid()) {
                    mcs_CategoryDto model = new mcs_CategoryDto();
					if (spnmcs_CategoryCategoryId.getSelectedItemPosition() > 0)
						model.setCategoryParentId(_mcs_CategoryList.get(spnmcs_CategoryCategoryId.getSelectedItemPosition() - 1).getCategoryId());
					model.setName(new String(etName.getText().toString()));
					model.setDescription(new String(etDescription.getText().toString()));
                    Createmcs_Category(model);
                }
            }
        });
    }

	boolean IsValid() {
		boolean result = true;
		if(!validation.isValid("Required", etName.getText().toString(), false, "String", "255")) {
			etName.requestFocus();
			etName.setError(validation.Message);
			result = false;
		}
		else {
			etName.clearFocus();
			etName.setError(null);
		}

		return result;
	}

    void Createmcs_Category(mcs_CategoryDto item) {
        mcs_CategoryRepository _mcs_CategoryRepository = new mcs_CategoryRepository(access_token);
        if (_mcs_CategoryRepository.Post(item) > 0) {
            Intent intmcs_CategoryList = new Intent(getApplicationContext(), mcs_CategoryListActivity.class);
            startActivity(intmcs_CategoryList);
        }
        else {
            Toast.makeText(getApplicationContext(), "Bad Request", Toast.LENGTH_LONG).show();
        }
    }

	void Getmcs_CategoryList() {
		mcs_CategoryRepository _mcs_CategoryRepository = new mcs_CategoryRepository(access_token);
		_mcs_CategoryList =  _mcs_CategoryRepository.GetList(new Criteria());
		String[] arrmcs_CategoryCategoryId = new String[_mcs_CategoryList.size() + 1];
		arrmcs_CategoryCategoryId[0] = getResources().getString(R.string.prompt_spinner_select);
		for (int e = 0; e < _mcs_CategoryList.size(); e++) {
			arrmcs_CategoryCategoryId[e + 1] = String.valueOf(_mcs_CategoryList.get(e).getName());
		}
		spnmcs_CategoryCategoryId = (Spinner) findViewById(R.id.spnmcs_CategoryCategoryId);
		mcs_CategoryCategoryIdAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrmcs_CategoryCategoryId);
		mcs_CategoryCategoryIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnmcs_CategoryCategoryId.setPrompt("Please Select!");
		spnmcs_CategoryCategoryId.setAdapter(mcs_CategoryCategoryIdAdapter);
	}

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
