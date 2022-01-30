package com.ian.mystorage;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.class.getName();
    ContentResolver cr;
    String[] mProjection = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED
    };
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cr = getApplicationContext().getContentResolver();


    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent it = result.getData();
                    Uri uri = it.getData();
                    getImage(uri);

                }
            }
    );

    public void getImage(Uri uri){
        cursor = cr.query(uri,mProjection,null,null,null);
//        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);

        if (cursor != null && cursor.moveToFirst()) {
//            long id = cursor.getLong(idColumn);
            @SuppressLint("Range") String imgPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            String fileName = cursor.getString(nameColumn);
//            Log.d(TAG, String.valueOf(id));
            Log.d(TAG, fileName);
            Log.d(TAG, imgPath);
        }
    }

    public void search(View view) {
        Intent it = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(it);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_SELECT && resultCode == RESULT_OK) {
//            Uri selectedImage = data.getData();
//            Log.d(TAG, selectedImage.toString());
//
//            Cursor cursor = cr.query(selectedImage, mProjection,
//                    null, null, null);
//            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
//            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
//
//            if (cursor != null && cursor.moveToFirst()) {
//                long id = cursor.getLong(idColumn);
//                @SuppressLint("Range") String imgPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//                String fileName = cursor.getString(nameColumn);
//                Log.d(TAG, String.valueOf(id));
//                Log.d(TAG, fileName);
//                Log.d(TAG, imgPath);
//            }
//        }
//    }

}