package com.cjf.testdemo.viewpage;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by sohu-mac on 16/10/25.
 */
public class ImageViewPager extends ViewPager {
    static final int NONE = 0;
    static final int DRAG_HORIZONTAL = 1;
    static final int DRAG_VERTICAL = 2;
    int mode = NONE;

    private PointF downPoint;
    private float currentY;
    private ViewPager.LayoutParams lpViewPager;

    public ImageViewPager(Context context) {
        super(context);
    }

    public ImageViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

/*
    private float[] handleMotionEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                downPoint = new PointF(event.getX(0), event.getY(0));
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                PointF curr = new PointF(event.getX(0), event.getY(0));
                return new float[] { curr.x - downPoint.x, curr.y - downPoint.y };
        }
        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.w("cjf---", "onTouchEvent --> " + TouchUtil.getTouchAction(event.getAction())
                + " x=" + event.getX() + " y=" + event.getY() + " mode=" + mode);

        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
            super.onTouchEvent(event);
        }

        float[] difference = handleMotionEvent(event);

        if(mode == NONE && difference != null && Math.abs(difference[0]) >= Math.abs(difference[1])) {
            mode = DRAG_HORIZONTAL; // need to send event to super class.
        } else if(mode == NONE && difference != null) {
            mode = DRAG_VERTICAL; //scrolling up/down
        }

        if (mode != DRAG_VERTICAL) {
            return super.onTouchEvent(event);
        }

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                downPoint = new PointF(event.getX(0), event.getY(0));
                break;
            case MotionEvent.ACTION_MOVE:
                currentY = event.getY(0);
                GestureViewPager.this.setScrollY((int)(currentY - downPoint.y));
                break;
            case MotionEvent.ACTION_UP:
                currentY = event.getY(0);
                Log.w("cjf---", "onTouchEvent --> " + TouchUtil.getTouchAction(event.getAction())
                        + " x=" + event.getX() + " y=" + event.getY() + " mode=" + mode);
                if(Math.abs(currentY - downPoint.y) > 300) {
                    GestureViewPager.this.setVisibility(GONE);
                }
                break;
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.i("cjf---", "onInterceptTouchEvent --> " + TouchUtil.getTouchAction(event.getAction())
                + " x=" + event.getX() + " y=" + event.getY()+ " mode=" + mode);

        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
            super.onInterceptTouchEvent(event);
        }

        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
            mode = NONE;
        }


        float[] difference = handleMotionEvent(event);
        if(mode == NONE && difference != null && Math.abs(difference[0]) >= Math.abs(difference[1])) {
            mode = DRAG_HORIZONTAL; // need to send event to super class.
        } else if(mode == NONE && difference != null) {
            mode = DRAG_VERTICAL; //scrolling up/down
        }

        if (mode != DRAG_VERTICAL) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }
*/

}
