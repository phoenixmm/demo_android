package com.cjf.testdemo.ImageBrowser;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


/**
 * Created by sohu-mac on 16/10/25.
 */
public class ImageBrowserViewPager extends ViewPager {

    public ImageBrowserViewPager(Context context) {
        super(context);
    }

    public ImageBrowserViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected PointF last;
    public ImageBrowserTouchImage mCurrentView;

    private float[] handleMotionEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                last = new PointF(event.getX(0), event.getY(0));
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                PointF curr = new PointF(event.getX(0), event.getY(0));
                return new float[] { curr.x - last.x, curr.y - last.y };

        }
        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.w("cjf---", "page onTouchEvent --> " + event.getAction()
                + " x=" + event.getX() + " y=" + event.getY());

        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
            super.onTouchEvent(event);
        }

        mCurrentView = ((ImageBrowserViewAdapter)this.getAdapter()).mCurrentView;
        float[] difference = handleMotionEvent(event);

        if (mCurrentView.pagerCanScroll()) {
            return super.onTouchEvent(event);
        } else {
            if (difference != null && mCurrentView.onRightSide
                    && difference[0] < 0) // move right
            {
                return super.onTouchEvent(event);
            }
            if (difference != null && mCurrentView.onLeftSide
                    && difference[0] > 0) // move  left
            {
                return super.onTouchEvent(event);
            }
            if (difference == null
                    && (mCurrentView.onLeftSide || mCurrentView.onRightSide)) {
                return super.onTouchEvent(event);
            }
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.i("cjf---", "page onInterceptTouchEvent --> " + event.getAction()
                + " x=" + event.getX() + " y=" + event.getY());

        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
            super.onInterceptTouchEvent(event);
        }

        mCurrentView = ((ImageBrowserViewAdapter)this.getAdapter()).mCurrentView;

        float[] difference = handleMotionEvent(event);

        try {
            if (mCurrentView.pagerCanScroll()) {
                return super.onInterceptTouchEvent(event);
            } else {
                if (difference != null && mCurrentView.onRightSide
                        && difference[0] < 0) // move right
                {
                    return super.onInterceptTouchEvent(event);
                }
                if (difference != null && mCurrentView.onLeftSide
                        && difference[0] > 0) // move  left
                {
                    return super.onInterceptTouchEvent(event);
                }
                if (difference == null
                        && (mCurrentView.onLeftSide || mCurrentView.onRightSide)) {
                    return super.onInterceptTouchEvent(event);
                }
            }
        } catch (IllegalArgumentException e) {
            //multi pointer touch will lead to
            // java.lang.IllegalArgumentException: pointerIndex out of range pointerIndex=-1 pointerCount=1.
            e.printStackTrace();
        }

        return false;
    }

}
