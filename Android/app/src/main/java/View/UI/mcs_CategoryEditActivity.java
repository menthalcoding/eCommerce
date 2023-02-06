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
import Util.Converter;
import Util.SessionManager;
import Util.Validation;

public class mcs_CategoryEditActivity extends AppCompatActivity {
	TextView tvCategoryId;
	EditText etName;
	EditText etDescription;
	Spinner spnmcs_CategoryCategoryId;
	ArrayAdapter<String> mcs_CategoryCategoryIdAdapter;
	List<mcs_CategoryDto> _mcs_CategoryList;
    Button btnEdit;
    private SessionManager session;
    private String access_token;
    private Validation validation;
    mcs_CategoryRepository _mcs_CategoryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_category_edit);
        getSupportActionBar().setTitle("mcs_Category Edit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        validation = new Validation(getApplicationContext());

        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        _mcs_CategoryRepository = new mcs_CategoryRepository(access_token);

		tvCategoryId = (TextView) findViewById(R.id.tvCategoryId);
		etName = (EditText) findViewById(R.id.etName);
		etDescription = (EditText) findViewById(R.id.etDescription);
		Getmcs_CategoryList();
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IsValid()) {
                    mcs_CategoryDto model = new mcs_CategoryDto();
					model.setCategoryId(new Integer(tvCategoryId.getText().toString()));
					if (spnmcs_CategoryCategoryId.getSelectedItemPosition() > 0)
						model.setCategoryParentId(_mcs_CategoryList.get(spnmcs_CategoryCategoryId.getSelectedItemPosition() - 1).getCategoryId());
					model.setName(new String(etName.getText().toString()));
					model.setDescription(new String(etDescription.getText().toString()));
                    Editmcs_Category(model);
                }
            }
        });

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
			String selValCategoryParentId = "";
            for (mcs_CategoryDto item : _mcs_CategoryList) {
				if (item.getCategoryId().equals(data.getCategoryParentId())) {
					selValCategoryParentId = String.valueOf(item.getName());
			        spnmcs_CategoryCategoryId.setSelection(mcs_CategoryCategoryIdAdapter.getPosition(selValCategoryParentId));
                    break;
                }
			}
			etName.setText(String.valueOf(data.getName()));
			etDescription.setText(String.valueOf(data.getDescription()));
        }
        else {
            Toast.makeText(getApplicationContext(), "Bad Request", Toast.LENGTH_LONG).show();
        }
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

    void Editmcs_Category(mcs_CategoryDto item) {
        if (item != null) {
            _mcs_CategoryRepository.Put(item);
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
