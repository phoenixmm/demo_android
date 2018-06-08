package com.cjf.testdemo;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;


public class MainActivity extends AppCompatActivity {
    public String LOG_TAG = "cjf------";

    Button testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //just like Build.VERSION_CODES.JELLY_BEAN (= 16)
        Log.d(LOG_TAG, " Build.VERSION.SDK_INT = " + Build.VERSION.SDK_INT);
        if (Build.VERSION_CODES.JELLY_BEAN >= Build.VERSION.SDK_INT) {

        }


        testBtn = (Button)findViewById(R.id.testBtn1);
        testBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "first acitity click!!!", Toast.LENGTH_LONG).show();

                Context context = MainActivity.this.getApplicationContext();





            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
