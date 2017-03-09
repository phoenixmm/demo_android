package com.cjf.testdemo.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by sohu-mac on 16/10/25.
 */
public class ChildGroup extends LinearLayout {

    public ChildGroup(Context context) {
        super(context);
    }

    public ChildGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("cjf---", "ChildGroup | dispatchTouchEvent --> " + TouchUtil.getTouchAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
//        return true;
//        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("cjf---", "ChildGroup | onInterceptTouchEvent --> " + TouchUtil.getTouchAction(ev.getAction()));
        return super.onInterceptTouchEvent(ev);
//        return true;
//        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d("cjf---", "ChildGroup | onTouchEvent --> " + TouchUtil.getTouchAction(ev.getAction()));
        return super.onTouchEvent(ev);
//        return true;
//        return false;
    }

}
