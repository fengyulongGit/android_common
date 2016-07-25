package com.fengyulong.android_common.net;

import java.util.Map;

/**
 * Created by fengyulong on 2016/7/25.
 */
public abstract class HttpParamsUtils {
    /**
     * 添加公共参数
     *
     * @param params
     * @return
     */
    public abstract Map<String, String> addPublicParams(Map<String, String> params);

    public abstract Map<String, String> securityRequestParams(Map<String, String> params);
}
