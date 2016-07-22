/**
 *
 */
package com.fengyulong.android_common.net.http;

import android.content.Context;
import android.util.Log;

import com.woke.common.utils.EnumRunMode;
import com.woke.common.utils.WokeCommonUtil;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bean
 */
public class HttpPostExecutor extends HttpExecutor {

    public HttpPostExecutor(Context context) {
        super(context);
    }

    @Override
    public HttpResponse onSyncExecute() throws HttpException {
        if (url == null)
            throw new HttpException("Url地址为空");

        /**
         * 设置参数请求编码的格式为utf-8
         */
        HttpParams parms = new BasicHttpParams();
        parms.setParameter("charset", WokeCommonUtil.DEFAULT_CHARSET);
        HttpConnectionParams.setConnectionTimeout(parms, 20 * 1000);
        HttpConnectionParams.setSoTimeout(parms, 20 * 1000);

        HttpClient httpclient = new DefaultHttpClient(parms);
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);


        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("charset", WokeCommonUtil.DEFAULT_CHARSET);

        for (HttpParam header : headers) {
            httpPost.addHeader(header.getName(), header.getValue());
        }

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        for (HttpParam param : params) {
            BasicNameValuePair pair = new BasicNameValuePair(param.getName(), param.getValue());
            nameValuePairs.add(pair);
        }

        try {
            if (byteContent != null && byteContent.length != 0) {
//				StringEntity se = new StringEntity(entityContent,WokeCommonUtil.DEFAULT_CHARSET);
//				se.setContentType("application/json");

                ByteArrayEntity reqEntity = new ByteArrayEntity(byteContent);
                reqEntity.setContentType("application/json");
//		        method.setEntity(reqEntity);  

                httpPost.setEntity(reqEntity);
            } else {

                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, WokeCommonUtil.DEFAULT_CHARSET);
                entity.setContentEncoding(WokeCommonUtil.DEFAULT_CHARSET);
                httpPost.setEntity(entity);
            }

        } catch (UnsupportedEncodingException exp) {

        }

        if (WokeCommonUtil.RUN_MODE == EnumRunMode.DEBUG) {
            Log.d(HttpPostExecutor.class.getCanonicalName(), entityContent);
        }

        try {
            HttpResponse response = httpclient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == 200) {
                return response;
            } else {
                if (WokeCommonUtil.RUN_MODE == EnumRunMode.DEBUG) {
                    Log.d(HttpGetExecutor.class.getCanonicalName(), EntityUtils.toString(response.getEntity()));
                }
                throw new HttpException("提交请求失败, StatusCode=" + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            throw new HttpException(e.getMessage());
        }
    }

    @Override
    public void onAsyncExecute(HttpCallback callback, int tag) {
        ExecuteRunable runable = new ExecuteRunable();
        runable.setCallback(callback);
        runable.setExecutore(this);
        runable.setTag(tag);

        Thread thread = new Thread(runable);
        thread.start();
    }


    private class ExecuteRunable implements Runnable {
        private HttpExecutor executor;
        private HttpCallback callback;
        private int tag;

        public void setExecutore(HttpExecutor executor) {
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
