package com.fengyulong.android_common.net;

import java.util.Map;

public abstract class Request {

    public abstract String url();

    public abstract Map<String, String> params();

    public abstract Class<?> response();

    public int timeOutMS() {
        return HttpConfig.getInstance().TIMEOUT_MS_DEFAULT;
    }

    public Map<String, String> headParams() {
        return null;
    }
}
