package com.fengyulong.android_common.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class VolleyErrorHelper {

    public static String getMessage(Object error) {
        if (error instanceof ServerError || error instanceof AuthFailureError) {
            return handleServerError(error);
        } else if (error instanceof ParseError) {
            return "攻城狮正在紧急维护中，请稍后再试";
        } else if (error instanceof NoConnectionError || error instanceof NetworkError) {
            return "网络连接不可用，请检查网络";
        } else if (error instanceof TimeoutError) {
            return "当前网络不稳定，请重试";
        }
        return "攻城狮正在紧急维护中，请稍后再试";
    }

    private static String handleServerError(Object err) {
        VolleyError error = (VolleyError) err;

        NetworkResponse response = error.networkResponse;

        if (response != null) {
            switch (response.statusCode) {
//				case 404:
//				case 422:
//				case 401:
//					String s = new String(response.data);
//					s = s.trim();
//					return s;
                default:
                    return "攻城狮正在紧急维护中，请稍后再试";
            }
        }
        return "攻城狮正在紧急维护中，请稍后再试";
    }
}
