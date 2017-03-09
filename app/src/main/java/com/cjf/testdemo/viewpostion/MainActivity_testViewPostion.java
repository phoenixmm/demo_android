package com.cjf.testdemo.viewpostion;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjf.testdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity_testViewPostion extends AppCompatActivity {
    public String LOG_TAG = "cjf---";
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.editText1)
    TextView editText1;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.MainRelativeLayout)
    RelativeLayout MainRelativeLayout;

    @TargetApi(21)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testviewpostion);
        ButterKnife.bind(this);

        //just like Build.VERSION_CODES.JELLY_BEAN (= 16)
        Log.d(LOG_TAG, " Build.VERSION.SDK_INT = " + Build.VERSION.SDK_INT);
        if (Build.VERSION_CODES.JELLY_BEAN >= Build.VERSION.SDK_INT) {

        }
        Log.d(LOG_TAG, " onCreate----");


////////////////////////////////////////////////////////

        //获取屏幕区域的宽高等尺寸获取
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        Log.d(LOG_TAG,"widthPixels=" + widthPixels+" heightPixels="+heightPixels);

        //应用程序App区域宽高等尺寸获取
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        Log.d(LOG_TAG,"getWindowVisibleDisplayFrame rect=" + rect);

        //获取状态栏高度
        rect= new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        Log.d(LOG_TAG,"statusBarHeight=" + statusBarHeight);


        //View布局区域宽高等尺寸获取
        rect = new Rect();
        getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(rect);
        Log.d(LOG_TAG,"ID_ANDROID_CONTENT rect=" + rect);


////////////////////////////////////////////////////////
        textView1.getLocalVisibleRect(rect);
        Log.d(LOG_TAG,"getLocalVisibleRect textView1 rect=" + rect);
        textView1.getGlobalVisibleRect(rect);
        Log.d(LOG_TAG,"getGlobalVisibleRect textView1 rect=" + rect);

        int[] p = new int[]{0,0};
        textView1.getLocationOnScreen(p);
        Log.d(LOG_TAG,"getLocationOnScreen textView1 p=" + p[0] + "*" + p[1]);
        textView1.getLocationInWindow(p);
        Log.d(LOG_TAG,"getLocationInWindow textView1 p=" + p[0] + "*" + p[1]);


////////////////////////////////////////////////////////
        MainRelativeLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(LOG_TAG,"=========================================================");
                Log.d(LOG_TAG,"=========================MainRelativeLayout v.getId()=" + v.getId()+"=======");
                Log.d(LOG_TAG,"v.getX()=" + v.getX()+" v.getY()="+v.getY());
                Log.d(LOG_TAG,"v.getLeft()=" + v.getLeft()+" v.getTop()="+v.getTop()
                        +" v.getRight()="+v.getRight()+" v.getBottom()="+v.getBottom());
                Log.d(LOG_TAG,"v.getScrollX()=" + v.getScrollX()+" v.getScrollY()="+v.getScrollY());
                Log.d(LOG_TAG,"v.getTranslationX()=" + v.getTranslationX()+" v.getTranslationY()="+v.getTranslationY());

                if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
                    Log.d(LOG_TAG," v.getTranslationZ()="+v.getTranslationZ());
                }
                Log.d(LOG_TAG,"v.getWidth()=" + v.getWidth()+" v.getHeight()="+v.getHeight());
                Log.d(LOG_TAG,"v.getMeasuredWidth()=" + v.getMeasuredWidth()+" v.getMeasuredHeight()="+v.getMeasuredHeight());
                Log.d(LOG_TAG,"v.getPaddingLeft()=" + v.getPaddingLeft()+" v.getPaddingTop()="+v.getPaddingTop()
                        + " v.getPaddingRight()=" + v.getPaddingRight()+" v.getPaddingBottom()="+v.getPaddingBottom()
                        +" v.getPaddingStart()=" + v.getPaddingStart()+" v.getPaddingEnd()="+v.getPaddingEnd());


                Log.d(LOG_TAG,"=========================");
                Log.d(LOG_TAG,"v.getRootView()" + v.getRootView());
                Log.d(LOG_TAG,"v.getParent()" + v.getParent());


                Log.d(LOG_TAG,"=========================");
                Log.d(LOG_TAG," event.getX()="+event.getX() + "event.getY()=" + event.getY());
                Log.d(LOG_TAG," event.getRawX()="+event.getRawX() + "event.getRawY()=" + event.getRawY());


                return true;
            }
        });

        textView1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(LOG_TAG,"=========================================================");
                Log.d(LOG_TAG,"=========================textView1 v.getId()=" + v.getId()+"=======");
                Log.d(LOG_TAG,"v.getX()=" + v.getX()+" v.getY()="+v.getY());
                Log.d(LOG_TAG,"v.getLeft()=" + v.getLeft()+" v.getTop()="+v.getTop()
                        +" v.getRight()="+v.getRight()+" v.getBottom()="+v.getBottom());
                Log.d(LOG_TAG,"v.getScrollX()=" + v.getScrollX()+" v.getScrollY()="+v.getScrollY());
                Log.d(LOG_TAG,"v.getTranslationX()=" + v.getTranslationX()+" v.getTranslationY()="+v.getTranslationY());

                if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
                    Log.d(LOG_TAG," v.getTranslationZ()="+v.getTranslationZ());
                }
                Log.d(LOG_TAG,"v.getWidth()=" + v.getWidth()+" v.getHeight()="+v.getHeight());
                Log.d(LOG_TAG,"v.getMeasuredWidth()=" + v.getMeasuredWidth()+" v.getMeasuredHeight()="+v.getMeasuredHeight());
                Log.d(LOG_TAG,"v.getPaddingLeft()=" + v.getPaddingLeft()+" v.getPaddingTop()="+v.getPaddingTop()
                        + " v.getPaddingRight()=" + v.getPaddingRight()+" v.getPaddingBottom()="+v.getPaddingBottom()
                        +" v.getPaddingStart()=" + v.getPaddingStart()+" v.getPaddingEnd()="+v.getPaddingEnd());


                Log.d(LOG_TAG,"=========================");
                Log.d(LOG_TAG,"v.getRootView()" + v.getRootView());
                Log.d(LOG_TAG,"v.getParent()" + v.getParent());


                Log.d(LOG_TAG,"=========================");
                Log.d(LOG_TAG," event.getX()="+event.getX() + "event.getY()=" + event.getY());
                Log.d(LOG_TAG," event.getRawX()="+event.getRawX() + "event.getRawY()=" + event.getRawY());

                return true;
            }
        });

        editText1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(LOG_TAG,"=========================================================");
                Log.d(LOG_TAG,"=========================editText1 v.getId()=" + v.getId()+"=======");
                Log.d(LOG_TAG,"v.getX()=" + v.getX()+" v.getY()="+v.getY());
                Log.d(LOG_TAG,"v.getLeft()=" + v.getLeft()+" v.getTop()="+v.getTop()
                        +" v.getRight()="+v.getRight()+" v.getBottom()="+v.getBottom());
                Log.d(LOG_TAG,"v.getScrollX()=" + v.getScrollX()+" v.getScrollY()="+v.getScrollY());
                Log.d(LOG_TAG,"v.getTranslationX()=" + v.getTranslationX()+" v.getTranslationY()="+v.getTranslationY());

                if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
                    Log.d(LOG_TAG," v.getTranslationZ()="+v.getTranslationZ());
                }
                Log.d(LOG_TAG,"v.getWidth()=" + v.getWidth()+" v.getHeight()="+v.getHeight());
                Log.d(LOG_TAG,"v.getMeasuredWidth()=" + v.getMeasuredWidth()+" v.getMeasuredHeight()="+v.getMeasuredHeight());
                Log.d(LOG_TAG,"v.getPaddingLeft()=" + v.getPaddingLeft()+" v.getPaddingTop()="+v.getPaddingTop()
                        + " v.getPaddingRight()=" + v.getPaddingRight()+" v.getPaddingBottom()="+v.getPaddingBottom()
                        +" v.getPaddingStart()=" + v.getPaddingStart()+" v.getPaddingEnd()="+v.getPaddingEnd());


                Log.d(LOG_TAG,"=========================");
                Log.d(LOG_TAG,"v.getRootView()" + v.getRootView());
                Log.d(LOG_TAG,"v.getParent()" + v.getParent());


                Log.d(LOG_TAG,"=========================");
                Log.d(LOG_TAG," event.getX()="+event.getX() + "event.getY()=" + event.getY());
                Log.d(LOG_TAG," event.getRawX()="+event.getRawX() + "event.getRawY()=" + event.getRawY());

                return true;
            }
        });

        btn1.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(LOG_TAG,"=========================================================");
                Log.d(LOG_TAG,"=========================btn1 v.getId()=" + v.getId()+"=======");
                Log.d(LOG_TAG,"v.getX()=" + v.getX()+" v.getY()="+v.getY());
                Log.d(LOG_TAG,"v.getLeft()=" + v.getLeft()+" v.getTop()="+v.getTop()
                        +" v.getRight()="+v.getRight()+" v.getBottom()="+v.getBottom());
                Log.d(LOG_TAG,"v.getScrollX()=" + v.getScrollX()+" v.getScrollY()="+v.getScrollY());
                Log.d(LOG_TAG,"v.getTranslationX()=" + v.getTranslationX()+" v.getTranslationY()="+v.getTranslationY());

                if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
                    Log.d(LOG_TAG," v.getTranslationZ()="+v.getTranslationZ());
                }
                Log.d(LOG_TAG,"v.getWidth()=" + v.getWidth()+" v.getHeight()="+v.getHeight());
                Log.d(LOG_TAG,"v.getMeasuredWidth()=" + v.getMeasuredWidth()+" v.getMeasuredHeight()="+v.getMeasuredHeight());
                Log.d(LOG_TAG,"v.getPaddingLeft()=" + v.getPaddingLeft()+" v.getPaddingTop()="+v.getPaddingTop()
                        + " v.getPaddingRight()=" + v.getPaddingRight()+" v.getPaddingBottom()="+v.getPaddingBottom()
                        +" v.getPaddingStart()=" + v.getPaddingStart()+" v.getPaddingEnd()="+v.getPaddingEnd());


                Log.d(LOG_TAG,"=========================");
                Log.d(LOG_TAG,"v.getRootView()" + v.getRootView());
                Log.d(LOG_TAG,"v.getParent()" + v.getParent());


                Log.d(LOG_TAG,"=========================");
                Log.d(LOG_TAG," event.getX()="+event.getX() + "event.getY()=" + event.getY());
                Log.d(LOG_TAG," event.getRawX()="+event.getRawX() + "event.getRawY()=" + event.getRawY());

                return true;
            }

        });


        MainRelativeLayout.post(new Runnable() {
            @Override
            public void run() {
                MainRelativeLayout.scrollBy(15, 5);
                textView1.setTranslationX(-15);
                textView1.setTranslationY(-5);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.d(LOG_TAG,"onWindowFocusChanged-----");

////////////////////////////////////////////////////////

        //获取屏幕区域的宽高等尺寸获取
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        Log.d(LOG_TAG,"widthPixels=" + widthPixels+" heightPixels="+heightPixels);

        //应用程序App区域宽高等尺寸获取
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        Log.d(LOG_TAG,"getWindowVisibleDisplayFrame rect=" + rect);

        //获取状态栏高度
        rect= new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        Log.d(LOG_TAG,"statusBarHeight=" + statusBarHeight);


        //View布局区域宽高等尺寸获取  // 已自己的左上角为（0，0）, 高为 heightPixels - top
        rect = new Rect();
        getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(rect);
        Log.d(LOG_TAG,"ID_ANDROID_CONTENT rect=" + rect);

////////////////////////////////////////////////////////
        MainRelativeLayout.getLocalVisibleRect(rect);
        Log.d(LOG_TAG,"getLocalVisibleRect MainRelativeLayout rect=" + rect);
        MainRelativeLayout.getGlobalVisibleRect(rect);
        Log.d(LOG_TAG,"getGlobalVisibleRect MainRelativeLayout rect=" + rect);

        int[] p = new int[]{0,0};
        MainRelativeLayout.getLocationOnScreen(p);
        Log.d(LOG_TAG,"getLocationOnScreen MainRelativeLayout p=" + p[0] + "*" + p[1]);
        MainRelativeLayout.getLocationInWindow(p);
        Log.d(LOG_TAG,"getLocationInWindow MainRelativeLayout p=" + p[0] + "*" + p[1]);

        textView1.getLocalVisibleRect(rect);
        Log.d(LOG_TAG,"getLocalVisibleRect textView1 rect=" + rect);
        textView1.getGlobalVisibleRect(rect);
        Log.d(LOG_TAG,"getGlobalVisibleRect textView1 rect=" + rect);

        p = new int[]{0,0};
        textView1.getLocationOnScreen(p);
        Log.d(LOG_TAG,"getLocationOnScreen textView1 p=" + p[0] + "*" + p[1]);
        textView1.getLocationInWindow(p);
        Log.d(LOG_TAG,"getLocationInWindow textView1 p=" + p[0] + "*" + p[1]);


        super.onWindowFocusChanged(hasFocus);
    }
}