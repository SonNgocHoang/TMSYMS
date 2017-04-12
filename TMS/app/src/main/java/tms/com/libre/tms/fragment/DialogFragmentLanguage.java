package tms.com.libre.tms.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import tms.com.libre.tms.R;


/**
 * Created by quangnv on 4/11/2017.
 */

public class DialogFragmentLanguage extends DialogFragment implements View.OnClickListener {
    private Context currentActivity;
    private AlertDialog dialog;
    private LinearLayout layoutEnglish;
    private LinearLayout layoutFrench;
    private LinearLayout layoutArab;
    private OnHeadlineSelectedListener mData;


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = getContext();
        View view = LayoutInflater.from(currentActivity).inflate(R.layout.dialog_fragment_language, null);
        init(view);
        dialog = new AlertDialog.Builder(currentActivity).setView(view).setTitle("Select your language !").create();

        return dialog;
    }

    public void init(View view) {
        layoutEnglish = (LinearLayout) view.findViewById(R.id.languageEnglish);
        layoutEnglish.setOnClickListener(this);
        layoutFrench = (LinearLayout) view.findViewById(R.id.languageFrench);
        layoutFrench.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.languageEnglish:
                layoutEnglish.setBackgroundResource(R.color.seleted_language);
                layoutFrench.setBackgroundResource(R.color.white);
                sentDataToMain(1);
                break;
            case R.id.languageFrench:
                layoutFrench.setBackgroundResource(R.color.seleted_language);
                layoutEnglish.setBackgroundResource(R.color.white);
                sentDataToMain(2);
                break;
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mData = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

    }

    public void sentDataToMain(int idLanguage) {
        mData.OnItemSelected(idLanguage);
    }

    public interface OnHeadlineSelectedListener {
        void OnItemSelected(int idLanguage);
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = 750;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

}
