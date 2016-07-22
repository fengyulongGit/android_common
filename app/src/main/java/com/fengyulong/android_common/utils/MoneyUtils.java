package com.fengyulong.android_common.utils;

/**
 * 处理与货币有关的工具类
 *
 * @author ranfl
 */
public class MoneyUtils {

    /**
     * 判断是否为货币类型
     *
     * @param str
     * @return
     */
    public static boolean isMoney(String str) {

        if (ValidateUtil.isEmpty(str)) {
            return false;
        }

        if (str.contains(".")) {//包含点，说明是double
            try {
                double d = Double.valueOf(str);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {//整形
            try {
                int i = Integer.valueOf(str);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    /**
     * 是否为正数人民币
     *
     * @return
     */
    public static boolean isPositiveMoney(String str) {
        if (ValidateUtil.isEmpty(str)) {
            return false;
        }

        if (str.contains(".")) {//包含点，说明是double
            try {
                double d = Double.valueOf(str);
                if (d > 0.0) {
                    return true;
                } else {
                    return false;
                }

            } catch (Exception e) {
                return false;
            }
        } else {//整形
            try {
                int i = Integer.valueOf(str);
                if (i > 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
    }

}
