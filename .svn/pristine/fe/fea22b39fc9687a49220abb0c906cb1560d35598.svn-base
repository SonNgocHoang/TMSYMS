package tms.com.libre.tms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import tms.com.libre.tms.R;
import tms.com.libre.tms.entry.EnTruckLoad;

/**
 * Created by quangnv on 4/11/17.
 */

public class AdapterTruckLoad extends BaseAdapter {
    private ArrayList<EnTruckLoad> listTruckLoads;
    private Context mContext;

    public AdapterTruckLoad(Context context, ArrayList<EnTruckLoad> listTruckLoads){
        this.mContext = context;
        this.listTruckLoads = listTruckLoads;

    }
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 10;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_gridview_truckload, null);
        return itemView;
    }
}
