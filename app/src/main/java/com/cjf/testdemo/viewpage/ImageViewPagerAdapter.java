package com.cjf.testdemo.viewpage;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cjf.testdemo.R;

/**
 * Created by sohu-mac on 16/10/25.
 */
public class ImageViewPagerAdapter extends PagerAdapter {
    Context context;
    ImageView image;
    private final int[] drawables = {R.drawable.fi, R.drawable.f, R.drawable.s, R.drawable.t, R.drawable.fo,
            R.drawable.fi, R.drawable.f, R.drawable.s, R.drawable.t};


    public ImageViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getCount() {
        return drawables.length;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        image = new ImageView(context);
        ViewPager.LayoutParams lp = new ViewPager.LayoutParams();
        lp.width = ViewPager.LayoutParams.MATCH_PARENT;
        lp.height = ViewPager.LayoutParams.MATCH_PARENT;
        image.setLayoutParams(lp);
        image.setBackgroundColor(Color.parseColor("#A0888888"));
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image.setImageResource(drawables[position]);
        container.addView(image);
        return image;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
