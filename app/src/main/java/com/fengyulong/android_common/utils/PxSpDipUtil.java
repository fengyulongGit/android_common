package com.fengyulong.android_common.utils;

import android.util.DisplayMetrics;

public class PxSpDipUtil {


    /*	 dip: device independent pixels(设备独立像素). 不同设备有不同的显示效果,这个和设备硬件有关，一般我们为了支持WVGA、HVGA和QVGA 推荐使用这个，不依赖像素。
         dp: dip是一样的
         px: pixels(像素). 不同设备显示效果相同，一般我们HVGA代表320x480像素，这个用的比较多。
         pt: point，是一个标准的长度单位，1pt＝1/72英寸，用于印刷业，非常简单易用；
         sp: scaled pixels(放大像素). 主要用于字体显示best for textsize。
        in（英寸）：长度单位。
        mm（毫米）：长度单位。*/
    public final static int COMPLEX_UNIT_PX = 0;
    public final static int COMPLEX_UNIT_DIP = 1;
    public final static int COMPLEX_UNIT_SP = 2;
    public final static int COMPLEX_UNIT_PT = 3;
    public final static int COMPLEX_UNIT_IN = 4;
    public final static int COMPLEX_UNIT_MM = 5;

    public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case COMPLEX_UNIT_PX:
                return value;
            case COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }
}
