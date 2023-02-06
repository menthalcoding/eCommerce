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

import HttpClient.DataTransferObjects.mcs_CartDto;
import HttpClient.Messages.Criteria.mcs_CartCriteria;
import HttpClient.Messages.Criteria.Criteria;
import HttpClient.Repositories.mcs_CartRepository;
import Util.SessionManager;
import Util.Validation;

public class mcs_CartCreateActivity extends AppCompatActivity {

	EditText etUserId;
	EditText etStatus;
	EditText etCreated_at;
	EditText etUpdated_at;
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
        setContentView(R.layout.activity_mcs_cart_create);
        getSupportActionBar().setTitle("mcs_Cart Create");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionManager(getApplicationContext());
        access_token = session.getAccessToken();
        if (access_token.isEmpty()) {
            Intent intent = new Intent(this, LoginWebApiActivity.class);
            startActivity(intent);
            return;
        }

        validation = new Validation(getApplicationContext());
		etUserId = (EditText) findViewById(R.id.etUserId);
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
				        datePickerDialog = new DatePickerDialog(mcs_CartCreateActivity.this,
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
				        datePickerDialog = new DatePickerDialog(mcs_CartCreateActivity.this,
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
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IsValid()) {
                    mcs_CartDto model = new mcs_CartDto();
					model.setUserId(new String(etUserId.getText().toString()));
					model.setStatus(new String(etStatus.getText().toString()));
					if (etCreated_at.getText().length() > 0)
						model.setCreated_at(new Date(etCreated_at.getText().toString()));
					if (etUpdated_at.getText().length() > 0)
						model.setUpdated_at(new Date(etUpdated_at.getText().toString()));
                    Createmcs_Cart(model);
                }
            }
        });
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
		if(!validation.isValid("None", etStatus.getText().toString(), false, "String", "255")) {
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
		if(!validation.isValid("None", etUpdated_at.getText().toString(), false, "Date", null)) {
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

    void Createmcs_Cart(mcs_CartDto item) {
        mcs_CartRepository _mcs_CartRepository = new mcs_CartRepository(access_token);
        if (_mcs_CartRepository.Post(item) > 0) {
            Intent intmcs_CartList = new Intent(getApplicationContext(), mcs_CartListActivity.class);
            startActivity(intmcs_CartList);
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
