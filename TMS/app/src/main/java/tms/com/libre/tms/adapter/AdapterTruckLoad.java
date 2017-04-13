package tms.com.libre.tms.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tms.com.libre.tms.AcPickup;
import tms.com.libre.tms.R;
import tms.com.libre.tms.entry.EnTruckLoad;

/**
 * Created by quangnv on 4/11/17.
 */

public class AdapterTruckLoad extends BaseAdapter {
    private ArrayList<EnTruckLoad.Content> listTruckLoads;
    private Context mContext;

    public AdapterTruckLoad(Context context, ArrayList<EnTruckLoad.Content> listTruckLoads) {
        this.mContext = context;
        this.listTruckLoads = listTruckLoads;

    }

    @Override
    public int getCount() {
        return listTruckLoads.size();
    }

    @Override
    public Object getItem(int i) {
        return listTruckLoads.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listTruckLoads.get(i).getID();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(R.layout.item_gridview_truckload, null);
        TextView tvTrucloadNo = (TextView) itemView.findViewById(R.id.truckLoadNo);
        TextView tvtvVoyge = (TextView) itemView.findViewById(R.id.tvVoyge);
        TextView tvCreated = (TextView) itemView.findViewById(R.id.tvCreated);
        EnTruckLoad.Content content = listTruckLoads.get(i);

        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(content.getDT().substring(6, 19))));
        tvTrucloadNo.setText(content.getTruckNo());
        tvCreated.setText(dateString.toString());

        Log.d("nguyeqweqw", content.getDT().substring(6, 19));
        Log.d("nguyeqweqw", dateString);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AcPickup.class);
                mContext.startActivity(intent);
            }
        });
        return itemView;
    }
}
