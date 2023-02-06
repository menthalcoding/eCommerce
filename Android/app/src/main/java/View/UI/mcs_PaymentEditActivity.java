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

import HttpClient.DataTransferObjects.mcs_PaymentDto;
import HttpClient.Messages.Criteria.mcs_PaymentCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_PaymentRepository;
import Util.Converter;
import Util.SessionManager;
import Util.Validation;

public class mcs_PaymentEditActivity extends AppCompatActivity {
	TextView tvPaymentId;
	EditText etUserId;
	EditText etPaymentType;
	EditText etCreated_at;
	EditText etAmount;
	EditText etCurrency;
	EditText etOrderId;
	EditText etAddressId;
    Button btnEdit;
	DatePickerDialog datePickerDialog;
	Calendar calendar = Calendar.getInstance();
	int year, month, dayOfMonth;
    private SessionManager session;
    private String access_token;
    private Validation validation;
    mcs_PaymentRepository _mcs_PaymentRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcs_payment_edit);
        getSupportActionBar().setTitle("mcs_Payment Edit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        validation = new Validation(getApplicationContext());

        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        _mcs_PaymentRepository = new mcs_PaymentRepository(access_token);

		tvPaymentId = (TextView) findViewById(R.id.tvPaymentId);
		etUserId = (EditText) findViewById(R.id.etUserId);
		etPaymentType = (EditText) findViewById(R.id.etPaymentType);
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
				        datePickerDialog = new DatePickerDialog(mcs_PaymentEditActivity.this,
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
		etAmount = (EditText) findViewById(R.id.etAmount);
		etCurrency = (EditText) findViewById(R.id.etCurrency);
		etOrderId = (EditText) findViewById(R.id.etOrderId);
		etAddressId = (EditText) findViewById(R.id.etAddressId);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IsValid()) {
                    mcs_PaymentDto model = new mcs_PaymentDto();
					model.setPaymentId(new Integer(tvPaymentId.getText().toString()));
					model.setUserId(new String(etUserId.getText().toString()));
					model.setPaymentType(new String(etPaymentType.getText().toString()));
					if (etCreated_at.getText().length() > 0)
						model.setCreated_at(new Date(etCreated_at.getText().toString()));
					if (etAmount.getText().length() > 0)
						model.setAmount(new BigDecimal(etAmount.getText().toString()));
					model.setCurrency(new String(etCurrency.getText().toString()));
					if (etOrderId.getText().length() > 0)
						model.setOrderId(new Integer(etOrderId.getText().toString()));
					if (etAddressId.getText().length() > 0)
						model.setAddressId(new Integer(etAddressId.getText().toString()));
                    Editmcs_Payment(model);
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
			Integer keyPaymentId = (Integer)extras.get("PaymentId");
            Getmcs_Payment(keyPaymentId);
        }
    }

    private void Getmcs_Payment(Integer PaymentId) {
        mcs_PaymentCriteria c = new mcs_PaymentCriteria();
		c.PaymentId = PaymentId;
        mcs_PaymentDto data = _mcs_PaymentRepository.Get(c);
        if (data != null) {
			tvPaymentId.setText(String.valueOf(data.getPaymentId()));
			etUserId.setText(String.valueOf(data.getUserId()));
			etPaymentType.setText(String.valueOf(data.getPaymentType()));
			etCreated_at.setText(Converter.formatDate(data.getCreated_at()));
			etAmount.setText(String.valueOf(data.getAmount()));
			etCurrency.setText(String.valueOf(data.getCurrency()));
			etOrderId.setText(String.valueOf(data.getOrderId()));
			etAddressId.setText(String.valueOf(data.getAddressId()));
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
		if(!validation.isValid("None", etPaymentType.getText().toString(), true, "String", "50")) {
			etPaymentType.requestFocus();
			etPaymentType.setError(validation.Message);
			result = false;
		}
		else {
			etPaymentType.clearFocus();
			etPaymentType.setError(null);
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
		if(!validation.isValid("None", etAmount.getText().toString(), true, "BigDecimal", null)) {
			etAmount.requestFocus();
			etAmount.setError(validation.Message);
			result = false;
		}
		else {
			etAmount.clearFocus();
			etAmount.setError(null);
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
		if(!validation.isValid("Required", etOrderId.getText().toString(), false, "Integer", null)) {
			etOrderId.requestFocus();
			etOrderId.setError(validation.Message);
			result = false;
		}
		else {
			etOrderId.clearFocus();
			etOrderId.setError(null);
		}
		if(!validation.isValid("Required", etAddressId.getText().toString(), false, "Integer", null)) {
			etAddressId.requestFocus();
			etAddressId.setError(validation.Message);
			result = false;
		}
		else {
			etAddressId.clearFocus();
			etAddressId.setError(null);
		}

		return result;
	}

    void Editmcs_Payment(mcs_PaymentDto item) {
        if (item != null) {
            _mcs_PaymentRepository.Put(item);
            Intent intmcs_PaymentList = new Intent(getApplicationContext(), mcs_PaymentListActivity.class);
            startActivity(intmcs_PaymentList);
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
