package com.fyl.android_common.net;

import java.util.Map;

/**
 * Created by fyl on 2016/7/25.
 */
public abstract class HttpParamsUtils {
    public abstract Map<String, String> addPublicParams(Map<String, String> params);

    public abstract Map<String, String> securityRequestParams(Map<String, String> params);
}
