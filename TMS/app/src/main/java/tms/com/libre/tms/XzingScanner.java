package tms.com.libre.tms;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.Result;

import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * Created by GL62 on 4/13/2017.
 */

public class XzingScanner extends AppCompatActivity {
    private ZXingScannerView mZXingScannerView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private SurfaceView cameraView;
    private TextView barcodeInfo;
    private Button btnBack;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xzing);
        cameraView = (SurfaceView) findViewById(R.id.camera_view);
        barcodeInfo = (TextView) findViewById(R.id.code_info);
        btnBack = (Button) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent received = getIntent();
                String check = received.getStringExtra("pickup");
                if (check==null) {
                    Intent intent = new Intent(getBaseContext(), AcDrop.class);
                    setResult(RESULT_CANCELED, intent);
                    finish();
                } else {
                    Intent intent = new Intent(getBaseContext(), AcPickup.class);
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }
            }
        });

        barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.CODABAR)
                        .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(XzingScanner.this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
                        public void run() {
                            barcodeInfo.setText(    // Update the TextView
                                    barcodes.valueAt(0).displayValue
                            );
                            Intent received = getIntent();
                            String check = received.getStringExtra("pickup");
                            if (check==null) {
                                Intent intent = new Intent(getBaseContext(), AcDrop.class);
                                intent.putExtra("vincode", barcodes.valueAt(0).displayValue);
                                setResult(RESULT_OK, intent);
                                finish();
                            } else {
                                Intent intent = new Intent(getBaseContext(), AcPickup.class);
                                intent.putExtra("vincode", barcodes.valueAt(0).displayValue);
                                setResult(RESULT_OK, intent);
                                finish();
                            }

                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraSource.release();
        barcodeDetector.release();
    }

}


//    public void scanCode(View view) {
//        mZXingScannerView = new ZXingScannerView(this);
//        setContentView(mZXingScannerView);
//        mZXingScannerView.setResultHandler(new Zxing());
//        mZXingScannerView.startCamera();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mZXingScannerView.stopCamera();
//    }
//
//    class Zxing implements ZXingScannerView.ResultHandler {
//
//        @Override
//        public void handleResult(Result result) {
//            String code = result.getText();
//            Intent intent = new Intent(getBaseContext(), AcPickup.class);
//            intent.putExtra("vincode", code);
//            setResult(RESULT_OK, intent);
//            mZXingScannerView.stopCamera();
//            finish();
//        }
//    }

