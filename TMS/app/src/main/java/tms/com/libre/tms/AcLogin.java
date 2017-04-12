package tms.com.libre.tms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.libre.mylibs.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tms.com.libre.tms.common.AppContanst;
import tms.com.libre.tms.entry.EnLoginResponse;
import tms.com.libre.tms.entry.EnValidateResponse;
import tms.com.libre.tms.serivces.AppApi;

public class AcLogin extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        checkLoginStatus();
        setContentView(R.layout.ac_login);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLoginStatus();
    }

    public void checkLoginStatus() {
        String token = MyUtils.getStringData(getApplicationContext(), AppContanst.TOKEN);

        if (token != null && !token.equals("")) {
            AppApi appApi = new AppApi();
            appApi.services().checkValidationToken(token, new Callback<EnValidateResponse>() {
                @Override
                public void success(EnValidateResponse validateResponse, Response response) {

                    if (validateResponse.getStatusCode() == 200) {
                        startActivity(new Intent(AcLogin.this, AcMain.class));
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        }
    }

    public void init() {

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                actionLogin();
                break;
        }
    }

    public void actionLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if (email.equals("")) {
            edtEmail.setError(getString(R.string.email_empty));
            edtEmail.requestFocus();
        } else if (password.equals("")) {
            edtPassword.setError(getString(R.string.password_empty));
            edtPassword.requestFocus();
        } else if (!isValidEmail(email)) {
            edtEmail.setError(getString(R.string.invalid_email));
            edtEmail.requestFocus();
        } else {
            loginFinal(email, password);
        }
    }

    public void loginFinal(String email, String password) {
        progressDialog.show();
        AppApi appApi = new AppApi();
        appApi.services().login(email, password, new Callback<EnLoginResponse>() {
            @Override
            public void success(EnLoginResponse loginResponse, Response response) {
                Log.d("testetseas",loginResponse.getContent());
                try {
                    JSONObject obj = new JSONObject(loginResponse.getContent());
                    MyUtils.insertStringData(getApplicationContext(), AppContanst.TOKEN, obj.getString("token"));
                    MyUtils.showToast(getApplicationContext(), "Login Success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(loginResponse.getStatusCode()==200){
                    startActivity(new Intent(AcLogin.this, AcMain.class));
                }else{
                    MyUtils.showToast(getApplicationContext(), "Login Fail, try again ");
                }
                progressDialog.cancel();


            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.cancel();

            }
        });

    }

    private boolean isValidEmail(String emailInput) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailInput);
        return matcher.matches();
    }

    //Created by Sonhoang
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
