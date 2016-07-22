/**
 *
 */
package com.fengyulong.android_common.net.http;

import android.content.Context;
import android.util.Log;

import com.fengyulong.android_common.utils.EnumRunMode;
import com.fengyulong.android_common.utils.WokeCommonUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author Bean
 */
public class HttpGetExecutor extends HttpExecutor {

    public HttpGetExecutor(Context context) {
        super(context);
    }

    @Override
    public HttpResponse onSyncExecute() throws HttpException {
        if (url == null)
            throw new HttpException("URL地址为空");

        if (WokeCommonUtil.RUN_MODE == EnumRunMode.DEBUG) {
            Log.d(HttpGetExecutor.class.getName(), url);
        }

        HttpGet httpGet = new HttpGet(addParamsToUrl(url));

        for (HttpParam header : headers) {
            httpGet.addHeader(header.getName(), header.getValue());
        }

        HttpParams parms = new BasicHttpParams();
        parms.setParameter("charset", WokeCommonUtil.DEFAULT_CHARSET);
        HttpConnectionParams.setConnectionTimeout(parms, 20 * 1000);
        HttpConnectionParams.setSoTimeout(parms, 20 * 1000);
        HttpClient httpclient = new DefaultHttpClient(parms);
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);

        try {
            HttpResponse response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                return response;
            } else {
                throw new HttpException("提交请求失败, StatusCode=" + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            throw new HttpException(e.getMessage());
        }
    }

    @Override
    public void onAsyncExecute(HttpCallback callback, int tag) {
        //使用线程发起Http请求
        ExecuteRunable runable = new ExecuteRunable();
        runable.setCallback(callback);
        runable.setExecutore(this);
        runable.setTag(tag);

        Thread thread = new Thread(runable);
        thread.start();
    }

    private String addParamsToUrl(String url) {
        StringBuilder sb = new StringBuilder();

        if ((url.contains("?") && url.endsWith("?"))) {
            sb.append(url);
        } else if (!url.contains("?")) {
            sb.append(url).append("?");
        } else if (url.contains("?") && !url.endsWith("?")) {
            sb.append(url).append("&");
        }

        int i = 0;
        for (HttpParam param : params) {
            if (i != 0) {
                sb.append("&");
            }
            sb.append(param.getName()).append("=");
            sb.append(URLEncoder.encode(param.getValue()));
            i++;
        }

        Log.d(HttpGetExecutor.class.getCanonicalName(), sb.toString());

        return sb.toString();
    }

    private class ExecuteRunable implements Runnable {
        private HttpGetExecutor executor;
        private HttpCallback callback;
        private int tag;

        public void setExecutore(HttpGetExecutor executor) {
            this.executor = executor;
        }

        public void setCallback(HttpCallback callback) {
            this.callback = callback;
        }


        public void setTag(int tag) {
            this.tag = tag;
        }

        @Override
        public void run() {
            if (executor == null)
                return;

            HttpException exp1 = null;
            HttpResponse response = null;
            try {
                response = executor.syncExecute();
            } catch (HttpException exp) {
                exp1 = exp;
            }

            if (callback != null)
                callback.onReceiveData(response, exp1, tag);
        }
    }
}
