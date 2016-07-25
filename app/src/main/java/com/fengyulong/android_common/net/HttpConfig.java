package com.fengyulong.android_common.net;

/**
 * Created by fengyulong on 2016/7/25.
 */
public class HttpConfig {
    private static HttpConfig instance;

    public final int TIMEOUT_MS_DEFAULT = 10 * 1000;
    private HttpParamsUtils httpParamsUtils;

    private HttpConfig() {
    }

    public static HttpConfig getInstance() {
        if (instance == null) {
            synchronized (HttpConfig.class) {
                if (instance == null) {
                    instance = new HttpConfig();
                }
            }
        }
        return instance;
    }

    public HttpParamsUtils getHttpParamsUtils() {
        return httpParamsUtils;
    }

    public void setHttpParamsUtils(HttpParamsUtils httpParamsUtils) {
        this.httpParamsUtils = httpParamsUtils;
    }
}
