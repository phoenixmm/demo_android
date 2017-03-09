package com.cjf.testdemo.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by sohu-mac on 16/10/25.
 */
public class NoteView extends View {

    public NoteView(Context context) {
        super(context);
    }

    public NoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("cjf---", "NoteView | dispatchTouchEvent --> " + TouchUtil.getTouchAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
//        return true;
//        return false;
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.i("cjf---", "NoteView | onInterceptTouchEvent --> " + TouchUtil.getTouchAction(ev.getAction()));
//        return super.onInterceptTouchEvent(ev);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d("cjf---", "NoteView | onTouchEvent --> " + TouchUtil.getTouchAction(ev.getAction()));
        return super.onTouchEvent(ev);
//        return true;
//        return false;
    }

}
