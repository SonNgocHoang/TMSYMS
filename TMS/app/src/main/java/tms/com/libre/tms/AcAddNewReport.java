package tms.com.libre.tms;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.libre.mylibs.MyUtils;

import java.io.File;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import tms.com.libre.tms.common.AppContanst;
import tms.com.libre.tms.common.IntentManager;
import tms.com.libre.tms.entry.EnDamageCodeResponse;
import tms.com.libre.tms.entry.EnDamageSeverityResponse;
import tms.com.libre.tms.entry.EnDamageTypeResponse;
import tms.com.libre.tms.serivces.AppApi;

public class AcAddNewReport extends AppCompatActivity implements View.OnClickListener {
    private Spinner spServerity, spCodeDamage, spTypeDamage;
    private RelativeLayout rlChangeNotesReport, rlSignOut, rlCalendar, rlTakePictureAddNewRp;
    private static final int NOTES_REQUEST_CODE = 4;
    private TextView tvNotesAddReport,tvTrucList;
    private Toolbar toolbar;
    private ImageView imgSignOut, imgCalendar;
    private TypedFile image_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_add_new_report);
        init();
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rlSignOut = (RelativeLayout) toolbar.findViewById(R.id.rlSignOut);
        rlCalendar = (RelativeLayout) toolbar.findViewById(R.id.rlCalendar);
        imgSignOut = (ImageView) toolbar.findViewById(R.id.imgSignOut);
        imgCalendar = (ImageView) toolbar.findViewById(R.id.imgCalendar);
        imgSignOut.setImageResource(R.drawable.back_arrow);
        imgCalendar.setImageResource(R.drawable.save);
        tvTrucList = (TextView)toolbar.findViewById(R.id.tvTrucList);
        tvTrucList.setText(R.string.addnewdmg);

        spServerity = (Spinner) findViewById(R.id.spServerity);
        spCodeDamage = (Spinner) findViewById(R.id.spCodeDamage);
        spTypeDamage = (Spinner) findViewById(R.id.spTypeDamage);
        rlChangeNotesReport = (RelativeLayout) findViewById(R.id.rlChangeNotesReport);
        rlTakePictureAddNewRp = (RelativeLayout) findViewById(R.id.rlTakePictureAddNewRp);
        tvNotesAddReport = (TextView) findViewById(R.id.tvNotesAddReport);


        rlChangeNotesReport.setOnClickListener(this);
        imgSignOut.setOnClickListener(this);
        imgCalendar.setOnClickListener(this);
        rlCalendar.setOnClickListener(this);
        rlSignOut.setOnClickListener(this);
        rlTakePictureAddNewRp.setOnClickListener(this);
        getSeverity();
        getDamageCode();
        getDamageType();
    }

    public void getDamageType() {
        AppApi appApi = new AppApi();
        appApi.services().getDamageType(new Callback<EnDamageTypeResponse>() {
            @Override
            public void success(EnDamageTypeResponse enDamageTypeResponse, Response response) {

                if (enDamageTypeResponse.getStatuscode() == 200) {
                    ArrayList<String> array = new ArrayList<>();
                    for (int i = 0; i < enDamageTypeResponse.getContent().size(); i++) {
//                        adapter.add(enDamageSeverityResponse.getContent().get(i).getName());
                        array.add(enDamageTypeResponse.getContent().get(i).getName());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, array);
                    spTypeDamage.setAdapter(adapter);
                } else {
                    MyUtils.showToast(getApplicationContext(), getString(R.string.load_fail));
                }

            }

            @Override
            public void failure(RetrofitError error) {
                MyUtils.showToast(getApplicationContext(), getString(R.string.load_fail));

            }
        });
    }

    public void getDamageCode() {
        AppApi appApi = new AppApi();
        appApi.services().getDamageCode(new Callback<EnDamageCodeResponse>() {
            @Override
            public void success(EnDamageCodeResponse enDamageCodeResponse, Response response) {
                if (enDamageCodeResponse.getStatusCode() == 200) {
                    ArrayList<String> array = new ArrayList<>();
                    for (int i = 0; i < enDamageCodeResponse.getContent().size(); i++) {
//                        adapter.add(enDamageSeverityResponse.getContent().get(i).getName());
                        array.add(enDamageCodeResponse.getContent().get(i).getName());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, array);
                    spCodeDamage.setAdapter(adapter);

                } else {
                    MyUtils.showToast(getApplicationContext(), getString(R.string.load_fail));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MyUtils.showToast(getApplicationContext(), getString(R.string.load_fail));

            }
        });
    }

    public void getSeverity() {
        AppApi appApi = new AppApi();
        appApi.services().getDamageSeverity(new Callback<EnDamageSeverityResponse>() {
            @Override
            public void success(EnDamageSeverityResponse enDamageSeverityResponse, Response response) {
                if (enDamageSeverityResponse.getStatusCode() == 200) {
                    ArrayList<String> array = new ArrayList<>();
//                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AcAddNewReport.this.getApplicationContext(), android.R.array.organizationTypes, R.layout.item_spinner);
//                    adapter.setDropDownViewResource(R.layout.item_drop_down_spinner);
                    for (int i = 0; i < enDamageSeverityResponse.getContent().size(); i++) {
//                        adapter.add(enDamageSeverityResponse.getContent().get(i).getName());
                        array.add(enDamageSeverityResponse.getContent().get(i).getName());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, array);
                    spServerity.setAdapter(adapter);
                } else {
                    MyUtils.showToast(getApplicationContext(), getString(R.string.load_fail));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MyUtils.showToast(getApplicationContext(), getString(R.string.load_fail));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlChangeNotesReport:
                IntentManager.startActivityForResult(AcAddNewReport.this, AccAddNote.class, null, NOTES_REQUEST_CODE, null);
                break;
            case R.id.rlCalendar:

                break;
            case R.id.rlSignOut:
                startActivity(new Intent(AcAddNewReport.this, AcDamageReport.class));
                break;
            case R.id.rlTakePictureAddNewRp:
                seletePhotoAction();
                break;
        }
    }

    public void seletePhotoAction() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);

        b.setTitle(this.getString(R.string.seleted_photo_upload));
        b.setMessage(this.getString(R.string.select_where_photo));
        b.setPositiveButton(getString(R.string.from_camera), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } catch (SecurityException s) {
                }

            }
        });
        b.setNegativeButton(getString(R.string.from_library), new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which)

            {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
            }

        });

        b.create().show();
    }

        private void displayImageFromGallery(Intent data, ImageView imageView) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String imgDecodableString = cursor.getString(columnIndex);
        cursor.close();
        Bitmap bitmap = MyUtils.bitmapRotate(imgDecodableString);
        imageView.setImageBitmap(bitmap);
        File file = MyUtils.saveBitmapToFile(MyUtils.resize(bitmap, 800, 800), "picture" + ".jpg");
        image_edit = new TypedFile("multipart/form-data", file);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case NOTES_REQUEST_CODE:
                    Log.d("davaoday", "aaaa");
                    tvNotesAddReport.setText(MyUtils.getStringData(getApplicationContext(), AppContanst.NOTES));
                    break;
//                case 0:
//                    if (resultCode == RESULT_OK) {
//                        Bitmap photo = (Bitmap) data.getExtras().get("data");
//                        imgDMGonRoute.setImageBitmap(photo);
//                        File file = MyUtils.saveBitmapToFile(MyUtils.resize(photo, 800, 800), "picture" + ".jpg");
//                        image_edit = new TypedFile("multipart/form-data", file);
//                    }
//
//                    break;
//                case 1:
//                    if (resultCode == RESULT_OK) {
//                        displayImageFromGallery(data, imgDMGonRoute);
//                    }
//                    break;
            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.try_again, Toast.LENGTH_LONG)
                    .show();
        }
    }
}
