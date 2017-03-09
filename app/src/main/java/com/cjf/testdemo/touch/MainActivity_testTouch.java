package com.cjf.testdemo.touch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.cjf.testdemo.R;

public class MainActivity_testTouch extends AppCompatActivity {
    public String LOG_TAG = "cjf---touch";

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_touch);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.w("cjf---", "MainActivity | dispatchTouchEvent --> " + TouchUtil.getTouchAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
//        return true;
//        return false;
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.i("cjf---", "MainActivity | onInterceptTouchEvent --> " + TouchUtil.getTouchAction(ev.getAction()));
//        return super.onInterceptTouchEvent(ev);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.w("cjf---", "MainActivity | onTouchEvent --> " + TouchUtil.getTouchAction(event.getAction()));
        return super.onTouchEvent(event);
//        return true;
//        return false;
    }
}
