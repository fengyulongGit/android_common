package com.fengyulong.android_common.net;

import android.content.Context;

/**
 * Description: 服务器返回异常（如单点登录）
 *
 * @author fengyulong
 * @code
 */

public class ReqCodeErrorHelper {

    public static void handleReqCode(final Context context, Response response) {
//		if ("S4002".equals(JsonUtils.getFiledData(result, "rspCode"))) {
//			DialogUtils.showAlertDialog(context, "提示", "你的账号已在别处登录，若非本人操作，请及时修改密码，保障账户安全！", "我知道了", new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int arg1) {
//					dialog.dismiss();
//					Intent mIntent = new Intent(Constants.EVENT_SESSION_FAILED);
//					context.sendBroadcast(mIntent);
//				}
//			});
//		}
    }
}
