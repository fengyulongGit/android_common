package com.fengyulong.android_common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * 大图加载相关的类
 *
 * @author Ranfl
 */
public class ImageLoadUtils {

    private Context context;

    public ImageLoadUtils(Context context) {
        super();
        this.context = context;
    }

    /**
     * 计算图片的缩放比例
     *
     * @param options
     * @param reqWidth  实际显示的宽度
     * @param reqHeight 实际显示的高度
     * @return 缩放比例
     */
    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    /**
     * 将资源图片转换为bitmap类型
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @retur
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static void setImage(Context context, View view, int resId, int reqWidth, int reqHeight) {
        if (resId != 0) {
            Bitmap bitmap = decodeSampledBitmapFromResource(context.getResources(), resId, reqWidth, reqHeight);

            Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
            view.setBackground(drawable);
        } else {

        }


    }
}
