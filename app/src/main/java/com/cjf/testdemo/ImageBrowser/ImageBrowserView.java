package com.cjf.testdemo.ImageBrowser;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjf.testdemo.R;


/**
 * Created by sohu-mac on 16/10/25.
 */
public class ImageBrowserView extends RelativeLayout {
    private static final int THRESHOLD_DIRECTION = 5;
    private static final int THRESHOLD_BACK = 200;
    private static final int THRESHOLD_MOVE = 30;

    private static final int END_BY_CANCEL = 0;
    private static final int END_BY_SCROLL = 1;
    private static final int END_BY_CLICK = 2;
    public static final int END_BY_BACKKEY = 3;

    private static final int MODE_NONE = 0;
    private static final int MODE_DRAG_HORIZONTAL = 1;
    private static final int MODE_DRAG_VERTICAL = 2;
    private int mode = MODE_NONE;

    private PointF downPoint;
    private float currentY;

    //-----------------------------------
    Context mContext;

    ImageBrowserViewPager viewPage;
    RelativeLayout bottomBar;
    TextView number;
    Button save;

    private ImageBrowserViewAdapter imageAdapter;
    private DetailEntityBean articleDetail = null;
    private int enterImgIndex = 0;
    private int enterImgTop = 0;
    private int animationStart = 0;
    private float animationScale = 0;



    public ImageBrowserView(Context context) {
        super(context);
    }

    public ImageBrowserView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(final Context context, final int enterImgIndex, final int enterImgTop, final DetailEntityBean articleDetail) {
        this.mContext = context;
        this.enterImgIndex = enterImgIndex;
        this.enterImgTop = enterImgTop;
        this.articleDetail = articleDetail;

        Log.d("cjf---", "ImageBrowserView enterImgIndex=" + enterImgIndex);
        Log.d("cjf---", "ImageBrowserView enterImgTop=" + enterImgTop);

        viewPage = (ImageBrowserViewPager)this.findViewById(R.id.viewpage);
        bottomBar = (RelativeLayout)this.findViewById(R.id.bottombar);
        number = (TextView)bottomBar.findViewById(R.id.number);
        save = (Button)bottomBar.findViewById(R.id.save);

        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                number.setText(position + 1 + "/" + imageAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        save.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BaseEvent event = new BaseEvent();
//                event.requestTag = EventTag.TAG_DETAIL_SAVEIMG_CLICK;
//                event.data = articleDetail.getImages().get(viewPage.getCurrentItem()).getUrl();
//                RxBus.getDefault().post(event);
//            }
//        });

        imageAdapter = new ImageBrowserViewAdapter(mContext, articleDetail);
        viewPage.setAdapter(imageAdapter);
        viewPage.setCurrentItem(enterImgIndex, false);
        number.setText(enterImgIndex+1 + "/" + imageAdapter.getCount());
        this.setVisibility(View.INVISIBLE);
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

    private boolean inChildrenInterceptRect(MotionEvent event) {
        int[] point = new int[2];
        save.getLocationOnScreen(point);
        if(event.getRawX() > point[0] && event.getRawX() < point[0] + save.getWidth()
                && event.getRawY() > point[1] && event.getRawY() < point[1] + save.getHeight()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.w("cjf---", "onTouchEvent --> " + event.getAction()
                + " x=" + event.getX() + " y=" + event.getY() + " mode=" + mode);

        if(mode == MODE_DRAG_HORIZONTAL) {
//            return true;
            return false;
        }

        float moveDistance = 0.0f;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE:
                currentY = event.getY(0);
                viewPage.setScrollY((int)(downPoint.y - currentY));

                moveDistance = Math.abs(currentY - downPoint.y);
                float alpha = 1.0f -  moveDistance * 2 / viewPage.getHeight();
                if(alpha < 0.0f) {
                    alpha = 0.0f;
                }
                Log.e("cjf---", "onTouchEvent alpha --> " + alpha);
                this.getBackground().setAlpha((int)(alpha * 255));
                bottomBar.setAlpha(alpha);
                this.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Log.e("cjf---", "onTouchEvent ACTION_UP --> " + event.getAction()
                        + " x=" + event.getX() + " y=" + event.getY() + " mode=" + mode);
                currentY = event.getY(0);
                moveDistance = Math.abs(currentY - downPoint.y);
                if( moveDistance > THRESHOLD_BACK) {//长距离移动
                    endShow(END_BY_SCROLL);
                } else if(moveDistance > THRESHOLD_MOVE) {//短距离移动
                    endShow(END_BY_CANCEL);
                } else {//点击
                    endShow(END_BY_CLICK);
                }
                break;
        }

//        return true;
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.i("cjf---", "onInterceptTouchEvent --> " + event.getAction()
                + " x=" + event.getX() + " y=" + event.getY()+ " mode=" + mode);

        boolean bInterceptTouchEvent = false;
        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
            if(inChildrenInterceptRect(event)) {
                mode = MODE_DRAG_HORIZONTAL;
            } else {
                mode = MODE_NONE;
            }
        }


        float[] difference = handleMotionEvent(event);
        if(mode == MODE_NONE && difference != null && Math.abs(difference[0]) > THRESHOLD_DIRECTION
                && Math.abs(difference[0]) > Math.abs(difference[1])) {
            mode = MODE_DRAG_HORIZONTAL; // fling page, need to send event to super class.
        } else if(mode == MODE_NONE && difference != null && Math.abs(difference[1]) > THRESHOLD_DIRECTION
                && Math.abs(difference[0]) < Math.abs(difference[1])) {
            mode = MODE_DRAG_VERTICAL; //scrolling up/down
            bInterceptTouchEvent = true;
        }

//        if(mode == MODE_NONE) {
//            this.onTouchEvent(event);
//        }

//        if (mode != MODE_DRAG_VERTICAL) {//not vertical scroll, dispatch event to children.
//            return super.onInterceptTouchEvent(event);
//        }

//        return bInterceptTouchEvent;
        return false;
    }

    public void startShow() {
        this.setVisibility(View.VISIBLE);

        DisplayMetrics displayMetrics = this.getContext().getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        Log.d("cjf---", "ImageBrowserView screenWidth=" + screenWidth);
        Log.d("cjf---", "ImageBrowserView screenHeight=" + screenHeight);

        DetailEntityBean.ImagesBean ImgBean = articleDetail.getImages().get(viewPage.getCurrentItem());
        int ImgWidth = ImgBean.getWidth();
        int ImgHeight = ImgBean.getHeight();
        Log.d("cjf---", "ImageBrowserView ImgWidth=" + ImgWidth);
        Log.d("cjf---", "ImageBrowserView ImgHeight=" + ImgHeight);

        ImgHeight = (int)((ImgHeight / (float)ImgWidth) * screenWidth);
        ImgWidth = screenWidth;
        Log.d("cjf---", "ImageBrowserView ImgWidth2=" + ImgWidth);
        Log.d("cjf---", "ImageBrowserView ImgHeight2=" + ImgHeight);

        int ImgTop = (int) (screenHeight - ImgHeight) / 2;
        animationStart = enterImgTop - ImgTop;
        Log.d("cjf---", "ImageBrowserView ImgTop=" + ImgTop);
        Log.d("cjf---", "ImageBrowserView moveStart=" + animationStart);

        int maxWidthStart =  screenWidth - dip2px(32);
        animationScale = ((float) maxWidthStart) / screenWidth;
        if(maxWidthStart > ImgWidth) {
            animationScale = ((float)ImgWidth) / screenWidth;
        }
        Log.d("cjf---", "ImageBrowserView scale=" + animationScale);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(500);

        //移动
        TranslateAnimation translateAnimation=new TranslateAnimation(0, 0, animationStart, 0);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        animationSet.addAnimation(translateAnimation);

        //缩放
        ScaleAnimation scaleAnimation =new ScaleAnimation(animationScale, 1.0f, animationScale, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f - animationScale/2);
        scaleAnimation.setInterpolator(new AccelerateInterpolator());
        animationSet.addAnimation(scaleAnimation);

//        viewPage.startAnimation(animationSet);
    }

    public void endShow(final int type) {
        if(type == END_BY_CANCEL) {
            viewPage.setScrollY(0);
            this.getBackground().setAlpha(255);
            bottomBar.setAlpha(1);
            return;
        } else if((type == END_BY_BACKKEY || type == END_BY_CLICK)
                && enterImgIndex == viewPage.getCurrentItem()){
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.setDuration(200);

            //移动
            TranslateAnimation translateAnimation=new TranslateAnimation(0, 0, 0, animationStart);
            translateAnimation.setInterpolator(new DecelerateInterpolator());
            animationSet.addAnimation(translateAnimation);

            //缩放
            ScaleAnimation scaleAnimation =new ScaleAnimation(1.0f, animationScale, 1.0f,animationScale,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setInterpolator(new AccelerateInterpolator());
            animationSet.addAnimation(scaleAnimation);

            viewPage.startAnimation(animationSet);

            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ImageBrowserView.this.setVisibility(View.GONE);
                    viewPage.setScrollY(0);
                    ImageBrowserView.this.getBackground().setAlpha(255);
                    bottomBar.setAlpha(1);
                }
            }, 210);

            return;
        }

        this.setVisibility(View.GONE);
        viewPage.setScrollY(0);
        this.getBackground().setAlpha(255);
        bottomBar.setAlpha(1);
    }


    public int dip2px( float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
