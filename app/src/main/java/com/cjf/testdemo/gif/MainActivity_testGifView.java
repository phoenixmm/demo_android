package com.cjf.testdemo.gif;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.cjf.testdemo.R;
import com.cjf.testdemo.gif.GifView;

public class MainActivity_testGifView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //test2: git view
        GifView gif1 = new GifView(this);
        gif1.setLayoutParams(new RelativeLayout.LayoutParams(200, 200));
        gif1.setMovieResource(R.raw.test);

        ((RelativeLayout)this.findViewById(R.id.MainRelativeLayout)).addView(gif1);

//        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
