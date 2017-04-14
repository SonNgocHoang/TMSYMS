package tms.com.libre.tms;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.libre.mylibs.MyUtils;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tms.com.libre.tms.adapter.AdapterDamageReport;
import tms.com.libre.tms.common.AppContanst;
import tms.com.libre.tms.entry.EnDamageCodeResponse;
import tms.com.libre.tms.entry.EnDamageLoadListResponse;
import tms.com.libre.tms.serivces.AppApi;

public class AcDamageReport extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private RelativeLayout rlBack;
    private TextView tvTitleLogin;
    private FloatingActionButton btnAddReport;
    private GridView gvDamageReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac__damage__report);
        init();
        getDamageLoadList();
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rlBack = (RelativeLayout) findViewById(R.id.rlBack);
        tvTitleLogin = (TextView) findViewById(R.id.tvTitleLogin);
        tvTitleLogin.setText(R.string.dmg);
        btnAddReport = (FloatingActionButton) findViewById(R.id.btnAddReport);

        gvDamageReport = (GridView)findViewById(R.id.gvDamageReport);

        rlBack.setOnClickListener(this);
        btnAddReport.setOnClickListener(this);
    }

    public void getDamageLoadList() {
        AppApi appApi = new AppApi();

        String token = MyUtils.getStringData(getBaseContext(),AppContanst.TOKEN);
        appApi.services().getDamageLoadList(token, "1", "10", new Callback<EnDamageLoadListResponse>() {
            @Override
            public void success(EnDamageLoadListResponse enDamageLoadListResponse, Response response) {
                if (enDamageLoadListResponse.getStatusCode() == 200) {
                    AdapterDamageReport adapterDamageReport = new AdapterDamageReport(getBaseContext(),enDamageLoadListResponse.getContent());
                    gvDamageReport.setAdapter(adapterDamageReport);
                } else {
                    MyUtils.showToast(getApplicationContext(), getString(R.string.load_fail));

                }
            }
            @Override
            public void failure(RetrofitError error) {
                MyUtils.showToast(getApplicationContext(), getString(R.string.load_fail));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlBack:
                startActivity(new Intent(AcDamageReport.this, AcPickup.class));
                break;
            case R.id.btnAddReport:
                startActivity(new Intent(AcDamageReport.this, AcAddNewReport.class));
                break;
        }
    }
}
