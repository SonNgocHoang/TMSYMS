package tms.com.libre.tms;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.zxing.Result;
import com.libre.mylibs.MyUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit.mime.TypedFile;
import tms.com.libre.tms.common.AppContanst;
import tms.com.libre.tms.common.IntentManager;

public class AcPickup extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignature, btnUpdateStatus, btnScanVin, btnDamage;
    private TextView tvVinCode, tvNotes, tvTitle;
    private ImageView imgSignature, imgDamage;
    private RelativeLayout rlChangeNotes;
    private static int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 999;
    private static final int NOTES_REQUEST_CODE = 4;
    private TypedFile image_edit;
    private RelativeLayout layoutNotes, rlBack;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_pickup);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasWritePermission = this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int hasCameraPermission = this.checkSelfPermission(Manifest.permission.CAMERA);

            List<String> permissions = new ArrayList<String>();
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
            }

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
            } else {
            }

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            } else {
            }

            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
            }
        }
        checkPermission();
        init();


    }

    public void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        rlBack = (RelativeLayout) toolbar.findViewById(R.id.rlBack);
        tvTitle = (TextView) toolbar.findViewById(R.id.tvTitleLogin);
        tvTitle.setText("Pick Up");

        layoutNotes = (RelativeLayout) findViewById(R.id.layoutNotes);
        btnSignature = (Button) findViewById(R.id.btnSignature);
        btnScanVin = (Button) findViewById(R.id.btnScanVin);
        btnDamage = (Button) findViewById(R.id.btnDamage);
        btnUpdateStatus = (Button) findViewById(R.id.btnUpdateStatus);
        rlChangeNotes = (RelativeLayout) findViewById(R.id.rlChangeNotes);
        tvVinCode = (TextView) findViewById(R.id.tvVinCode);
        tvNotes = (TextView) findViewById(R.id.tvNotes);
        imgSignature = (ImageView) findViewById(R.id.imgSignature);
        imgDamage = (ImageView) findViewById(R.id.imgDamage);

        rlBack.setOnClickListener(this);
        layoutNotes.setOnClickListener(this);
        btnSignature.setOnClickListener(this);
        btnScanVin.setOnClickListener(this);
        btnDamage.setOnClickListener(this);
        btnUpdateStatus.setOnClickListener(this);
        rlChangeNotes.setOnClickListener(this);
        tvVinCode.setOnClickListener(this);
        tvNotes.setOnClickListener(this);
        imgSignature.setOnClickListener(this);
        imgDamage.setOnClickListener(this);
    }

    //Show dialog Signature
    public void showSignatureDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_signature, null);
        dialogBuilder.setView(dialogView);

        final SignaturePad signaturePad = (SignaturePad) dialogView.findViewById(R.id.signaturePad);

        dialogBuilder.setTitle(R.string.get_signature);
        dialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //get bitmap from signaturepad
                Bitmap bitmap = signaturePad.getSignatureBitmap();
                imgSignature.setImageBitmap(bitmap);
                Log.d("DIALOG", "onClick: " + bitmap.toString());

                //save to file
                Random rand = new Random();
                int n = rand.nextInt(50) + 1;
                MyUtils.saveBitmapToFile(MyUtils.resize(bitmap, 800, 800), "picture" + n + ".jpg");
                dialog.dismiss();
            }
        });
        dialogBuilder.setNegativeButton(R.string.clear, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                signaturePad.clear();
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        Log.d("DIALOG", "showChangeLangDialog: ");
        b.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlBack:
                startActivity(new Intent(AcPickup.this, AcMain.class));
                break;
            case R.id.btnSignature:
                showSignatureDialog();
                break;
            case R.id.btnScanVin:
                startActivityForResult(new Intent(AcPickup.this, XzingScanner.class), 10);
                break;
            case R.id.btnDamage:
                seletePhotoAction();
                break;
            case R.id.btnUpdateStatus:
                startActivity(new Intent(AcPickup.this, AcOnRoute.class));
                break;
            case R.id.rlChangeNotes:
                IntentManager.startActivityForResult(AcPickup.this, AccAddNote.class, null, NOTES_REQUEST_CODE, null);
                break;
            case R.id.tvVinCode:
                break;
            case R.id.tvNotes:
                IntentManager.startActivityForResult(AcPickup.this, AccAddNote.class, null, NOTES_REQUEST_CODE, null);
                break;
            case R.id.imgSignature:
                showPopUpImage(imgSignature.getDrawable(), 0);
                break;
            case R.id.imgDamage:
                showPopUpImage(imgDamage.getDrawable(), 1);
                break;
            case R.id.layoutNotes:
                IntentManager.startActivityForResult(AcPickup.this, AccAddNote.class, null, NOTES_REQUEST_CODE, null);
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

    public void showPopUpImage(Drawable drawable, int i) {
        final ImagePopup imagePopup = new ImagePopup(this);
        imagePopup.setBackgroundColor(Color.GRAY);
        imagePopup.setHideCloseIcon(true);
        imagePopup.setImageOnClickClose(true);
        if (i == 0) {
            imagePopup.setWindowWidth(800);
            imagePopup.setWindowHeight(800);
            imagePopup.initiatePopup(drawable);
        } else {
            imagePopup.setWindowWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            imagePopup.setWindowHeight(ViewGroup.LayoutParams.MATCH_PARENT);

            imagePopup.initiatePopup(drawable);

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        try {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK) {
                        Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                        imgDamage.setImageBitmap(photo);
                        File file = MyUtils.saveBitmapToFile(MyUtils.resize(photo, 800, 800), "picture" + ".jpg");
                        image_edit = new TypedFile("multipart/form-data", file);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK) {
                        displayImageFromGallery(imageReturnedIntent, imgDamage);
                    }
                    break;
                case NOTES_REQUEST_CODE:
                    Log.d("davaoday", "aaaa");
                    tvNotes.setText(MyUtils.getStringData(getApplicationContext(), AppContanst.NOTES));
                    break;

            }
            switch (resultCode) {
                case RESULT_OK:
                    if (requestCode == 10) {
                        String code = imageReturnedIntent.getStringExtra("code");
                        tvVinCode.setText(code);
                    }
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }

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

    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 999);
        } else {
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.CAMERA}, 998);
        } else {
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case 999:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                }
                break;
            case 998:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                }
                break;
            default:
                break;
        }
    }
}
