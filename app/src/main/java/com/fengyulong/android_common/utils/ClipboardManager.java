package com.fengyulong.android_common.utils;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipDescription;
import android.content.Context;

/**
 * 剪切板使用工具类
 * 对应的类：
 * API 11之前： android.text.ClipboardManager
 * API 11之后： android.content.ClipboardManager
 *
 * @author ranfl
 */
public class ClipboardManager {

    private Context context;

    public ClipboardManager(Context context) {
        super();
        this.context = context;
    }

    /**
     * 过去剪切板内容
     * API 11之前： android.text.ClipboardManager
     * API 11之后： android.content.ClipboardManager
     */
    public String getContent() {
        String content = "";
        if (android.os.Build.VERSION.SDK_INT > 11) {
            android.content.ClipboardManager cm = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (cm.hasPrimaryClip()) {
                ClipDescription clipDescription = cm.getPrimaryClipDescription();
                if (clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    ClipData cd = cm.getPrimaryClip();
                    Item item = cd.getItemAt(0);
                    content = item.getText().toString().trim();
                    if (ValidateUtil.isEmpty(content)) {
                        content = "";
                    }
                }
            }

        } else {
            android.text.ClipboardManager c = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            content = c.getText().toString().trim();
            if (ValidateUtil.isEmpty(content)) {
                content = "";
            }
        }

        return content;
    }

    /**
     * 设置剪切板的内容
     *
     * @param content
     */
    public void setContent(String content) {
        if (android.os.Build.VERSION.SDK_INT > 11) {
            android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData cd = ClipData.newPlainText("woke", content.trim());
            clipboardManager.setPrimaryClip(cd);

        } else {
            @SuppressWarnings("deprecation")
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(content.trim());
        }
    }
}
