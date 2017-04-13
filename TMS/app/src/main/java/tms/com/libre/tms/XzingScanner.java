package tms.com.libre.tms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * Created by GL62 on 4/13/2017.
 */

public class XzingScanner extends AppCompatActivity {
    private ZXingScannerView mZXingScannerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.xzing);
        scanCode();
    }


    public void scanCode(View view) {
        mZXingScannerView = new ZXingScannerView(this);
        setContentView(mZXingScannerView);
        mZXingScannerView.setResultHandler(new Zxing());
        mZXingScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mZXingScannerView.stopCamera();
    }

    class Zxing implements ZXingScannerView.ResultHandler {

        @Override
        public void handleResult(Result result) {
            String code = result.getText();
            Intent intent = new Intent(getBaseContext(), AcPickup.class);
            intent.putExtra("vincode", code);
            setResult(RESULT_OK, intent);
            mZXingScannerView.stopCamera();
            finish();
        }
    }
}
