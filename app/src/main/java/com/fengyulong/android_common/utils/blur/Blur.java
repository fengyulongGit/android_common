package com.fengyulong.android_common.utils.blur;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by ranfl on 2016/5/16.
 */
public class Blur {

    public static Bitmap blur(Bitmap bkg, View view) {
        float scaleFactor = 8;//缩放比例
        float radius = 5;//模糊度

        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth() / scaleFactor),
                (int) (view.getMeasuredHeight() / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);

        return overlay;
    }

    public static Bitmap blur(Bitmap bkg) {
        float scaleFactor = 8;//缩放比例
        float radius = 10;//模糊度

        Bitmap overlay = Bitmap.createBitmap((int) (bkg.getWidth() / scaleFactor),
                (int) (bkg.getHeight() / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
//        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() / scaleFactor);
        canvas.translate(0, 0);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        //模糊图片
        overlay = FastBlur.doBlur(overlay, (int) radius, true);

        return overlay;
    }

    /**
     * 图片模糊化
     *
     * @param bkg
     * @param isAddCoverage 是否增加蒙版层
     * @return
     */
    public static Bitmap blur(Bitmap bkg, boolean isAddCoverage) {
        Bitmap overlay = blur(bkg);

        if (isAddCoverage) {
            //增加灰度蒙版
            return getCoverageBitmap(overlay);
        } else {
            return overlay;
        }
    }

    /**
     * 增加图层蒙版
     *
     * @return
     */
    private static Bitmap getCoverageBitmap(Bitmap bitmap) {
        try {

            float scaleFactor = 1.5F;//缩放比例

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            Bitmap newBitmap = Bitmap.createBitmap(bitmap);
            Canvas canvasNew = new Canvas(newBitmap);
            Paint paintNew = new Paint();

            //放大
            canvasNew.scale(scaleFactor, scaleFactor);

            canvasNew.drawBitmap(bitmap, -10, -10, paintNew);

            paintNew = new Paint();
            //获取颜色
            int color = getRgbColor(bitmap);
            paintNew.setColor(color);
            paintNew.setAlpha(100);
            canvasNew.drawRect(0, 0, width, height, paintNew);

            canvasNew.save(Canvas.ALL_SAVE_FLAG);
            // 存储新合成的图片
            canvasNew.restore();

            return newBitmap;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 获取图层中间的颜色值
     *
     * @param bitmap
     * @return
     */
    private static int getRgbColor(Bitmap bitmap) {
        if (bitmap != null) {

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            //获得Bitmap 图片中每一个点的color颜色值
            int color = bitmap.getPixel(width / 2, height / 2);

            int r = Color.red(color);
            int g = Color.green(color);
            int b = Color.blue(color);
            return Color.rgb(r, g, b);
        } else {
            return 0;
        }

    }
}


