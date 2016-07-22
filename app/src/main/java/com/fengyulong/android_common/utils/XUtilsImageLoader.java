package com.fengyulong.android_common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;

public class XUtilsImageLoader {
    private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(
            android.R.color.transparent);
    private BitmapUtils bitmapUtils;
    private Context mContext;
    private int resId;
    private Handler handler;

    public XUtilsImageLoader(Context context, int resId) {
        this.mContext = context;
        this.resId = resId;

        handler = new Handler();

        try {
            bitmapUtils = new BitmapUtils(mContext);
            if (resId != 0) {
                bitmapUtils.configDefaultLoadingImage(resId);// 默认背景图片
                bitmapUtils.configDefaultLoadFailedImage(resId);// 加载失败图片
            }
        } catch (Exception e) {
        }
    }

    /**
     * @param imageView
     * @param bitmap
     * @author sunglasses
     * @category 图片加载效果
     */
    public void fadeInDisplay(ImageView imageView, Bitmap bitmap) {// 目前流行的渐变效果
        final TransitionDrawable transitionDrawable = new TransitionDrawable(
                new Drawable[]{TRANSPARENT_DRAWABLE, new BitmapDrawable(imageView.getResources(), bitmap)});
        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(500);
    }

    public void display(ImageView container, String url) {// 外部接口函数
        if (ValidateUtil.isEmpty(url)) {
            return;
        }
        try {
            bitmapUtils.display(container, url, new CustomBitmapLoadCallBack());
        } catch (Exception e) {
        }
    }

    public void displayViewGroup(ViewGroup container, String url) {// 外部接口函数
        if (ValidateUtil.isEmpty(url)) {
            return;
        }
        try {
            bitmapUtils.display(container, url, new CustomViewGroupLoadCallBack());
        } catch (Exception e) {
        }
    }

    public void clearMemAndDiskCache(String uri) {
        try {
            if (!TextUtils.isEmpty(uri)) {
                bitmapUtils.clearMemoryCache(uri);
                bitmapUtils.clearDiskCache(uri);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public BitmapUtils getBitmapUtils() {
        return bitmapUtils;
    }

    /**
     * @author sunglasses
     * @category 图片回调函数
     */
    public class CustomBitmapLoadCallBack extends DefaultBitmapLoadCallBack<ImageView> {

        @Override
        public void onLoading(ImageView container, String uri,
                              BitmapDisplayConfig config, long total, long current) {
        }

        @Override
        public void onLoadCompleted(final ImageView container, String uri, final Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    container.setImageBitmap(bitmap);
                }
            });

            super.onLoadCompleted(container, uri, bitmap, config, from);
        }

        @Override
        public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
            if (resId != 0) {
                container.setImageResource(resId);
            }
        }
    }

    /**
     * @author sunglasses
     * @category 图片回调函数
     */
    public class CustomViewGroupLoadCallBack extends DefaultBitmapLoadCallBack<ViewGroup> {

        @Override
        public void onLoading(ViewGroup container, String uri,
                              BitmapDisplayConfig config, long total, long current) {
        }
    }

}
