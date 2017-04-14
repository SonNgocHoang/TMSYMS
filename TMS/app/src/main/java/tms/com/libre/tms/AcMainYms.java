package tms.com.libre.tms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.libre.mylibs.MyUtils;

import tms.com.libre.tms.common.AppContanst;

public class AcMainYms extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private RelativeLayout rlSignOut, rlCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main_yms);
        init();
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rlSignOut = (RelativeLayout) toolbar.findViewById(R.id.rlSignOut);
        rlCalendar = (RelativeLayout) toolbar.findViewById(R.id.rlCalendar);
        rlCalendar.setVisibility(View.GONE);


        rlSignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlSignOut:
                Intent intent = new Intent(AcMainYms.this, AcStart.class);
                MyUtils.insertStringData(getApplicationContext(), AppContanst.TOKEN, "");
                startActivity(intent);
                break;
        }
    }
}
