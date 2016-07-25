package com.fengyulong.android_common.net;

import java.io.Serializable;


public class Response implements Serializable {

    public String statusCode;
    public String errorInfo;
    public String versionCode;

    public boolean isSuccess() {
        return "200".equals(statusCode);
    }

    public class Data implements Serializable {
        public String accessToken;
        public String expires_in;
    }
}
