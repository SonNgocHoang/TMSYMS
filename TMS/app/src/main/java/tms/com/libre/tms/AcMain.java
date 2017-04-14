package tms.com.libre.tms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.libre.mylibs.MyUtils;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tms.com.libre.tms.adapter.AdapterTruckLoad;
import tms.com.libre.tms.common.AppContanst;
import tms.com.libre.tms.entry.EnTruckLoadResponse;
import tms.com.libre.tms.serivces.AppApi;

public class AcMain extends AppCompatActivity implements View.OnClickListener {
    public static Drawer drawer;
    private LinearLayout layoutPickUp;
    private LinearLayout layoutOnRoute;
    private LinearLayout layoutDrop;

    private TextView tvPickUp;
    private TextView tvOnRoute;
    private TextView tvDrop;

    private Image imgAva;
    private TextView tvName;

    private Toolbar toolbar;
    private TextView tvToolbar;
    private RelativeLayout rlSignOut;
    private RelativeLayout rlCalender;
    private GridView gridviewTruckLoad;
    private ArrayList<EnTruckLoadResponse.Content> listTruckLoads;
    private AdapterTruckLoad adapterTruckLoad;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        getTrucLoad();
        init();


    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rlSignOut = (RelativeLayout) findViewById(R.id.rlSignOut);
        rlSignOut.setOnClickListener(this);

        rlCalender = (RelativeLayout) findViewById(R.id.rlCalendar);
        rlCalender.setOnClickListener(this);

        gridviewTruckLoad = (GridView) findViewById(R.id.gridviewTruckLoad);


        findViewById(R.id.gotoOnRoute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AcMain.this, AcOnRoute.class));
            }
        });

    }

//    public void initSlideMenuItem() {
//        layoutPickUp = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutPickUp);
//        layoutPickUp.setOnClickListener(this);
//        layoutOnRoute = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutOnRoute);
//        layoutOnRoute.setOnClickListener(this);
//        layoutDrop = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutDrop);
//        layoutDrop.setOnClickListener(this);
//
//        tvPickUp = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvPickUp);
//        tvOnRoute = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvOnRoute);
//        tvDrop = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvDrop);
//
//    }
//

    public void getTrucLoad() {
        progressDialog.show();
        AppApi appApi = new AppApi();
        String driverid = MyUtils.getStringData(getBaseContext(), AppContanst.DRIVERID);
        String token = MyUtils.getStringData(getBaseContext(), AppContanst.TOKEN);

        appApi.services().getTruckLoad(token, driverid, "16", "30", new Callback<EnTruckLoadResponse>() {
            @Override
            public void success(EnTruckLoadResponse enTruckLoadResponse, Response response) {
                if (enTruckLoadResponse.getStatusCode() == 200) {
                    listTruckLoads = new ArrayList<>();
                    listTruckLoads = enTruckLoadResponse.getContent();

                    Log.d("Truck coming", "success: " + enTruckLoadResponse.getContent().size());

                    adapterTruckLoad = new AdapterTruckLoad(AcMain.this, listTruckLoads);
                    gridviewTruckLoad.setAdapter(adapterTruckLoad);


                } else {
                    MyUtils.showToast(getApplicationContext(), getString(R.string.load_fail));
                }

                progressDialog.cancel();
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.cancel();
                MyUtils.showToast(getApplicationContext(), getString(R.string.load_fail));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layoutPickUp:
                break;
            case R.id.layoutOnRoute:
                break;
            case R.id.layoutDrop:
                break;
            case R.id.rlSignOut:
                Intent intent = new Intent(AcMain.this, AcStart.class);
                MyUtils.insertStringData(getApplicationContext(), AppContanst.TOKEN, "");
                startActivity(intent);
                break;
            case R.id.rlCalendar:
                Intent intent1 = new Intent(AcMain.this, AcCalendar.class);
                startActivity(intent1);
//                Calendar cal = Calendar.getInstance();
//                Intent intent3 = new Intent(Intent.ACTION_EDIT);
//                intent3.setType("vnd.android.cursor.item/event");
//                intent3.putExtra("beginTime", cal.getTimeInMillis());
//                intent3.putExtra("allDay", true);
//                intent3.putExtra("rrule", "FREQ=YEARLY");
//                intent3.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
//                intent3.putExtra("title", "A Test Event from android app");
//                startActivity(intent3);
                break;
        }

    }
}
