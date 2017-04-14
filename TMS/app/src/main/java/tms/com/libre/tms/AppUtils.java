package tms.com.libre.tms;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import tms.com.libre.tms.entry.EnTrucLoadStatusListResponse;
import tms.com.libre.tms.entry.EnTruckLoadResponse;

/**
 * Created by quangnv on 4/11/17.
 */

public class AppUtils {
    private ProgressDialog progressDialog;

    public static String getStatus(Context context, EnTrucLoadStatusListResponse.Content l, EnTruckLoadResponse.Content e, TextView textView) {
        String status = "";
        if (l.getCompleted() == e.getStatus()) {
            status = context.getString(R.string.Completed);
            textView.setText(status);
            textView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }
        if (l.getDischarge() == e.getStatus()) {
            status = context.getString(R.string.Discharge);
            textView.setText(status);
            textView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_light));
        }
        if (l.getOnRoute() == e.getStatus()) {
            status = context.getString(R.string.OnRoute);
            textView.setText(status);
            textView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_orange_light));
        }
        if (l.getPickup() == e.getStatus()) {
            status = context.getString(R.string.Pickup);
            textView.setText(status);
            textView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_light));
        }
        if (l.getScheduled() == e.getStatus()) {
            status = context.getString(R.string.Scheduled);
            textView.setText(status);
            textView.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
        }
        return status;
    }


}
