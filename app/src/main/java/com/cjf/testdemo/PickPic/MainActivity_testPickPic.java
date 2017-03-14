package com.cjf.testdemo.PickPic;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cjf.testdemo.R;


public class MainActivity_testPickPic extends AppCompatActivity {
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


        testBtn = (Button)findViewById(R.id.testBtn1);
        testBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity_testPickPic.this, "first first acitity click!!!", Toast.LENGTH_SHORT).show();
            }
        });

        Button testBtn2 = (Button)findViewById(R.id.testBtn2);
        testBtn2.setText("open second activity");
        testBtn2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_testPickPic.this, SecondeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
