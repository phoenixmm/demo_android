package com.cjf.testdemo.ImageBrowser;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import java.util.LinkedList;

/**
 * Created by sohu-mac on 16/10/25.
 */
public class ImageBrowserViewAdapter extends PagerAdapter {
    private Context context;
    private LinkedList<ImageBrowserTouchImage> imageViewArray = new LinkedList<> ();
    private DetailEntityBean articleDetail = null;
    public ImageBrowserTouchImage mCurrentView = null;


    public ImageBrowserViewAdapter(Context context, DetailEntityBean articleDetail) {
        this.context = context;
        this.articleDetail = articleDetail;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentView = (ImageBrowserTouchImage) object;
    }

    @Override
    public int getCount() {
        return (articleDetail == null || articleDetail.getImages() == null)? 0 : articleDetail.getImages().size();
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
        ImageBrowserTouchImage image = null;
        DetailEntityBean.ImagesBean imgBean = articleDetail.getImages().get(position);

        if(imageViewArray != null && imageViewArray.size() > 0) {
            Log.d("cjf---", "instantiateItem image already in array position=" + position);
            image =  imageViewArray.getFirst();
            imageViewArray.removeFirst();
        } else {
            Log.d("cjf---", "instantiateItem image new position=" + position);
            image = new ImageBrowserTouchImage(context);
            image.setImgSize(imgBean.getWidth(), imgBean.getHeight());
            ViewPager.LayoutParams lp = new ViewPager.LayoutParams();
            lp.width = ViewPager.LayoutParams.MATCH_PARENT;
            lp.height = ViewPager.LayoutParams.MATCH_PARENT;
            image.setLayoutParams(lp);
            image.setBackgroundColor(Color.GREEN);
        }

//        ImageLoaderUtil.loadImage(context, imgBean.getUrl(), image);
        GlideImageLoaderUtil.getInstance().loadImageView(context, imgBean.getUrl(), image);
//        showImageOnCustomView(imgBean.getUrl(), image);
//        image.setImageResource(R.drawable.fi);


        container.addView(image);
        return image;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        if ( object != null) {
            imageViewArray.addLast((ImageBrowserTouchImage )object);
        }
    }


    public void showImageOnCustomView( String url, ImageBrowserTouchImage view) {
        ViewTarget viewTarget = new ViewTarget<View, GlideDrawable>(view) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                ((ImageBrowserTouchImage)view).setImageDrawable(resource.getCurrent());
            }
        };
        Glide.with(context).load(url).into(viewTarget);

    }
}
