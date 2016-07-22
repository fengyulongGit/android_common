/**
 *
 */
package com.fengyulong.android_common.net.http;

import android.content.Context;

/**
 * @author Bean
 */
public class HttpExecutorFactory {
    private static Context context;

    public static void init(Context context) {
        HttpExecutorFactory.context = context;
    }

    public static HttpExecutor createHttpExecutor(EnumHttpMethod method) {
        if (method == EnumHttpMethod.GET) {
            return new HttpGetExecutor(context);
        } else if (method == EnumHttpMethod.POST) {
            return new HttpPostExecutor(context);
        }

        return null;
    }
}
