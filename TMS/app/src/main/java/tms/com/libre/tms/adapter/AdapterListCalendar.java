package tms.com.libre.tms.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tms.com.libre.tms.AcDrop;
import tms.com.libre.tms.AcOnRoute;
import tms.com.libre.tms.AcPickup;
import tms.com.libre.tms.R;
import tms.com.libre.tms.entry.EnTruckLoadResponse;

/**
 * Created by GL62 on 4/13/2017.
 */

public class AdapterListCalendar extends BaseAdapter {
    private ArrayList<EnTruckLoadResponse.Content> listcontent = new ArrayList<>();
    private Context context;

    public AdapterListCalendar(ArrayList<EnTruckLoadResponse.Content> contents, Context context) {
        this.listcontent = contents;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listcontent.size();
    }

    @Override
    public Object getItem(int position) {
        return listcontent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listcontent.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(R.layout.dialog_calendar_detail, null);

        final TextView tvDate = (TextView) itemView.findViewById(R.id.tvDT);
        final TextView tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
        final TextView tvTotalCount = (TextView) itemView.findViewById(R.id.tvTotalCount);
        final TextView tvVihicleCount = (TextView) itemView.findViewById(R.id.tvVehicleCount);
        final TextView tvID = (TextView) itemView.findViewById(R.id.tvID);
        final TextView tvTLNo = (TextView) itemView.findViewById(R.id.tvTLNO);
        final TextView tvTruckNo = (TextView) itemView.findViewById(R.id.tvTrucNo);
        final TextView tvDriverID = (TextView) itemView.findViewById(R.id.tvDriverID);
        final TextView tvDriver = (TextView) itemView.findViewById(R.id.tvDriver);

        final EnTruckLoadResponse.Content content = listcontent.get(position);

        tvDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(content.getDT().substring(6, 19)))));
        tvStatus.setText(String.valueOf(content.getStatus()));
        tvTotalCount.setText(String.valueOf(content.getTotalCount()));
        tvVihicleCount.setText(String.valueOf(content.getTotalCount()));
        tvID.setText(String.valueOf(content.getID()));
        tvTLNo.setText(content.getTLNO());
        tvTruckNo.setText(content.getTruckNo());
        tvDriverID.setText(String.valueOf(content.getDriverID()));
        tvDriver.setText(content.getDriver());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (content.getStatus()) {
                    case 16:
                        break;
                    case 17:
                        context.startActivity(new Intent(context.getApplicationContext(), AcPickup.class));
                        break;
                    case 18:
                        context.startActivity(new Intent(context.getApplicationContext(), AcOnRoute.class));
                        break;
                    case 19:
                        break;
                    case 20:
                        context.startActivity(new Intent(context.getApplicationContext(), AcDrop.class));
                        break;
                }

            }
        });
        return itemView;
    }
}
