package com.cjf.testdemo;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cjf.testdemo.ImageBrowser.DetailEntityBean;
import com.cjf.testdemo.ImageBrowser.ImageBrowserView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public String LOG_TAG = "cjf------";

    Button testBtn;
    ImageBrowserView imageBrowserView;
    int position = 0;
    int top = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //just like Build.VERSION_CODES.JELLY_BEAN (= 16)
        Log.d(LOG_TAG, " Build.VERSION.SDK_INT = " + Build.VERSION.SDK_INT);
        if (Build.VERSION_CODES.JELLY_BEAN >= Build.VERSION.SDK_INT) {

        }

        imageBrowserView = (ImageBrowserView)findViewById(R.id.scroll_back_view);
        testBtn = (Button)findViewById(R.id.testBtn1);


        final DetailEntityBean articleBean = new DetailEntityBean();
        List<DetailEntityBean.ImagesBean> imgList = new ArrayList<>();
        articleBean.setImages(imgList);

        DetailEntityBean.ImagesBean imgBean = new DetailEntityBean.ImagesBean(); //小图
        imgBean.setType("jpg");
        imgBean.setUrl("http://10.2.82.131:8080/cjf/flogo.jpg");
        imgBean.setWidth(180);
        imgBean.setHeight(100);
        imgList.add(imgBean);

        imgBean = new DetailEntityBean.ImagesBean(); //宽图
        imgBean.setType("jpg");
        imgBean.setUrl("http://10.2.82.131:8080/cjf/article_long_h.png");
        imgBean.setWidth(1000);
        imgBean.setHeight(70);
        imgList.add(imgBean);

        imgBean = new DetailEntityBean.ImagesBean(); //长图
        imgBean.setType("jpg");
        imgBean.setUrl("http://pic64.nipic.com/file/20150416/7186944_105033450000_2.jpg");
        imgBean.setWidth(200);
        imgBean.setHeight(700);
        imgList.add(imgBean);

        imgBean = new DetailEntityBean.ImagesBean(); //小gif
        imgBean.setType("gif");
        imgBean.setUrl("http://10.2.82.131:8080/cjf/test.gif");
        imgBean.setWidth(65);
        imgBean.setHeight(63);
        imgList.add(imgBean);

        imgBean = new DetailEntityBean.ImagesBean(); //正常gif
        imgBean.setType("gif");
        imgBean.setUrl("http://10.2.82.131:8080/cjf/test1.gif");
        imgBean.setWidth(400);
        imgBean.setHeight(300);
        imgList.add(imgBean);

        imgBean = new DetailEntityBean.ImagesBean();
        imgBean.setType("jpeg");
        imgBean.setUrl("http://p.cdn.sohu.com/bc4e413e/0190800433e97003bb1ec7c1737729a9.jpeg");
        imgBean.setWidth(640);
        imgBean.setHeight(436);
        imgList.add(imgBean);
//        imgBean = new DetailEntityBean.ImagesBean();
//        imgBean.setType("jpeg");
//        imgBean.setUrl("http://p.cdn.sohu.com/bc4e413e/1f2fa44c5fb4d39fe3e6b3a933e83188.jpeg");
//        imgBean.setWidth(640);
//        imgBean.setHeight(491);
//        imgList.add(imgBean);
//        imgBean = new DetailEntityBean.ImagesBean();
//        imgBean.setType("jpeg");
//        imgBean.setUrl("http://p.cdn.sohu.com/d750efec/53bc09dd66b082e174abdcda35b6dcc4.jpeg");
//        imgBean.setWidth(640);
//        imgBean.setHeight(464);
//        imgList.add(imgBean);
//        imgBean = new DetailEntityBean.ImagesBean();
//        imgBean.setType("jpeg");
//        imgBean.setUrl("http://p.cdn.sohu.com/49dc1158/74c507ac4d50d70aec50338c8c0f7ddc.jpeg");
//        imgBean.setWidth(640);
//        imgBean.setHeight(500);
//        imgList.add(imgBean);
//        imgBean = new DetailEntityBean.ImagesBean();
//        imgBean.setType("jpeg");
//        imgBean.setUrl("http://p.cdn.sohu.com/49dc1158/534e2d6ca9f62d950b48b453b4bc2975.jpeg");
//        imgBean.setWidth(640);
//        imgBean.setHeight(500);
//        imgList.add(imgBean);
//        imgBean = new DetailEntityBean.ImagesBean();
//        imgBean.setType("jpeg");
//        imgBean.setUrl("http://p.cdn.sohu.com/bc4e413e/6ba4d5bc3ba82b736e6d3847478c4b16.jpeg");
//        imgBean.setWidth(400);
//        imgBean.setHeight(300);
//        imgList.add(imgBean);
//        imgBean = new DetailEntityBean.ImagesBean();
//        imgBean.setType("jpeg");
//        imgBean.setUrl("http://p.cdn.sohu.com/f2942a04/41947c6036d829be160ae7a0d34f84d3.jpeg");
//        imgBean.setWidth(640);
//        imgBean.setHeight(479);
//        imgList.add(imgBean);



        testBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imageBrowserView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageBrowserView.init(MainActivity.this, position%6, top, articleBean);
                        imageBrowserView.startShow();
                        position += 1;
                        top += 50;
                    }
                });
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
