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
import android.widget.ListView;

import com.libre.mylibs.MyUtils;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tms.com.libre.tms.adapter.AdapterListCalendar;
import tms.com.libre.tms.common.AppContanst;
import tms.com.libre.tms.entry.EnTruckLoadResponse;
import tms.com.libre.tms.serivces.AppApi;


// Created by Sonhoang

public class AcCalendar extends AppCompatActivity {

    private ArrayList<EnTruckLoadResponse.Content> content = new ArrayList<>();
    private ArrayList<EnTruckLoadResponse.Content> listContents = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_calender);
        init();
    }

    public void init() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.calendar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getData();


    }

    public void initCalender(final ArrayList<EnTruckLoadResponse.Content> contents) {

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
                listContents.clear();
                for (int i = 0; i < contents.size(); i++) {
                    String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(contents.get(i).getDT().substring(6, 19))));
                    String dateclick = new SimpleDateFormat("dd/MM/yyyy").format(date);

                    if (dateclick.equals(dateString)) {
                        listContents.add(contents.get(i));
                    }
                }
                showDetailDialog(listContents);
            }

            @Override
            public void onCaldroidViewCreated() {
            }

        };
        caldroidFragment.setCaldroidListener(listener);
    }

    public ArrayList<EnTruckLoadResponse.Content> getData() {

        AppApi appApi = new AppApi();
        String driverid = MyUtils.getStringData(getBaseContext(), AppContanst.DRIVERID);
        String token = MyUtils.getStringData(getBaseContext(), AppContanst.TOKEN);

        appApi.services().getTruckLoad(token, driverid, "16", "30", new Callback<EnTruckLoadResponse>() {
            @Override
            public void success(EnTruckLoadResponse enTruckLoadResponse, Response response) {
                if (enTruckLoadResponse.getStatusCode() == 200) {
                    content = new ArrayList<>();
                    content = enTruckLoadResponse.getContent();
                    initCalender(content);
                } else {
                    MyUtils.showToast(getApplicationContext(), getString(R.string.login_failed));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MyUtils.showToast(getApplicationContext(), getString(R.string.login_failed));
            }
        });
        return content;
    }

    public void showDetailDialog(ArrayList<EnTruckLoadResponse.Content> listContents) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.list_calendar, null);
        dialogBuilder.setView(dialogView);

        //setAdapter for customdialog
        AdapterListCalendar adapterListCalendar = new AdapterListCalendar(listContents, this);

        adapterListCalendar.notifyDataSetChanged();
        ListView lsvCalendar = (ListView) dialogView.findViewById(R.id.lsvCalendar);
        lsvCalendar.setAdapter(adapterListCalendar);
        dialogBuilder.setTitle(R.string.calendar);
        dialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
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
