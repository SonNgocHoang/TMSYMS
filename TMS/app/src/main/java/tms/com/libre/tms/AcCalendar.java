package tms.com.libre.tms;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.libre.mylibs.MyUtils;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tms.com.libre.tms.adapter.AdapterTruckLoad;
import tms.com.libre.tms.common.AppContanst;
import tms.com.libre.tms.entry.EnTruckLoad;
import tms.com.libre.tms.serivces.AppApi;

import static tms.com.libre.tms.R.id.content_layout;
import static tms.com.libre.tms.R.id.gridviewTruckLoad;


// Created by Sonhoang

public class AcCalendar extends AppCompatActivity {

    private ArrayList<EnTruckLoad.Content> content = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_calender);
        init();
    }

    public void init() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Calendar");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getData();

    }

    public void initCalender(final ArrayList<EnTruckLoad.Content> contents) {

        //init Calendar
        CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        caldroidFragment.setArguments(args);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.fr_Calendar, caldroidFragment);
        t.commit();

        //data from user
        for (int i = 0; i < contents.size(); i++) {
            Calendar specailDate = Calendar.getInstance();

            //get Special date
            String year = new SimpleDateFormat("yyyy").format(new Date(Long.parseLong(content.get(i).getDT().substring(6, 19))));
            String month = new SimpleDateFormat("MM").format(new Date(Long.parseLong(content.get(i).getDT().substring(6, 19))));
            String day = new SimpleDateFormat("dd").format(new Date(Long.parseLong(content.get(i).getDT().substring(6, 19))));

            Log.d("CODATER", "initCalender: " + year + month + day);

            specailDate.set(Integer.valueOf(year), Integer.valueOf(month) - 1, Integer.valueOf(day));
            caldroidFragment.setBackgroundDrawableForDate(getResources().getDrawable(R.drawable.specail_date), specailDate.getTime());

        }

        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
            }

            @Override
            public void onChangeMonth(int month, int year) {
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                for (int i = 0; i < contents.size(); i++) {

                    String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(contents.get(i).getDT().substring(6, 19))));
                    String dateclick = new SimpleDateFormat("dd/MM/yyyy").format(date);
                    if (dateclick.equals(dateString)) {
                        showDetailDialog(contents.get(i));
                    }

                }

            }

            @Override
            public void onCaldroidViewCreated() {
            }

        };
        caldroidFragment.setCaldroidListener(listener);
    }

    public ArrayList<EnTruckLoad.Content> getData() {

        AppApi appApi = new AppApi();
        String driverid = MyUtils.getStringData(getBaseContext(), AppContanst.DRIVERID);
        String token = MyUtils.getStringData(getBaseContext(), AppContanst.TOKEN);

        appApi.services().getTruckLoad(token, driverid, "16", "30", new Callback<EnTruckLoad>() {
            @Override
            public void success(EnTruckLoad enTruckLoad, Response response) {
                if (enTruckLoad.getStatusCode() == 200) {
                    content = new ArrayList<>();
                    content = enTruckLoad.getContent();
                    initCalender(content);
                } else {
                    MyUtils.showToast(getApplicationContext(), "Load Fail ");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MyUtils.showToast(getApplicationContext(), "Load Fail ");
            }
        });
        return content;
    }

    public void showDetailDialog(EnTruckLoad.Content content) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_calendar_detail, null);
        dialogBuilder.setView(dialogView);

        final TextView tvDate = (TextView) dialogView.findViewById(R.id.tvDT);
        final TextView tvStatus = (TextView) dialogView.findViewById(R.id.tvStatus);
        final TextView tvTotalCount = (TextView) dialogView.findViewById(R.id.tvTotalCount);
        final TextView tvVihicleCount = (TextView) dialogView.findViewById(R.id.tvVehicleCount);
        final TextView tvID = (TextView) dialogView.findViewById(R.id.tvID);
        final TextView tvTLNo = (TextView) dialogView.findViewById(R.id.tvTLNO);
        final TextView tvTruckNo = (TextView) dialogView.findViewById(R.id.tvTrucNo);
        final TextView tvDriverID = (TextView) dialogView.findViewById(R.id.tvDriverID);
        final TextView tvDriver = (TextView) dialogView.findViewById(R.id.tvDriver);

        tvDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(content.getDT().substring(6, 19)))));
        tvStatus.setText(String.valueOf(content.getStatus()));
        tvTotalCount.setText(String.valueOf(content.getTotalCount()));
        tvVihicleCount.setText(String.valueOf(content.getTotalCount()));
        tvID.setText(String.valueOf(content.getID()));
        tvTLNo.setText(content.getTLNO());
        tvTruckNo.setText(content.getTruckNo());
        tvDriverID.setText(String.valueOf(content.getDriverID()));
        tvDriver.setText(content.getDriver());

        dialogBuilder.setTitle("Calendar");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
