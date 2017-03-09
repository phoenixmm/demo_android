package com.cjf.testdemo.ImageBrowser;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by srain on 16/10/9.
 */

public class GlideImageLoaderModule implements GlideModule {



    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.4 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.4 * defaultBitmapPoolSize);

        builder.setMemoryCache( new LruResourceCache( customMemoryCacheSize ));
        builder.setBitmapPool( new LruBitmapPool( customBitmapPoolSize ));

        //设置本地缓存
        // set size & external vs. internal
        int cacheSize50MegaBytes = 1024*1024*150;

        //应用内路径缓存
        builder.setDiskCache(
                new InternalCacheDiskCacheFactory(context,cacheSize50MegaBytes)
        );
        //应用外路径缓存
        builder.setDiskCache(
                new ExternalCacheDiskCacheFactory(context, cacheSize50MegaBytes)
        );


    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
