package tms.com.libre.tms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tms.com.libre.tms.R;
import tms.com.libre.tms.entry.EnDamageLoadListResponse;

/**
 * Created by GL62 on 4/14/2017.
 */

public class AdapterDamageReport extends BaseAdapter {
    private Context context;
    private List<EnDamageLoadListResponse.Content> list = new ArrayList<>();

    public AdapterDamageReport(Context context, List<EnDamageLoadListResponse.Content> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getVehicleID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View rootView = mInflater.inflate(R.layout.item_grid_damagereport, parent, false);

        TextView tvVehicleID = (TextView) rootView.findViewById(R.id.tvVehicleID);
        TextView tvVinCodeDmgRp = (TextView) rootView.findViewById(R.id.tvVinCodeDmgRp);
        TextView tvModel = (TextView) rootView.findViewById(R.id.tvModel);
        TextView tvMake = (TextView) rootView.findViewById(R.id.tvMake);
        TextView tvYear = (TextView) rootView.findViewById(R.id.tvYear);
        TextView tvShipper = (TextView) rootView.findViewById(R.id.tvShipper);
        TextView tvVehicleCount = (TextView) rootView.findViewById(R.id.tvVehicleCount);
        TextView tvTototalCountDmgRp = (TextView) rootView.findViewById(R.id.tvTototalCountDmgRp);
        TextView tvDate = (TextView) rootView.findViewById(R.id.tvDate);


        EnDamageLoadListResponse.Content content = list.get(position);

        tvVehicleID.setText(String.valueOf(content.getVehicleID()));
        tvVinCodeDmgRp.setText(content.getVIN());
        tvModel.setText(content.getModel());
        tvYear.setText(content.getYear());
        tvMake.setText(content.getMake());
        tvShipper.setText(content.getShipper());
        tvVehicleCount.setText(String.valueOf(content.getVehicleCount()));
        tvTototalCountDmgRp.setText(String.valueOf(content.getTotalCount()));

        tvDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(content.getDT().substring(8, 21)))));

        return rootView;
    }
}
