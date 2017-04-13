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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.libre.mylibs.MyUtils;

import java.io.File;

import retrofit.mime.TypedFile;
import tms.com.libre.tms.common.AppContanst;

import static tms.com.libre.tms.R.id.imgDamage;

public class AcOnRoute extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private RelativeLayout rlBack;
    private TextView tvTitle;
    private RelativeLayout rlUpdateStatusOnRoute, rlDMGonRoute;
    private ImageView imgDMGonRoute;
    private TypedFile image_edit;
    private Button btnDMGonRoute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_on_route);
        init();
    }

    public void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        rlBack = (RelativeLayout) toolbar.findViewById(R.id.rlBack);
        tvTitle = (TextView) toolbar.findViewById(R.id.tvTitleLogin);
        tvTitle.setText("On Route");
        rlUpdateStatusOnRoute = (RelativeLayout) findViewById(R.id.rlUpdateStatusOnRoute);
        rlDMGonRoute = (RelativeLayout) findViewById(R.id.rlDmgOnRoute);
        imgDMGonRoute = (ImageView) findViewById(R.id.imgDMGonRoute);
        btnDMGonRoute = (Button) findViewById(R.id.btnDMGonRoute);

        rlUpdateStatusOnRoute.setOnClickListener(this);
        rlBack.setOnClickListener(this);
        rlDMGonRoute.setOnClickListener(this);
        btnDMGonRoute.setOnClickListener(this);
        imgDMGonRoute.setOnClickListener(this);

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
            case R.id.rlBack:
                startActivity(new Intent(AcOnRoute.this, AcMain.class));
                break;
            case R.id.rlUpdateStatusOnRoute:
                startActivity(new Intent(AcOnRoute.this, AcDrop.class));
                break;
            case R.id.rlDmgOnRoute:
                seletePhotoAction();
                break;
            case R.id.btnDMGonRoute:
                seletePhotoAction();
                break;
            case R.id.imgDMGonRoute:
                showPopUpImage(imgDMGonRoute.getDrawable(), 1);
                break;
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        imgDMGonRoute.setImageBitmap(photo);
                        File file = MyUtils.saveBitmapToFile(MyUtils.resize(photo, 800, 800), "picture" + ".jpg");
                        image_edit = new TypedFile("multipart/form-data", file);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK) {
                        displayImageFromGallery(data, imgDMGonRoute);
                    }
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }
    }
}
