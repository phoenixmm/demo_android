package com.cjf.testdemo.PickPic;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cjf.testdemo.R;
import com.cjf.testdemo.util.FileUrlConvert;


public class SecondeActivity extends AppCompatActivity {
    public static int IMAGE_REQUEST = 1001;
    public String LOG_TAG = "cjf------";

    Button testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pickpic);

        //just like Build.VERSION_CODES.JELLY_BEAN (= 16)
        Log.d(LOG_TAG, " Build.VERSION.SDK_INT = " + Build.VERSION.SDK_INT);
        if (Build.VERSION_CODES.JELLY_BEAN >= Build.VERSION.SDK_INT) {

        }

        TextView tv = (TextView)findViewById(R.id.textView1);
        tv.setHeight(300 * 3);
        tv.setWidth(300 * 3);

        testBtn = (Button)findViewById(R.id.testBtn1);
        testBtn.setVisibility(View.GONE);
        testBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(SecondeActivity.this, "second sencond acitity click!!!", Toast.LENGTH_SHORT).show();
            }
        });

        Button testBtn2 = (Button)findViewById(R.id.testBtn2);
        testBtn2.setText("open pick pic");
        testBtn2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent, IMAGE_REQUEST);

//                Intent intent = new Intent();
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("image/*");
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                }else {
//                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//                }
//                startActivityForResult(intent, IMAGE_REQUEST);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (IMAGE_REQUEST == requestCode && resultCode == RESULT_OK && data.getData() != null) {
            final Uri uri = data.getData(); //like:  content://media/external/images/media/113
            Log.i("cjf---", uri.toString());

            String filePath = FileUrlConvert.getPath(SecondeActivity.this, uri); //like "/storage/emulated/0/DCIM/Camera/IMG_20161114_145142.jpg";
            Log.i("cjf---", filePath);
        }
    }
}
