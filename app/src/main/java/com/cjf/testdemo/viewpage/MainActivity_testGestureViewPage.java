package com.cjf.testdemo.viewpage;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjf.testdemo.R;

public class MainActivity_testGestureViewPage extends AppCompatActivity {
    public String LOG_TAG = "cjf------";

    ScrollBackView gestureViewPager;
    ViewPager viewPage;
    TextView txNumber;
    ImageViewPagerAdapter imageAdapter;
    ImageView posiotView;

    int positionY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_gestureviewpage);

        posiotView = (ImageView)findViewById(R.id.positionview);

        gestureViewPager = (ScrollBackView)findViewById(R.id.gestureview);
        viewPage = (ViewPager)findViewById(R.id.viewpage);
        txNumber = (TextView)findViewById(R.id.number);
        imageAdapter = new ImageViewPagerAdapter(this);
        viewPage.setAdapter(imageAdapter);

        gestureViewPager.setVisibility(View.INVISIBLE);
        posiotView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionY = posiotView.getTop();
                Log.w("cjf---", "onClick --> positionY = " + positionY);
                Log.w("cjf---", "onClick --> gestureViewPager.getHeight() = " + gestureViewPager.getHeight());

                gestureViewPager.startShowAnimation(positionY);
            }
        });

        viewPage.setCurrentItem(0);
        txNumber.setText("1/" + imageAdapter.getCount());
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                txNumber.setText(position+1 + "/" + imageAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
