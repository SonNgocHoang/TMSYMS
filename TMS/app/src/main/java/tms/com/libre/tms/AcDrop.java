package tms.com.libre.tms;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.libre.mylibs.MyUtils;

import java.io.File;
import java.util.Random;

import retrofit.mime.TypedFile;

public class AcDrop extends AppCompatActivity implements View.OnClickListener {

    private Button btnDmgDrop, btnSignatureDrop;
    private ImageView imgDmgDrop, imgSignatureDrop;
    private TypedFile image_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_drop);
    }

    public void init() {
        btnDmgDrop = (Button) findViewById(R.id.btnDmgDrop);
        btnSignatureDrop = (Button) findViewById(R.id.btnSignatureDrop);
        imgDmgDrop = (ImageView) findViewById(R.id.imgDmgDrop);
        imgSignatureDrop = (ImageView) findViewById(R.id.imgSignatureDrop);

        btnDmgDrop.setOnClickListener(this);
        btnSignatureDrop.setOnClickListener(this);
        imgDmgDrop.setOnClickListener(this);
        imgSignatureDrop.setOnClickListener(this);
    }


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
                imgSignatureDrop.setImageBitmap(bitmap);
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSignatureDrop:
                showSignatureDialog();
                break;
            case R.id.btnDmgDrop:
                seletePhotoAction();
                break;
            case R.id.imgDmgDrop:
                showPopUpImage(imgDmgDrop.getDrawable(), 1);
                break;
            case R.id.imgSignatureDrop:
                showPopUpImage(imgDmgDrop.getDrawable(), 0);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        imgDmgDrop.setImageBitmap(photo);
                        File file = MyUtils.saveBitmapToFile(MyUtils.resize(photo, 800, 800), "picture" + ".jpg");
                        image_edit = new TypedFile("multipart/form-data", file);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK) {
                        displayImageFromGallery(data, imgDmgDrop);
                    }
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }
    }
}
