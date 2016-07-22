package com.fengyulong.android_common.code.qr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

/**
 * 生成二维码
 *
 * @author ranfl
 */
public class QrCode {

    private static final int QR_WIDTH = 400;
    private static final int QR_HEIGHT = 400;
    private static final String CHARSET = "UTF-8";

    /**
     * 生成二维码(无logo)
     *
     * @param string 传递进来的字符串
     * @return bitmap 图像(无logo)
     */
    public static Bitmap qrCode(String string) {

        MultiFormatWriter writer = new MultiFormatWriter();
        Hashtable<EncodeHintType, String> hst = new Hashtable<EncodeHintType, String>();
        hst.put(EncodeHintType.CHARACTER_SET, CHARSET);

        BitMatrix matrix = null;
        try {
            matrix = writer.encode(string, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hst);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }

            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }


    /**
     * 生成二维码(有logo)
     *
     * @param string  传递进来的字符串
     * @param imageId 传进来的图片id
     * @return bitmap 二维码图像（有logo）
     */
    public static Bitmap qrCodeByLogo(Context context, String string, int imageId) {

        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new QRCodeWriter().encode(string, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];

        Bitmap[] bitmaps = new Bitmap[2];
        bitmaps[1] = BitmapFactory.decodeResource(context.getResources(), imageId);// logo图标
        int imageW = bitmaps[1].getWidth();
        int imageH = bitmaps[1].getHeight();
        int startW = (width - imageW) / 2;
        int starH = height / 2 - imageH / 2;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if ((x <= startW || x >= starH + imageW) || (y <= starH || y >= +imageH)) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    }
                } else {
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        bitmaps[0] = bitmap;

        return combineBitmaps(bitmaps, startW, starH);

    }

    /**
     * 添加logo
     *
     * @param bitmaps 传入的logo
     * @param w       logo的宽
     * @param h       logo的高
     * @return bitmap
     */
    public static Bitmap combineBitmaps(Bitmap[] bitmaps, int w, int h) {

        Bitmap newBitmap = Bitmap.createBitmap(bitmaps[0].getWidth(), bitmaps[0].getHeight(), Config.ARGB_8888);
        Canvas cv = new Canvas(newBitmap);

        for (int i = 0; i < bitmaps.length; i++) {
            if (i == 0) {
                cv.drawBitmap(bitmaps[0], 0, 0, null);
            } else {
                cv.drawBitmap(bitmaps[i], w, h, null);
            }
            cv.save(Canvas.ALL_SAVE_FLAG);
            cv.restore();
        }
        return newBitmap;
    }
}
