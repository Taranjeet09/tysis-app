package com.example.rawin.taa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class Home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView imageView;
    FloatingActionButton b1 = MainActivity.b;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Fragment frag = this;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                frag.startActivityForResult(intent, 111);

            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 111) {
                // Do something with imagePath

                Bitmap bp1 = (Bitmap) data.getExtras().get("data");
                //  imageView.setImageBitmap(photo);
                //  imageView.buildDrawingCache();
                // Bitmap bp1 = imageView.getDrawingCache();
                FileOutputStream os = null;
                File strc = Environment.getExternalStorageDirectory();
                File f = new File(strc.getAbsolutePath(), "Hell");
                f.mkdirs();
                String fname = String.format("%d.jpg", System.currentTimeMillis());
                File of = new File(f, fname);

                try {
                    os = new FileOutputStream(of);
                    bp1.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP


            }
        }
    }

    /* public Uri getImageUri(Context inContext, Bitmap inImage) {
         ByteArrayOutputStream bytes = new ByteArrayOutputStream();
         inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
             String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "hello", null);
         return Uri.parse(path);
     }
     public String getRealPathFromURI(Uri contentUri) {
         Cursor cursor = null;
         try {
             String[] proj = { MediaStore.Images.Media.DATA };
             cursor = getActivity().getContentResolver().query(contentUri,  proj, null, null, null);
             int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
             cursor.moveToFirst();
             return cursor.getString(column_index);
         } finally {
             if (cursor != null) {
                 cursor.close();
             }
         }
     }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


}
