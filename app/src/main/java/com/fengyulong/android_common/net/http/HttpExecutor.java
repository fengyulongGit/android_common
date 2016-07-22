/**
 *
 */
package com.fengyulong.android_common.net.http;

import android.content.Context;

import org.apache.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bean
 */
public abstract class HttpExecutor {

    protected List<HttpParam> params = new ArrayList<HttpParam>();
    protected List<HttpParam> headers = new ArrayList<HttpParam>();

    protected String url;
    protected Context context;

    protected long startTime;

    protected String entityContent;
    protected String contentType;

    protected byte[] byteContent;

    public HttpExecutor(Context context) {
        this.context = context;
    }

    /**
     * 增加请求参数
     *
     * @param param
     */
    public void addParam(HttpParam param) {
        if (param == null)
            return;

        if (!params.contains(param)) {
            params.add(param);
        }
    }

    public void removeParam(HttpParam param) {
        if (param == null)
            return;

        if (params.contains(param)) {
            params.remove(param);
        }
    }

    public void addHeader(HttpParam param) {
        if (param == null)
            return;

        if (!headers.contains(param)) {
            headers.add(param);
        }
    }


    public void addHeaders(List<HttpParam> headers) {
        this.headers.addAll(headers);
    }

    public void removeHeader(HttpParam param) {
        if (param == null)
            return;

        if (headers.contains(param)) {
            headers.remove(param);
        }
    }

    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public HttpResponse syncExecute() throws HttpException {
        startTime = System.currentTimeMillis();
        HttpResponse response = onSyncExecute();
        long end = System.currentTimeMillis();

//		if(logger.isDebugEnabled()) {
//			logger.debug(formatNetLog(end - startTime));
//		}
        return response;
    }

    public void asyncExecute(HttpCallback callback, int tag) {
        onAsyncExecute(callback, tag);
    }

    protected abstract HttpResponse onSyncExecute() throws HttpException;

    protected abstract void onAsyncExecute(HttpCallback callback, int tag);

    private String formatNetLog(long costTime) {
        StringBuffer sb = new StringBuffer();
        sb.append("URL : ").append(url).append("\r\n");
        sb.append("Request Params : ");
        for (HttpParam param : params) {
            sb.append(param.getName()).append("=").append(param.getValue()).append("&");
        }
        sb.append("\r\n");
        sb.append("CostTime : ").append(costTime).append("ms").append("\r\n");

        sb.append("\r\n");

        return sb.toString();
    }

    /**
     * @return the entityContent
     */
    public String getEntityContent() {
        return entityContent;
    }

    /**
     * @param entityContent the entityContent to set
     */
    public void setEntityContent(String entityContent) {
        this.entityContent = entityContent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getByteContent() {
        return byteContent;
    }

    public void setByteContent(byte[] byteContent) {
        this.byteContent = byteContent;
    }

}
