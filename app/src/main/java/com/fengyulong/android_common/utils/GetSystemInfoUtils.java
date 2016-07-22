package com.fengyulong.android_common.utils;

/**
 * 获取系统相关信息的工具类
 *
 * @author ranfl
 */
public class GetSystemInfoUtils {

    /**
     * 获取系统版本SDK号
     *
     * @return
     */
    public static int getAndroidSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
        }
        return version;
    }

}
