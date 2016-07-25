package com.fengyulong.android_common.net;

/**
 * Description: 网络请求回调方法
 *
 * @author fengyulong
 * @code
 */

public interface HttpActionHandle {
    //请求动作开始调用
    public void handleActionStart(String actionName, Response response);

    //请求动作结束调用
    public void handleActionFinish(String actionName, Response response);

    /***
     * Description: 请求动作错误调用
     *
     * @param actionName 标识
     * @param response   返回结果
     * @Author wf
     * Create Date: 2016年2月24日 下午3:26:06
     */
    public void handleActionError(String actionName, Response response);

    //请求动作成功时调用
    public void handleActionSuccess(String actionName, Response response);

}
