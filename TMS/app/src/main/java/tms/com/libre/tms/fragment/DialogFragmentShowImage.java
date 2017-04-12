package tms.com.libre.tms.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tms.com.libre.tms.R;


public class DialogFragmentShowImage extends DialogFragment implements View.OnClickListener {
    private Context currentActivity;
    private AlertDialog dialog;
    private TextView tvCancel, tvChooseAnother;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        currentActivity = getContext();
        View view = LayoutInflater.from(currentActivity).inflate(R.layout.dialog_fragment_show_image, null);
        init(view);
        dialog = new AlertDialog.Builder(currentActivity).setView(view).setTitle("Your Image").create();

        return dialog;

    }

    public void init(View view) {
        tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        tvChooseAnother = (TextView) view.findViewById(R.id.tvChooseAnother);

        tvCancel.setOnClickListener(this);
        tvChooseAnother.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvCancel:

                break;
            case R.id.tvChooseAnother:

                break;
        }

    }
    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }
}
