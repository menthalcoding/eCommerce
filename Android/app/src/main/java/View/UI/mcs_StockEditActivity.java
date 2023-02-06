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

import HttpClient.DataTransferObjects.mcs_StockDto;
import HttpClient.Messages.Criteria.mcs_StockCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_StockRepository;
import HttpClient.Repositories.mcs_ProductRepository;
import HttpClient.DataTransferObjects.mcs_ProductDto;
import Util.Converter;
import Util.SessionManager;
import Util.Validation;

public class mcs_StockEditActivity extends AppCompatActivity {
	TextView tvStockId;
	EditText etQuantity;
	EditText etEntryPrice;
	EditText etEntryDate;
	Spinner spnmcs_ProductProductId;
	ArrayAdapter<String> mcs_ProductProductIdAdapter;
	List<mcs_ProductDto> _mcs_ProductList;
    Button btnEdit;
	DatePickerDialog datePickerDialog;
	Calendar calendar = Calendar.getInstance();
	int year, month, dayOfMonth;
    private SessionManager session;
    private String access_token;
    private Validation validation;
    mcs_StockRepository _mcs_StockRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_stock_edit);
        getSupportActionBar().setTitle("mcs_Stock Edit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        validation = new Validation(getApplicationContext());

        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        _mcs_StockRepository = new mcs_StockRepository(access_token);

		tvStockId = (TextView) findViewById(R.id.tvStockId);
		etQuantity = (EditText) findViewById(R.id.etQuantity);
		etEntryPrice = (EditText) findViewById(R.id.etEntryPrice);
		etEntryDate = (EditText) findViewById(R.id.etEntryDate);
        etEntryDate.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
                        //etEntryDate.setInputType(InputType.TYPE_NULL);
				        year = calendar.get(Calendar.YEAR);
				        month = calendar.get(Calendar.MONTH);
				        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
				        datePickerDialog = new DatePickerDialog(mcs_StockEditActivity.this,
					        new DatePickerDialog.OnDateSetListener() {
						        @Override
						        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
									String selectedDate = day + "/" + (month+1) + "/" + year;
									if (validation.isValidDate(selectedDate)) {
										etEntryDate.setText(selectedDate);
										etEntryDate.clearFocus();
										etEntryDate.setError(null);
									}
									else {
										etEntryDate.requestFocus();
										etEntryDate.setError(validation.Message);
									}
						        }
					        }, year, month, dayOfMonth);
				        datePickerDialog.show();
                    break;
                }
				return false;
			}
		});
		Getmcs_ProductList();
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IsValid()) {
                    mcs_StockDto model = new mcs_StockDto();
					model.setStockId(new Integer(tvStockId.getText().toString()));
					if (spnmcs_ProductProductId.getSelectedItemPosition() > 0)
						model.setProductId(_mcs_ProductList.get(spnmcs_ProductProductId.getSelectedItemPosition() - 1).getProductId());
					if (etQuantity.getText().length() > 0)
						model.setQuantity(new Integer(etQuantity.getText().toString()));
					if (etEntryPrice.getText().length() > 0)
						model.setEntryPrice(new BigDecimal(etEntryPrice.getText().toString()));
					if (etEntryDate.getText().length() > 0)
						model.setEntryDate(new Date(etEntryDate.getText().toString()));
                    Editmcs_Stock(model);
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
			Integer keyStockId = (Integer)extras.get("StockId");
            Getmcs_Stock(keyStockId);
        }
    }

    private void Getmcs_Stock(Integer StockId) {
        mcs_StockCriteria c = new mcs_StockCriteria();
		c.StockId = StockId;
        mcs_StockDto data = _mcs_StockRepository.Get(c);
        if (data != null) {
			tvStockId.setText(String.valueOf(data.getStockId()));
			String selValProductId = "";
            for (mcs_ProductDto item : _mcs_ProductList) {
				if (item.getProductId().equals(data.getProductId())) {
					selValProductId = String.valueOf(item.getName());
			        spnmcs_ProductProductId.setSelection(mcs_ProductProductIdAdapter.getPosition(selValProductId));
                    break;
                }
			}
			etQuantity.setText(String.valueOf(data.getQuantity()));
			etEntryPrice.setText(String.valueOf(data.getEntryPrice()));
			etEntryDate.setText(Converter.formatDate(data.getEntryDate()));
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
		if(!validation.isValid("Required", etQuantity.getText().toString(), false, "Integer", null)) {
			etQuantity.requestFocus();
			etQuantity.setError(validation.Message);
			result = false;
		}
		else {
			etQuantity.clearFocus();
			etQuantity.setError(null);
		}
		if(!validation.isValid("None", etEntryPrice.getText().toString(), true, "BigDecimal", null)) {
			etEntryPrice.requestFocus();
			etEntryPrice.setError(validation.Message);
			result = false;
		}
		else {
			etEntryPrice.clearFocus();
			etEntryPrice.setError(null);
		}
		if(!validation.isValid("None", etEntryDate.getText().toString(), true, "Date", null)) {
			etEntryDate.requestFocus();
			etEntryDate.setError(validation.Message);
			result = false;
		}
		else {
			etEntryDate.clearFocus();
			etEntryDate.setError(null);
		}

		return result;
	}

    void Editmcs_Stock(mcs_StockDto item) {
        if (item != null) {
            _mcs_StockRepository.Put(item);
            Intent intmcs_StockList = new Intent(getApplicationContext(), mcs_StockListActivity.class);
            startActivity(intmcs_StockList);
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
