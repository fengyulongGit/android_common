package com.fengyulong.android_common.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RequestQueue.RequestFilter;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.fengyulong.android_common.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestProvider {
    private HttpActionHandle actionHandle;
    private HttpStringRequest httpStringRequest;
    private Context context;

    public HttpRequestProvider(Context context, HttpActionHandle actionHandle) {
        this.actionHandle = actionHandle;
        this.httpStringRequest = HttpStringRequest.getinstance(context);
        this.context = context;

    }

    public void cancleRequest(String tag) {
        RequestQueue requestQueue = httpStringRequest.getRequestQueue();
        requestQueue.cancelAll(actionHandle.hashCode() + tag);
    }

    public void cancleAllRequest() {
        RequestQueue requestQueue = httpStringRequest.getRequestQueue();
        requestQueue.cancelAll(new RequestFilter() {
            @Override
            public boolean apply(Request<?> arg0) {
                String tagPre = actionHandle.hashCode() + "";
                String tag = (String) arg0.getTag();
                if (tag != null && tag.startsWith(tagPre)) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public void request(final String actionName, int method, Map<String, String> map, Map<String, String> headermap, String url,
                        int TIMEOUT_MS, final DataParse dataParse) {

        if (map == null) {
            map = new HashMap<String, String>();
        }

        if (HttpConfig.getInstance().getHttpParamsUtils() != null) {
            Map<String, String> params = HttpConfig.getInstance().getHttpParamsUtils().addPublicParams(map);
            if (params != null) {
                map = params;
            }

            params = HttpConfig.getInstance().getHttpParamsUtils().securityRequestParams(map);
            if (params != null) {
                map = params;
            }
        }


        actionHandle.handleActionStart(actionName, null);

        httpStringRequest.request(actionHandle, method, url, map, headermap, TIMEOUT_MS, new Listener<String>() {

            @Override
            public void onResponse(String arg0) {
                LogUtils.i("onResponse------" + actionName + "------" + arg0);
                Response response = null;
                try {
                    response = dataParse.onParse(arg0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (response != null) {
                    if (response.isSuccess()) {
                        actionHandle.handleActionSuccess(actionName, response);
                    } else {
                        actionHandle.handleActionError(actionName, response);
                        ReqCodeErrorHelper.handleReqCode(context, response);
                    }
                } else {
                    response = new Response();
                    response.errorInfo = "攻城狮正在紧急维护中，请稍后再试";
                    actionHandle.handleActionError(actionName, response);
                }
                actionHandle.handleActionFinish(actionName, response);
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                LogUtils.i("onResponse------" + actionName + "------" + VolleyErrorHelper.getMessage(arg0));

                Response response = new Response();
                response.errorInfo = VolleyErrorHelper.getMessage(arg0);
                actionHandle.handleActionError(actionName, response);
                actionHandle.handleActionFinish(actionName, response);
            }
        });
    }

    public interface DataParse {
        Response onParse(String result) throws com.google.gson.JsonParseException, com.google.gson.JsonSyntaxException,
                com.google.gson.JsonIOException;
    }

}
