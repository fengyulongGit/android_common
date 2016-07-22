package com.fengyulong.android_common.view;


import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.fengyulong.android_common.R;

/**
 * 自定义的一些试图
 *
 * @author ranfl
 */
public class AutoTextView {

    /*
     * 动态生成的有背景内容的TextView
     */
    public static TextView getMyTextView(Context context, String str) {
        TextView textView = new TextView(context);
        textView.setText(str);
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        textView.setPadding(2, 2, 2, 2);
        textView.setTextSize(12);
        textView.setSingleLine(true);
        textView.setBackgroundResource(R.drawable.background_benefit);
        return textView;
    }

    /*
     * 动态生成的无背景内容的TextView
     */
    public static TextView getMyTextViewByNoBackground(Context context, String str) {
        TextView textView = new TextView(context);
        textView.setText(str);
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        textView.setPadding(2, 2, 2, 2);
        textView.setTextSize(14);
        textView.setSingleLine(true);
        return textView;
    }
}
