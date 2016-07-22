package com.fengyulong.android_common.net.http;

import org.apache.http.HttpResponse;


public interface HttpCallback {
    public void onReceiveData(HttpResponse rep, HttpException exp, int tag);
}
