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
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Calendar;

import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.R;

import HttpClient.DataTransferObjects.mcs_ProductDto;
import HttpClient.Messages.Criteria.mcs_ProductCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_ProductRepository;
import HttpClient.Repositories.mcs_CategoryRepository;
import HttpClient.DataTransferObjects.mcs_CategoryDto;
import Util.Converter;
import Util.SessionManager;
import Util.Validation;

public class mcs_ProductEditActivity extends AppCompatActivity {
	TextView tvProductId;
	EditText etName;
	EditText etTitle;
	EditText etDescription;
	EditText etPrice;
	EditText etDefaultPhoto;
	EditText etStatus;
	EditText etCreated_at;
	EditText etUpdated_at;
	Spinner spnmcs_CategoryCategoryId;
	ArrayAdapter<String> mcs_CategoryCategoryIdAdapter;
	List<mcs_CategoryDto> _mcs_CategoryList;
    Button btnEdit;
	DatePickerDialog datePickerDialog;
	Calendar calendar = Calendar.getInstance();
	int year, month, dayOfMonth;
    private SessionManager session;
    private String access_token;
    private Validation validation;
    mcs_ProductRepository _mcs_ProductRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_product_edit);
        getSupportActionBar().setTitle("mcs_Product Edit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        validation = new Validation(getApplicationContext());

        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        _mcs_ProductRepository = new mcs_ProductRepository(access_token);

		tvProductId = (TextView) findViewById(R.id.tvProductId);
		etName = (EditText) findViewById(R.id.etName);
		etTitle = (EditText) findViewById(R.id.etTitle);
		etDescription = (EditText) findViewById(R.id.etDescription);
		etPrice = (EditText) findViewById(R.id.etPrice);
		etDefaultPhoto = (EditText) findViewById(R.id.etDefaultPhoto);
		etStatus = (EditText) findViewById(R.id.etStatus);
		etCreated_at = (EditText) findViewById(R.id.etCreated_at);
        etCreated_at.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
                        //etCreated_at.setInputType(InputType.TYPE_NULL);
				        year = calendar.get(Calendar.YEAR);
				        month = calendar.get(Calendar.MONTH);
				        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
				        datePickerDialog = new DatePickerDialog(mcs_ProductEditActivity.this,
					        new DatePickerDialog.OnDateSetListener() {
						        @Override
						        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
									String selectedDate = day + "/" + (month+1) + "/" + year;
									if (validation.isValidDate(selectedDate)) {
										etCreated_at.setText(selectedDate);
										etCreated_at.clearFocus();
										etCreated_at.setError(null);
									}
									else {
										etCreated_at.requestFocus();
										etCreated_at.setError(validation.Message);
									}
						        }
					        }, year, month, dayOfMonth);
				        datePickerDialog.show();
                    break;
                }
				return false;
			}
		});
		etUpdated_at = (EditText) findViewById(R.id.etUpdated_at);
        etUpdated_at.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
                        //etUpdated_at.setInputType(InputType.TYPE_NULL);
				        year = calendar.get(Calendar.YEAR);
				        month = calendar.get(Calendar.MONTH);
				        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
				        datePickerDialog = new DatePickerDialog(mcs_ProductEditActivity.this,
					        new DatePickerDialog.OnDateSetListener() {
						        @Override
						        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
									String selectedDate = day + "/" + (month+1) + "/" + year;
									if (validation.isValidDate(selectedDate)) {
										etUpdated_at.setText(selectedDate);
										etUpdated_at.clearFocus();
										etUpdated_at.setError(null);
									}
									else {
										etUpdated_at.requestFocus();
										etUpdated_at.setError(validation.Message);
									}
						        }
					        }, year, month, dayOfMonth);
				        datePickerDialog.show();
                    break;
                }
				return false;
			}
		});
		Getmcs_CategoryList();
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IsValid()) {
                    mcs_ProductDto model = new mcs_ProductDto();
					model.setProductId(new Integer(tvProductId.getText().toString()));
					model.setName(new String(etName.getText().toString()));
					model.setTitle(new String(etTitle.getText().toString()));
					model.setDescription(new String(etDescription.getText().toString()));
					if (spnmcs_CategoryCategoryId.getSelectedItemPosition() > 0)
						model.setCategoryId(_mcs_CategoryList.get(spnmcs_CategoryCategoryId.getSelectedItemPosition() - 1).getCategoryId());
					if (etPrice.getText().length() > 0)
						model.setPrice(new BigDecimal(etPrice.getText().toString()));
					model.setDefaultPhoto(new String(etDefaultPhoto.getText().toString()));
					model.setStatus(new String(etStatus.getText().toString()));
					if (etCreated_at.getText().length() > 0)
						model.setCreated_at(new Date(etCreated_at.getText().toString()));
					if (etUpdated_at.getText().length() > 0)
						model.setUpdated_at(new Date(etUpdated_at.getText().toString()));
                    Editmcs_Product(model);
                }
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
			etName.setText(String.valueOf(data.getName()));
			etTitle.setText(String.valueOf(data.getTitle()));
			etDescription.setText(String.valueOf(data.getDescription()));
			String selValCategoryId = "";
            for (mcs_CategoryDto item : _mcs_CategoryList) {
				if (item.getCategoryId().equals(data.getCategoryId())) {
					selValCategoryId = String.valueOf(item.getName());
			        spnmcs_CategoryCategoryId.setSelection(mcs_CategoryCategoryIdAdapter.getPosition(selValCategoryId));
                    break;
                }
			}
			etPrice.setText(String.valueOf(data.getPrice()));
			etDefaultPhoto.setText(String.valueOf(data.getDefaultPhoto()));
			etStatus.setText(String.valueOf(data.getStatus()));
			etCreated_at.setText(Converter.formatDate(data.getCreated_at()));
			etUpdated_at.setText(Converter.formatDate(data.getUpdated_at()));
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
		if(!validation.isValid("None", etTitle.getText().toString(), true, "String", "500")) {
			etTitle.requestFocus();
			etTitle.setError(validation.Message);
			result = false;
		}
		else {
			etTitle.clearFocus();
			etTitle.setError(null);
		}
		if(!validation.isValid("None", etDescription.getText().toString(), true, "String", "500")) {
			etDescription.requestFocus();
			etDescription.setError(validation.Message);
			result = false;
		}
		else {
			etDescription.clearFocus();
			etDescription.setError(null);
		}
		if(!validation.isValid("Required", (spnmcs_CategoryCategoryId.getSelectedItemPosition() > 0 ? String.valueOf(_mcs_CategoryList.get(spnmcs_CategoryCategoryId.getSelectedItemPosition() - 1).getCategoryId()) : null), false, "Integer", null)) {
			spnmcs_CategoryCategoryId.setFocusable(true);
			spnmcs_CategoryCategoryId.setFocusableInTouchMode(true);
			spnmcs_CategoryCategoryId.requestFocus();
			((TextView)spnmcs_CategoryCategoryId.getSelectedView()).setError(validation.Message);
			result = false;
		}
		else {
			spnmcs_CategoryCategoryId.clearFocus();
			((TextView)spnmcs_CategoryCategoryId.getSelectedView()).setError(null);
		}
		if(!validation.isValid("None", etPrice.getText().toString(), false, "BigDecimal", null)) {
			etPrice.requestFocus();
			etPrice.setError(validation.Message);
			result = false;
		}
		else {
			etPrice.clearFocus();
			etPrice.setError(null);
		}
		if(!validation.isValid("None", etDefaultPhoto.getText().toString(), true, "String", "500")) {
			etDefaultPhoto.requestFocus();
			etDefaultPhoto.setError(validation.Message);
			result = false;
		}
		else {
			etDefaultPhoto.clearFocus();
			etDefaultPhoto.setError(null);
		}
		if(!validation.isValid("None", etStatus.getText().toString(), true, "String", "255")) {
			etStatus.requestFocus();
			etStatus.setError(validation.Message);
			result = false;
		}
		else {
			etStatus.clearFocus();
			etStatus.setError(null);
		}
		if(!validation.isValid("None", etCreated_at.getText().toString(), false, "Date", null)) {
			etCreated_at.requestFocus();
			etCreated_at.setError(validation.Message);
			result = false;
		}
		else {
			etCreated_at.clearFocus();
			etCreated_at.setError(null);
		}
		if(!validation.isValid("None", etUpdated_at.getText().toString(), true, "Date", null)) {
			etUpdated_at.requestFocus();
			etUpdated_at.setError(validation.Message);
			result = false;
		}
		else {
			etUpdated_at.clearFocus();
			etUpdated_at.setError(null);
		}

		return result;
	}

    void Editmcs_Product(mcs_ProductDto item) {
        if (item != null) {
            _mcs_ProductRepository.Put(item);
            Intent intmcs_ProductList = new Intent(getApplicationContext(), mcs_ProductListActivity.class);
            startActivity(intmcs_ProductList);
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
