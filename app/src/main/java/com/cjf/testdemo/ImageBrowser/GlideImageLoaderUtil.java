package com.cjf.testdemo.ImageBrowser;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.cjf.testdemo.R;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;


import static com.bumptech.glide.Glide.with;

/**
 * 支持gif webp 图片加载
 * Created by srain on 16/10/9.
 */

public class GlideImageLoaderUtil {

    private  static  GlideImageLoaderUtil mGlideImageLoaderUtil;
    //管理集合
    private Set<RequestManager> mRequestManagerList;
    private GlideImageLoaderUtil(){
        mRequestManagerList = new HashSet<RequestManager>();
    }

    public static synchronized GlideImageLoaderUtil  getInstance(){
        if(null == mGlideImageLoaderUtil){
            mGlideImageLoaderUtil = new GlideImageLoaderUtil();
        }
        return mGlideImageLoaderUtil;
    }


    /**
     *
     * 添加管理类
     */
    private  RequestManager getGlide(Context context){
        RequestManager mRequestManager = with(context);
        mRequestManagerList.add(mRequestManager);
        return mRequestManager;
    }


    /**
     *
     * 加载图片
     * @param url
     * @param imageView
     */
    public  void loadImageView(Context context, String url, ImageView imageView){

//        Context myContext = judgeContext(context);

//        getGlide(context)
        with(context)
             .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .override(180,100)

             .placeholder(R.drawable.image_default)
//                .override(141,107)

//                .override(282,214)
                .dontAnimate()
                .dontTransform()
//                .placeholder(R.drawable.placeholder)
             .into(imageView);

    }

    /**
     * 可以设置初始化占位图片
     * @param url
     * @param imageView
     * @param r_id
     */
    public   void loadImageView(Context context,String url, ImageView imageView, int r_id){
        Context myContext = judgeContext(context);

        getGlide(context)
                .load(url)
                .placeholder(r_id)
                .into(imageView);
    }

    /**
     * 加载本地图片
     * @param context
     * @param imageView
     * @param r_id 本地图片
     */
    public   void loadLocalImageView(Context context, ImageView imageView, int r_id){
        Context myContext = judgeContext(context);

        getGlide(context)
                .load(r_id)
                .into(imageView);
    }





    /**
     * Glide 同步的获取 Bitmap
     * @param url
     * @return
     */
    public  Bitmap loadImageSync (Context context,String  url){
        Context myContext = judgeContext(context);
        Bitmap myBitmap = null;
        try {
             myBitmap =   getGlide(context).load(url).asBitmap()
                     .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                     .into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                     .get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return myBitmap;
    }

    public  byte[] loadGifSync (Context context,String  url){
        Context myContext = judgeContext(context);
        byte[] dataGif = null;
        try {
            dataGif =   getGlide(context).load(url).asGif()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get().getDecoder().getData();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return dataGif;
    }



//    /**
//     * 增加圆形加载
//     */
//
//    public    void  loadCircleImageView( Context context , final ImageView imageView, String url){
//        final Context myContext = judgeContext(context);
//
//       getGlide(myContext)
//                .load(url)
//                .asBitmap()
//                .placeholder(R.drawable.user_head_default)
//                .centerCrop()
//                .into(new BitmapImageViewTarget(imageView) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                Drawable drawable = BitmapUtils.getCircularBitmap(myContext,resource);
//                imageView.setImageDrawable(drawable);
//            }
//        });
//
//    }

//    /**
//     * 增加圆形加载
//     */
//
//    public    void  loadCircleImageView( Context context , final ImageView imageView, int url){
//        final Context myContext = judgeContext(context);
//
//        getGlide(myContext)
//                .load(url)
//                .asBitmap()
//                .centerCrop()
//                .into(new BitmapImageViewTarget(imageView) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        Drawable drawable = BitmapUtils.getCircularBitmap(myContext,resource);
//                        imageView.setImageDrawable(drawable);
//                    }
//                });
//
//    }

//
//
//
//    /**
//     * 图形加边
//     * @param bitmap
//     * @param mColor
//     * @return
//     */
//    public  static Bitmap getRoundedCornerBitmap(Bitmap bitmap,int mColor) {
//        Bitmap output
//                = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(output);
//
//        final int color = mColor;
//
//        final Paint paint = new Paint();
//
//
//        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//        final RectF rectF = new RectF(rect);
//        final float roundPx = bitmap.getWidth();
//
//        paint.setAntiAlias(true);
//        canvas.drawARGB(0, 0, 0, 0);
//        paint.setColor(color);
//
//        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
//
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
//        paint.setStrokeWidth(90);
//
//        canvas.drawBitmap(bitmap, rect, rect, paint);
//
//        return output;
//    }
//
    /**
     * 判断 图片的上下文
     * @param context
     * @return
     */
    private static Context judgeContext(Context context){
        if (null == context){
            return  null;//MApplication.mContext;
        }
        return context;
    }


//    /**
//     * 清除图片磁盘缓存
//     */
//    public  static void clearImageDiskCache() {
//        try {
//            if (Looper.myLooper() == Looper.getMainLooper()) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Glide.get(MApplication.mContext).clearDiskCache();
//                    }
//                }).start();
//            } else {
//                Glide.get(MApplication.mContext).clearDiskCache();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 清除图片内存缓存
//     */
//    public  static void clearImageMemoryCache() {
//        try {
//            if (Looper.myLooper() == Looper.getMainLooper()) {
//                //只能在主线程执行
//                Glide.get(MApplication.mContext).clearMemory();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 清除图片所有缓存
//     */
//    public static void  clearImageAllCache() {
////        clearImageDiskCache();
////        clearImageMemoryCache();
//        //清除本地sd卡路径文件
//        deleteFolderFile(MApplication.mContext.getExternalCacheDir().getPath(), false);
//        //删除本地文件
//        deleteFolderFile(Glide.getPhotoCacheDir(MApplication.mContext).getPath(),false);
//    }
//
//    /**
//     * 获取Glide造成的缓存大小
//     * 本地缓存 外部缓存  没有计算内存缓存
//     *
//     * @return CacheSize
//     */
//    public static String getCacheSize() {
//
//        try {
//
//            long Memorylength = getFolderSize(new File( Glide.getPhotoCacheDir(MApplication.mContext).getPath()));
//            long ExternalCachelength = getFolderSize(new File( MApplication.mContext.getExternalCacheDir().getPath()));
//
//            return getFormatSize(Memorylength+ExternalCachelength);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "0KB";
//    }
//
//    /**
//     * 获取指定文件夹内所有文件大小的和
//     *
//     * @param file file
//     * @return size
//     * @throws Exception
//     */
//    public static long getFolderSize(File file) throws Exception {
//        long size = 0;
//
//        try {
//            File[] fileList = file.listFiles();
//            for (File aFileList : fileList) {
//                if (aFileList.isDirectory()) {
//
//                    size = size + getFolderSize(aFileList);
//                } else {
//
//                    size = size + aFileList.length();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return size;
//    }

//    /**
//     * 删除指定目录下的文件，这里用于缓存的删除
//     *
//     * @param filePath       filePath
//     * @param deleteThisPath deleteThisPath
//     */
//    public static  void deleteFolderFile(String filePath, boolean deleteThisPath) {
//        if (!TextUtils.isEmpty(filePath)) {
//            try {
//                File file = new File(filePath);
//                if (file.isDirectory()) {
//                    File files[] = file.listFiles();
//                    for (File file1 : files) {
//                        deleteFolderFile(file1.getAbsolutePath(), true);
//                    }
//                }
//                if (deleteThisPath) {
//                    if (!file.isDirectory()) {
//                        file.delete();
//                    } else {
//                        if (file.listFiles().length == 0) {
//                            file.delete();
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 格式化单位
//     *
//     * @param size size
//     * @return size
//     */
//    public static String getFormatSize(double size) {
//
//        double kiloByte = size / 1024;
//        if (kiloByte < 1) {
//            return size + "KB";
//        }
//
//        double megaByte = kiloByte / 1024;
//        if (megaByte < 1) {
//            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
//            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
//        }
//
//        double gigaByte = megaByte / 1024;
//        if (gigaByte < 1) {
//            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
//            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
//        }
//
//        double teraBytes = gigaByte / 1024;
//        if (teraBytes < 1) {
//            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
//            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
//        }
//        BigDecimal result4 = new BigDecimal(teraBytes);
//
//        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
//    }
//
//    /**
//     * 停止所有加载
//     */
//    public void pauseAll(){
//        if (null == mRequestManagerList||mRequestManagerList.size()<=0){
//            return;
//        }
//        for (RequestManager manager :mRequestManagerList){
//            manager.pauseRequests();
//        }
//    }
//
//    /**
//     *恢复所有
//     */
//    public void resumeAll(){
//        if (null == mRequestManagerList||mRequestManagerList.size()<=0){
//            return;
//        }
//        for (RequestManager manager :mRequestManagerList){
//            manager.resumeRequests();
//        }
//    }


}
