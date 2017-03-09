package com.cjf.testdemo.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by sohu-mac on 16/10/25.
 */
public class ParentGroup extends LinearLayout {

    public ParentGroup(Context context) {
        super(context);
    }

    public ParentGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("cjf---", "ParentGroup | dispatchTouchEvent --> " + TouchUtil.getTouchAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
//        return true;
//        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("cjf---", "ParentGroup | onInterceptTouchEvent --> " + TouchUtil.getTouchAction(ev.getAction()));
        return super.onInterceptTouchEvent(ev);
//        return true;
//        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d("cjf---", "ParentGroup | onTouchEvent --> " + TouchUtil.getTouchAction(ev.getAction()));
        return super.onTouchEvent(ev);
//        return true;
//        return false;
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        super.setOnLongClickListener(l);
    }
}
