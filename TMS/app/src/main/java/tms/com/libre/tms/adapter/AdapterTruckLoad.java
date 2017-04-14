package tms.com.libre.tms.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.libre.mylibs.MyUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tms.com.libre.tms.AcPickup;
import tms.com.libre.tms.AppUtils;
import tms.com.libre.tms.R;
import tms.com.libre.tms.entry.EnTrucLoadStatusListResponse;
import tms.com.libre.tms.entry.EnTruckLoadResponse;
import tms.com.libre.tms.serivces.AppApi;

/**
 * Created by quangnv on 4/11/17.
 */

public class AdapterTruckLoad extends BaseAdapter {
    private ArrayList<EnTruckLoadResponse.Content> listTruckLoads;
    private Context mContext;
    EnTrucLoadStatusListResponse.Content list = new EnTrucLoadStatusListResponse.Content();

    public AdapterTruckLoad(Context context, ArrayList<EnTruckLoadResponse.Content> listTruckLoads) {
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
        final TextView tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);


        final EnTruckLoadResponse.Content content = listTruckLoads.get(i);
        AppApi appApi = new AppApi();
        appApi.services().getTruckLoadStatusList(new Callback<EnTrucLoadStatusListResponse>() {
            @Override
            public void success(EnTrucLoadStatusListResponse enTrucLoadStatusListResponse, Response response) {
                if (enTrucLoadStatusListResponse.getStatusCode() == 200) {
                    list = enTrucLoadStatusListResponse.getContent();
                    tvStatus.setText(AppUtils.getStatus(mContext, list, content,tvStatus));
                } else {
                    MyUtils.showToast(mContext.getApplicationContext(), mContext.getString(R.string.load_trucklist_fail));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MyUtils.showToast(mContext.getApplicationContext(), mContext.getString(R.string.load_trucklist_fail));
            }
        });

        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(content.getDT().substring(6, 19))));
        tvTrucloadNo.setText(content.getTruckNo());
        tvCreated.setText(dateString);

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
