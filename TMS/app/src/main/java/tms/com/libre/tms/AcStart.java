package tms.com.libre.tms;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.libre.mylibs.MyUtils;

import java.util.Locale;

import tms.com.libre.tms.common.AppContanst;
import tms.com.libre.tms.fragment.DialogFragmentLanguage;

public class AcStart extends AppCompatActivity implements View.OnClickListener , DialogFragmentLanguage.OnHeadlineSelectedListener
{
    private Button btnYms;
    private Button btnTms;
    private Button btnLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_start);
        init();
    }

    public void init(){
        btnYms =(Button) findViewById(R.id.btnYms);
        btnYms.setOnClickListener(this);
        btnTms = (Button) findViewById(R.id.btnTms);
        btnTms.setOnClickListener(this);
        btnLanguage =(Button) findViewById(R.id.btnLanguage);
        btnLanguage.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnYms:
                //YMS
                break;
            case R.id.btnTms:
                startActivity(new Intent(AcStart.this, AcLogin.class));
                //TMS
                break;
            case R.id.btnLanguage:
                DialogFragmentLanguage dialogFragmentLanguage = new DialogFragmentLanguage();
                dialogFragmentLanguage.show(AcStart.this.getSupportFragmentManager(), "DIALOG_TAG");
                break;
        }

    }

    @Override
    public void OnItemSelected(int idLanguage) {
        if (idLanguage == 1) {
            MyUtils.insertStringData(AcStart.this, AppContanst.LANGUAGE, "en");
            setupLanguage("en");
        } else if (idLanguage == 2) {
            MyUtils.insertStringData(AcStart.this, AppContanst.LANGUAGE, "fr");
            setupLanguage("fr");
        }
    }

    private void setupLanguage(String language) {
        final Intent intent = new Intent(this, AcStart.class);
        String languageToLoad = language;
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//it will recreate it self with new language.
        startActivity(intent);
        finish();
    }
}
