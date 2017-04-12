package tms.com.libre.tms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.libre.mylibs.MyUtils;

import tms.com.libre.tms.common.AppContanst;

/**
 * Created by quangnv on 4/12/17.
 */

public class AccAddNote extends AppCompatActivity implements View.OnClickListener {
    private EditText edtAddNotes;
    private Button btnCancel;
    private Button btnAddNotes;
    private String strNotes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_add_note);
        init();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddNotes:
                strNotes = edtAddNotes.getText().toString().trim();
                MyUtils.insertStringData(getApplicationContext(), AppContanst.NOTES, strNotes);
                AccAddNote.this.setResult(RESULT_OK);
                AccAddNote.this.finish();
                break;
            case R.id.btnCancel:
                AccAddNote.this.setResult(RESULT_CANCELED);
                AccAddNote.this.finish();
                break;
        }

    }

    public void enableSubmitIfReady() {
        boolean isReady = edtAddNotes.getText().toString().length() > 0;
        btnAddNotes.setEnabled(isReady);
    }


    public void init() {
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnAddNotes = (Button) findViewById(R.id.btnAddNotes);
        btnCancel.setOnClickListener(this);
        btnAddNotes.setOnClickListener(this);
        btnAddNotes.setEnabled(false);
        edtAddNotes = (EditText) findViewById(R.id.edtAddNote);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.count_text_changed_items, menu);
        final MenuItem objectNearbySettingsItem = menu.findItem(R.id.strTotal);
        edtAddNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int count = edtAddNotes.length();
                enableSubmitIfReady();
                objectNearbySettingsItem.setTitle(getString(R.string.str_total, count));
            }
        });
        return true;
    }
}
