package com.fengyulong.android_common.utils;

import com.fengyulong.android_common.net.HttpConfig;
import com.fengyulong.android_common.net.HttpParamsUtils;

/**
 * Created by fengyulong on 2016/7/25.
 */
public class CommonHelper {

    public static void setDebugMode(boolean debugMode) {
        CommonUtils.setDEBUG(debugMode);
    }

    public static void setHttpParamsUtils(HttpParamsUtils httpParamsUtils) {
        HttpConfig.getInstance().setHttpParamsUtils(httpParamsUtils);
    }
}
