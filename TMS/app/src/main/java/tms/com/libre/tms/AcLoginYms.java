package tms.com.libre.tms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.libre.mylibs.MyUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tms.com.libre.tms.common.AppContanst;
import tms.com.libre.tms.entry.EnLoginResponse;
import tms.com.libre.tms.entry.EnValidateResponse;
import tms.com.libre.tms.serivces.AppApi;

public class AcLoginYms extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private ProgressDialog progressDialog;
    private CheckBox cbSaveLogin;
    private Toolbar toolbar_login;
    private RelativeLayout rlBack;
    private TextView tvTitleLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        setContentView(R.layout.ac_yms_login);
        init();
        checkLoginStatus();


    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLoginStatus();

        //check Save Login sonhoang
        String email = MyUtils.getStringData(getApplicationContext(), AppContanst.EMAILYMS);
        String password = MyUtils.getStringData(getApplicationContext(), AppContanst.PASSWORDYMS);

        edtEmail.setText(email);
        edtPassword.setText(password);
    }

    public void checkLoginStatus() {
        String token = MyUtils.getStringData(getApplicationContext(), AppContanst.TOKEN);

        if (token != null && !token.equals("")) {
            AppApi appApi = new AppApi();
            appApi.services().checkValidationToken(token, new Callback<EnValidateResponse>() {
                @Override
                public void success(EnValidateResponse validateResponse, Response response) {

                    if (validateResponse.getStatusCode() == 200) {
                        startActivity(new Intent(AcLoginYms.this, AcMainYms.class));
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        }
    }

    public void init() {
        toolbar_login = (Toolbar) findViewById(R.id.toolbar_login);
        rlBack = (RelativeLayout) toolbar_login.findViewById(R.id.rlBack);
        tvTitleLogin = (TextView) toolbar_login.findViewById(R.id.tvTitleLogin);
        tvTitleLogin.setText("YMS");

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        cbSaveLogin = (CheckBox) findViewById(R.id.cbSaveLogin);
        btnLogin.setOnClickListener(this);
        rlBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                actionLogin();
                break;
            case R.id.rlBack:
                Intent intent = new Intent(AcLoginYms.this, AcStart.class);
                startActivity(intent);
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
            if (cbSaveLogin.isChecked()) {
                loginFinal(email, password);
                MyUtils.insertStringData(getBaseContext(), AppContanst.EMAILYMS, email);
                MyUtils.insertStringData(getBaseContext(), AppContanst.EMAILYMS, password);
            } else {
                MyUtils.insertStringData(getBaseContext(), AppContanst.EMAILYMS, "");
                MyUtils.insertStringData(getBaseContext(), AppContanst.EMAILYMS, "");
                loginFinal(email, password);
            }
        }
    }

    public void loginFinal(String email, String password) {
        progressDialog.show();
        AppApi appApi = new AppApi();
        appApi.services().login(email, password, new Callback<EnLoginResponse>() {
            @Override
            public void success(EnLoginResponse loginResponse, Response response) {
                Log.d("testetseas", loginResponse.getContent().getToken());
                if (loginResponse.getStatusCode() == 200) {
                    MyUtils.insertStringData(getApplicationContext(), AppContanst.TOKEN, loginResponse.getContent().getToken());
                    MyUtils.showToast(getApplicationContext(), "Login Success");
                    if (loginResponse.getContent().getUserRoleType().equals("driver")) {
                        startActivity(new Intent(AcLoginYms.this, AcMain.class));
                    } else {
                        startActivity(new Intent(AcLoginYms.this, AcMainYms.class));
                    }
                } else {
                    MyUtils.showToast(getApplicationContext(), "Login Fail, try again ");
                }
                progressDialog.cancel();
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.cancel();
                MyUtils.showToast(getApplicationContext(), "Login Fail, try again ");
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


}
