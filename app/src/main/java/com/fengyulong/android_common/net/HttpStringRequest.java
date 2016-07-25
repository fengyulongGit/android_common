package com.fengyulong.android_common.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Description: 网络请求方法参数Volley方法
 *
 * @author fengyulong
 * @code
 */
public class HttpStringRequest {
    private static final int MAX_RETRIES = 0;
    private static final int BACKOFF_MULT = 0;
    private static HttpStringRequest httpStringRequest;
    private static RequestQueue requestQueue;

    private HttpStringRequest() {
    }

    public static HttpStringRequest getinstance(Context context) {
        if (httpStringRequest == null) {
            synchronized (HttpStringRequest.class) {
                if (httpStringRequest == null) {
                    httpStringRequest = new HttpStringRequest();
                    requestQueue = Volley.newRequestQueue(context);
//					requestQueue = Volley.newRequestQueue(context, R.raw.yqs, Constants.certificatePwd);
                }
            }
        }
        return httpStringRequest;
    }

    /**
     * @return 获取请求队列
     */
    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    /**
     * @param actionHandle  网络请求回调方法
     * @param method        post 或者 get
     * @param url           接口url
     * @param param         map 传递的参数
     * @param headerMap     map 头参数
     * @param listener      请求相应回调
     * @param errorListener 请求错误回调
     */
    public StringRequest request(HttpActionHandle actionHandle, final int method, String url, final Map<String, String> param, final Map<String, String> headerMap, int timeout, Listener<String> listener, ErrorListener errorListener) {
        final String back_url = url;
        try {
            if (method == Method.GET && param != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("?");
                Set<Map.Entry<String, String>> set = param.entrySet();
                for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext(); ) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
                    sb.append(entry.getKey() + "");
                    sb.append("=");
                    sb.append(URLEncoder.encode(entry.getValue() + "", "UTF-8"));
                    sb.append("&");
                }
                if (sb.length() > 0) {
                    url += sb.substring(0, sb.length() - 1);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringRequest stringRequest = new StringRequest(method, url, listener, errorListener) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return param == null ? super.getParams() : param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = null;
                if (headerMap != null) {
                    header = new HashMap<String, String>();
                    Set<String> keys = headerMap.keySet();

                    StringBuffer sb = new StringBuffer();
                    for (String key : keys) {
                        sb.append(key);
                        sb.append("=");
                        sb.append(headerMap.get(key));
                        sb.append(";");
                    }
                    String cookies = sb.toString();
                    cookies = cookies.substring(0, cookies.length() - 1);
                    header.put("Cookie", cookies);
                }
                return header == null ? super.getHeaders() : header;
            }

        };
        stringRequest.setTag(actionHandle.hashCode() + back_url);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, MAX_RETRIES, BACKOFF_MULT));
        requestQueue.add(stringRequest);
        return stringRequest;
    }
}
