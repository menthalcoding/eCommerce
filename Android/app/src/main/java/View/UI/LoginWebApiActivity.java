package View.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.northwindsampledb.R;

import HttpClient.Messages.LoginResponse;
import HttpClient.Messages.LoginWebApiRequest;
import HttpClient.Repositories.AuthRepository;
import Util.SessionManager;

public class LoginWebApiActivity extends AppCompatActivity {
    SessionManager session;
    private TextView tvEmail;
    private TextView tvPassword;
    private Button btnLogin;
    private TextView tvSignUp;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());
        tvEmail = (TextView) findViewById(R.id.email);
        tvPassword = (TextView) findViewById(R.id.password);
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        btnLogin = (Button) findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tvEmail.getText().toString().isEmpty() || tvPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.required, Toast.LENGTH_LONG).show();
                }
                else{
                    email = tvEmail.getText().toString();
                    password = tvPassword.getText().toString();
                    loginCheck(email, password);
                }
            }
        });
    }

    private void loginCheck(String email, String password) {

        LoginWebApiRequest request = new LoginWebApiRequest();
        request.Email = email;
        request.Password = password;

        LoginResponse response = new LoginResponse();
        response = new AuthRepository().Login(request);

        if (response != null && !response.accessToken.isEmpty()) {

            String tokenType = response.tokenType;
            String accesToken = response.accessToken;
            String expires_in = response.expiresIn;

            session.createAccessToken(accesToken, tokenType, expires_in, request.Email, response.sessionId);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), R.string.NotFound, Toast.LENGTH_LONG).show();
        }
    }
}
