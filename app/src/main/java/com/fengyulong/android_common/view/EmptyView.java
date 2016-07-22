package com.fengyulong.android_common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengyulong.android_common.R;

public class EmptyView extends LinearLayout {

    private LinearLayout layout = null;
    private ImageView image;
    private TextView text;
    private Context context;

    public EmptyView(Context context) {
        super(context);

        initView(context);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context);
    }

    @SuppressLint("NewApi")
    public EmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        if (layout == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflater.inflate(R.layout.empty_layout, this);

            image = (ImageView) layout.findViewById(R.id.empty_image);
            text = (TextView) layout.findViewById(R.id.empty_text);
        }

    }

    public void setEmptyImageAndContent(int resid, CharSequence content) {
        setEmptyImage(resid);
        setEmptyContent(content);
    }

    public void setEmptyImageAndContent(int resid, int contentResid) {
        setEmptyImage(resid);
        setEmptyContent(context.getResources().getText(contentResid));
    }

    /**
     * 设置空的图片
     *
     * @param resId 图片资源
     */
    public void setEmptyImage(int resId) {
        if (image != null) {
            image.setBackgroundDrawable(context.getResources().getDrawable(resId));
        }
    }

    /**
     * 设置空的提示文字
     *
     * @param content 提示文字
     */
    public void setEmptyContent(CharSequence content) {
        if (text != null) {
            text.setText(content);
        }
    }

}
