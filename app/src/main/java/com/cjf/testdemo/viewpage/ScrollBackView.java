package com.cjf.testdemo.viewpage;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.cjf.testdemo.touch.TouchUtil;

/**
 * Created by sohu-mac on 16/10/25.
 */
public class ScrollBackView extends RelativeLayout {
    private static final int THRESHOLD_DIRECTION = 1;
    private static final int THRESHOLD_BACK = 200;
    private static final int THRESHOLD_MOVE = 30;

    private static final int MODE_NONE = 0;
    private static final int MODE_DRAG_HORIZONTAL = 1;
    private static final int MODE_DRAG_VERTICAL = 2;
    private int mode = MODE_NONE;

    private boolean bMove = false;
    private PointF downPoint;
    private float currentY;
    private int endPositionY;

    public ScrollBackView(Context context) {
        super(context);
    }

    public ScrollBackView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


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

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE:
                currentY = event.getY(0);
                ScrollBackView.this.setScrollY((int)(downPoint.y - currentY));
                if(Math.abs(currentY - downPoint.y) > THRESHOLD_MOVE) {
                    bMove = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                currentY = event.getY(0);
                Log.e("cjf---", "onTouchEvent --> " + TouchUtil.getTouchAction(event.getAction())
                        + " x=" + event.getX() + " y=" + event.getY() + " mode=" + mode);

                if(bMove && Math.abs(currentY - downPoint.y) > THRESHOLD_BACK) {
                    ScrollBackView.this.setVisibility(GONE);
                } else if(bMove) {
                    ScrollBackView.this.setScrollY(0);
                } else {
                    ScrollBackView.this.endShowAnimation(endPositionY);
                }
                break;
        }

        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.i("cjf---", "onInterceptTouchEvent --> " + TouchUtil.getTouchAction(event.getAction())
                + " x=" + event.getX() + " y=" + event.getY()+ " mode=" + mode);
//
//        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
//            super.onInterceptTouchEvent(event);
//        }

        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
            onTouchEvent(event);
            mode = MODE_NONE;
            bMove = false;
        }


        float[] difference = handleMotionEvent(event);
        if(mode == MODE_NONE && difference != null && Math.abs(difference[0]) > THRESHOLD_DIRECTION
                && Math.abs(difference[0]) > Math.abs(difference[1])) {
            mode = MODE_DRAG_HORIZONTAL; // need to send event to super class.
        } else if(mode == MODE_NONE && difference != null && Math.abs(difference[1]) > THRESHOLD_DIRECTION
                && Math.abs(difference[0]) < Math.abs(difference[1])) {
            mode = MODE_DRAG_VERTICAL; //scrolling up/down
        }

        if(mode == MODE_NONE) {
            this.onTouchEvent(event);
        }

        if (mode != MODE_DRAG_VERTICAL) {
            return super.onInterceptTouchEvent(event);
        }

        return true;
    }

    public void startShowAnimation(int startPositionY) {
        endPositionY = startPositionY;
        this.setVisibility(View.VISIBLE);
        this.setScrollY(startPositionY);
        ValueAnimator valueAnimator = ValueAnimator.ofInt((int)startPositionY, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int offset = (Integer) animation.getAnimatedValue();
                Log.w("cjf---", "startShowAnimation --> offset = " + offset);
                ScrollBackView.this.setScrollY(offset);
            }
        });

        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    private void endShowAnimation(final int endPositionY) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt((int)this.getScrollY(), endPositionY);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int offset = (Integer) animation.getAnimatedValue();
                Log.w("cjf---", "endShowAnimation --> offset = " + offset);
                ScrollBackView.this.setScrollY(offset);
                if(endPositionY == offset) {
                    ScrollBackView.this.setVisibility(View.GONE);
                }
            }


        });

        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }


}
