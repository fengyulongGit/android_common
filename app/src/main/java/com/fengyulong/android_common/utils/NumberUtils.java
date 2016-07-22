package com.fengyulong.android_common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 与数字相关的工具类
 *
 * @author ranfl
 */
public class NumberUtils {

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param f     浮点型数据
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static float round(float f, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }

        BigDecimal b = new BigDecimal(f);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 将货币转换为0.00格式的字符串，均保留2位小数
     *
     * @param f传入的数字
     * @return 字符串类型的货币
     */
    public static String round2Dot(float f) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(f);//format 返回的是字符串
    }

    public static String double2Dot(double d) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(d);
    }

    /**
     * 两个double类型相加
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 两个double类型相乘
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double multiply(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.multiply(b2).doubleValue();
    }

}
