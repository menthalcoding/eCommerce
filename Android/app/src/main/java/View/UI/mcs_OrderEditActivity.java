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

import HttpClient.DataTransferObjects.mcs_OrderDto;
import HttpClient.Messages.Criteria.mcs_OrderCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_OrderRepository;
import HttpClient.Repositories.mcs_CartRepository;
import HttpClient.DataTransferObjects.mcs_CartDto;
import Util.Converter;
import Util.SessionManager;
import Util.Validation;

public class mcs_OrderEditActivity extends AppCompatActivity {
	TextView tvOrderId;
	EditText etUserId;
	EditText etCreated_at;
	EditText etCost;
	EditText etTax;
	EditText etTotal;
	EditText etPaid;
	EditText etCurrency;
	Spinner spnmcs_CartCartId;
	ArrayAdapter<String> mcs_CartCartIdAdapter;
	List<mcs_CartDto> _mcs_CartList;
    Button btnEdit;
	DatePickerDialog datePickerDialog;
	Calendar calendar = Calendar.getInstance();
	int year, month, dayOfMonth;
    private SessionManager session;
    private String access_token;
    private Validation validation;
    mcs_OrderRepository _mcs_OrderRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_order_edit);
        getSupportActionBar().setTitle("mcs_Order Edit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        validation = new Validation(getApplicationContext());

        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        _mcs_OrderRepository = new mcs_OrderRepository(access_token);

		tvOrderId = (TextView) findViewById(R.id.tvOrderId);
		etUserId = (EditText) findViewById(R.id.etUserId);
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
				        datePickerDialog = new DatePickerDialog(mcs_OrderEditActivity.this,
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
		etCost = (EditText) findViewById(R.id.etCost);
		etTax = (EditText) findViewById(R.id.etTax);
		etTotal = (EditText) findViewById(R.id.etTotal);
		etPaid = (EditText) findViewById(R.id.etPaid);
		etCurrency = (EditText) findViewById(R.id.etCurrency);
		Getmcs_CartList();
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IsValid()) {
                    mcs_OrderDto model = new mcs_OrderDto();
					model.setOrderId(new Integer(tvOrderId.getText().toString()));
					model.setUserId(new String(etUserId.getText().toString()));
					if (spnmcs_CartCartId.getSelectedItemPosition() > 0)
						model.setCartId(_mcs_CartList.get(spnmcs_CartCartId.getSelectedItemPosition() - 1).getCartId());
					if (etCreated_at.getText().length() > 0)
						model.setCreated_at(new Date(etCreated_at.getText().toString()));
					if (etCost.getText().length() > 0)
						model.setCost(new BigDecimal(etCost.getText().toString()));
					if (etTax.getText().length() > 0)
						model.setTax(new BigDecimal(etTax.getText().toString()));
					if (etTotal.getText().length() > 0)
						model.setTotal(new BigDecimal(etTotal.getText().toString()));
					if (etPaid.getText().length() > 0)
						model.setPaid(new Boolean(etPaid.getText().toString()));
					model.setCurrency(new String(etCurrency.getText().toString()));
                    Editmcs_Order(model);
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
			Integer keyOrderId = (Integer)extras.get("OrderId");
            Getmcs_Order(keyOrderId);
        }
    }

    private void Getmcs_Order(Integer OrderId) {
        mcs_OrderCriteria c = new mcs_OrderCriteria();
		c.OrderId = OrderId;
        mcs_OrderDto data = _mcs_OrderRepository.Get(c);
        if (data != null) {
			tvOrderId.setText(String.valueOf(data.getOrderId()));
			etUserId.setText(String.valueOf(data.getUserId()));
			String selValCartId = "";
            for (mcs_CartDto item : _mcs_CartList) {
				if (item.getCartId().equals(data.getCartId())) {
					selValCartId = String.valueOf(item.getCartId());
			        spnmcs_CartCartId.setSelection(mcs_CartCartIdAdapter.getPosition(selValCartId));
                    break;
                }
			}
			etCreated_at.setText(Converter.formatDate(data.getCreated_at()));
			etCost.setText(String.valueOf(data.getCost()));
			etTax.setText(String.valueOf(data.getTax()));
			etTotal.setText(String.valueOf(data.getTotal()));
			etPaid.setText(String.valueOf(data.getPaid()));
			etCurrency.setText(String.valueOf(data.getCurrency()));
        }
        else {
            Toast.makeText(getApplicationContext(), "Bad Request", Toast.LENGTH_LONG).show();
        }
    }

	boolean IsValid() {
		boolean result = true;
		if(!validation.isValid("Required", etUserId.getText().toString(), false, "String", "450")) {
			etUserId.requestFocus();
			etUserId.setError(validation.Message);
			result = false;
		}
		else {
			etUserId.clearFocus();
			etUserId.setError(null);
		}
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
		if(!validation.isValid("None", etCreated_at.getText().toString(), true, "Date", null)) {
			etCreated_at.requestFocus();
			etCreated_at.setError(validation.Message);
			result = false;
		}
		else {
			etCreated_at.clearFocus();
			etCreated_at.setError(null);
		}
		if(!validation.isValid("None", etCost.getText().toString(), true, "BigDecimal", null)) {
			etCost.requestFocus();
			etCost.setError(validation.Message);
			result = false;
		}
		else {
			etCost.clearFocus();
			etCost.setError(null);
		}
		if(!validation.isValid("None", etTax.getText().toString(), true, "BigDecimal", null)) {
			etTax.requestFocus();
			etTax.setError(validation.Message);
			result = false;
		}
		else {
			etTax.clearFocus();
			etTax.setError(null);
		}
		if(!validation.isValid("None", etTotal.getText().toString(), true, "BigDecimal", null)) {
			etTotal.requestFocus();
			etTotal.setError(validation.Message);
			result = false;
		}
		else {
			etTotal.clearFocus();
			etTotal.setError(null);
		}
		if(!validation.isValid("None", etPaid.getText().toString(), true, "Boolean", null)) {
			etPaid.requestFocus();
			etPaid.setError(validation.Message);
			result = false;
		}
		else {
			etPaid.clearFocus();
			etPaid.setError(null);
		}
		if(!validation.isValid("None", etCurrency.getText().toString(), true, "String", "50")) {
			etCurrency.requestFocus();
			etCurrency.setError(validation.Message);
			result = false;
		}
		else {
			etCurrency.clearFocus();
			etCurrency.setError(null);
		}

		return result;
	}

    void Editmcs_Order(mcs_OrderDto item) {
        if (item != null) {
            _mcs_OrderRepository.Put(item);
            Intent intmcs_OrderList = new Intent(getApplicationContext(), mcs_OrderListActivity.class);
            startActivity(intmcs_OrderList);
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
