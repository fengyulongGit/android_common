package com.fyl.android_common.utils;

import com.fyl.android_common.net.HttpConfig;
import com.fyl.android_common.net.HttpParamsUtils;

/**
 * Created by fyl on 2016/7/25.
 */
public class CommonHelper {

    public static void setDebugMode(boolean debugMode) {
        CommonUtils.setDEBUG(debugMode);
    }

    public static void setHttpParamsUtils(HttpParamsUtils httpParamsUtils) {
        HttpConfig.getInstance().setHttpParamsUtils(httpParamsUtils);
    }
}
