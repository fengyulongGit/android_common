package com.fengyulong.android_common.net;

public interface HttpActionHandle {
    public void handleActionStart(String actionName, Response response);

    public void handleActionFinish(String actionName, Response response);

    public void handleActionError(String actionName, Response response);

    public void handleActionSuccess(String actionName, Response response);

}
