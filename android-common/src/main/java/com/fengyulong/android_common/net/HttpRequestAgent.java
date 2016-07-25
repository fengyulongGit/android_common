package com.fengyulong.android_common.net;

import android.content.Context;

import com.android.volley.Request.Method;
import com.fengyulong.android_common.base.BaseApp;
import com.fengyulong.android_common.utils.GsonUtils;

public class HttpRequestAgent {

    private Context context;
    private HttpRequestProvider client;

    public HttpRequestAgent() {
        context = BaseApp.getInstance().getBaseContext();
    }

    public <T> void execute(final String key, final Request request,
                            final HttpActionHandle httpActionHandle) {
        if (client == null) {
            client = new HttpRequestProvider(context, httpActionHandle);
        }
        client.request(key, Method.POST, request.params(), request.headParams(),
                request.url(), request.timeOutMS(), new HttpRequestProvider.DataParse() {
                    @Override
                    public Response onParse(String result) {
                        Response obj = (Response) GsonUtils.fromJson(result, request.response());
                        return obj;
                    }
                });
    }

    public void cancleAllRequest() {
        if (client != null) {
            client.cancleAllRequest();
        }
    }
}
