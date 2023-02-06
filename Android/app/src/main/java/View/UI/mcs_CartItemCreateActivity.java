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

import HttpClient.DataTransferObjects.mcs_CartItemDto;
import HttpClient.Messages.Criteria.mcs_CartItemCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_CartItemRepository;
import HttpClient.Repositories.mcs_CartRepository;
import HttpClient.DataTransferObjects.mcs_CartDto;
import HttpClient.Repositories.mcs_ProductRepository;
import HttpClient.DataTransferObjects.mcs_ProductDto;
import Util.SessionManager;
import Util.Validation;

public class mcs_CartItemCreateActivity extends AppCompatActivity {

	EditText etQuantity;
	EditText etCreated_at;
	EditText etUpdated_at;
	Spinner spnmcs_CartCartId;
	Spinner spnmcs_ProductProductId;
	ArrayAdapter<String> mcs_CartCartIdAdapter;
	ArrayAdapter<String> mcs_ProductProductIdAdapter;
	List<mcs_CartDto> _mcs_CartList;
	List<mcs_ProductDto> _mcs_ProductList;
    Button btnCreate;
	DatePickerDialog datePickerDialog;
	Calendar calendar = Calendar.getInstance();
	int year, month, dayOfMonth;
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
        setContentView(R.layout.activity_mcs_cartıtem_create);
        getSupportActionBar().setTitle("mcs_CartItem Create");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        validation = new Validation(getApplicationContext());
		etQuantity = (EditText) findViewById(R.id.etQuantity);
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
				        datePickerDialog = new DatePickerDialog(mcs_CartItemCreateActivity.this,
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
				        datePickerDialog = new DatePickerDialog(mcs_CartItemCreateActivity.this,
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
		Getmcs_CartList();
		Getmcs_ProductList();
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IsValid()) {
                    mcs_CartItemDto model = new mcs_CartItemDto();
					if (spnmcs_CartCartId.getSelectedItemPosition() > 0)
						model.setCartId(_mcs_CartList.get(spnmcs_CartCartId.getSelectedItemPosition() - 1).getCartId());
					if (spnmcs_ProductProductId.getSelectedItemPosition() > 0)
						model.setProductId(_mcs_ProductList.get(spnmcs_ProductProductId.getSelectedItemPosition() - 1).getProductId());
					if (etQuantity.getText().length() > 0)
						model.setQuantity(new Integer(etQuantity.getText().toString()));
					if (etCreated_at.getText().length() > 0)
						model.setCreated_at(new Date(etCreated_at.getText().toString()));
					if (etUpdated_at.getText().length() > 0)
						model.setUpdated_at(new Date(etUpdated_at.getText().toString()));
                    Createmcs_CartItem(model);
                }
            }
        });
    }

	boolean IsValid() {
		boolean result = true;
		if(!validation.isValid("Required", (spnmcs_CartCartId.getSelectedItemPosition() > 0 ? String.valueOf(_mcs_CartList.get(spnmcs_CartCartId.getSelectedItemPosition() - 1).getCartId()) : null), false, "Integer", null)) {
			spnmcs_CartCartId.setFocusable(true);
			spnmcs_CartCartId.setFocusableInTouchMode(true);
			spnmcs_CartCartId.requestFocus();
			((TextView)spnmcs_CartCartId.getSelectedView()).setError(validation.Message);
			result = false;
		}
		else {
			spnmcs_CartCartId.clearFocus();
			((TextView)spnmcs_CartCartId.getSelectedView()).setError(null);
		}
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
		if(!validation.isValid("None", etQuantity.getText().toString(), true, "Integer", null)) {
			etQuantity.requestFocus();
			etQuantity.setError(validation.Message);
			result = false;
		}
		else {
			etQuantity.clearFocus();
			etQuantity.setError(null);
		}
		if(!validation.isValid("None", etCreated_at.getText().toString(), true, "Date", null)) {
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

    void Createmcs_CartItem(mcs_CartItemDto item) {
        mcs_CartItemRepository _mcs_CartItemRepository = new mcs_CartItemRepository(access_token);
        if (_mcs_CartItemRepository.Post(item) > 0) {
            Intent intmcs_CartItemList = new Intent(getApplicationContext(), mcs_CartItemListActivity.class);
            startActivity(intmcs_CartItemList);
        }
        else {
            Toast.makeText(getApplicationContext(), "Bad Request", Toast.LENGTH_LONG).show();
        }
    }

	void Getmcs_CartList() {
		mcs_CartRepository _mcs_CartRepository = new mcs_CartRepository(access_token);
		_mcs_CartList =  _mcs_CartRepository.GetList(new Criteria());
		String[] arrmcs_CartCartId = new String[_mcs_CartList.size() + 1];
		arrmcs_CartCartId[0] = getResources().getString(R.string.prompt_spinner_select);
		for (int e = 0; e < _mcs_CartList.size(); e++) {
			arrmcs_CartCartId[e + 1] = String.valueOf(_mcs_CartList.get(e).getCartId());
		}
		spnmcs_CartCartId = (Spinner) findViewById(R.id.spnmcs_CartCartId);
		mcs_CartCartIdAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrmcs_CartCartId);
		mcs_CartCartIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnmcs_CartCartId.setPrompt("Please Select!");
		spnmcs_CartCartId.setAdapter(mcs_CartCartIdAdapter);
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
