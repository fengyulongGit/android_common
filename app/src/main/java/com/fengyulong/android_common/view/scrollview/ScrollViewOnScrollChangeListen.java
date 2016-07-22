package com.fengyulong.android_common.view.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by FENGYULONG on 2016/5/19.
 */
public class ScrollViewOnScrollChangeListen extends ScrollView {
    private OnScrollChangeListener onScrollChangeListener;

    public ScrollViewOnScrollChangeListen(Context context) {
        super(context);
    }

    public ScrollViewOnScrollChangeListen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewOnScrollChangeListen(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (getOnScrollChangeListener() != null) {
            getOnScrollChangeListener().onScrollChange(this, l, t, oldl, oldt);
        }
    }

    public OnScrollChangeListener getOnScrollChangeListener() {
        return onScrollChangeListener;
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        this.onScrollChangeListener = onScrollChangeListener;
    }

    public interface OnScrollChangeListener {
        void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }
}
