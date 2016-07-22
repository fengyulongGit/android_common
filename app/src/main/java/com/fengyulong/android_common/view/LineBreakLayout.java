package com.fengyulong.android_common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class LineBreakLayout extends ViewGroup {
    private final static String TAG = "LineBreakLayout";
    private final static int BOTTOM_MARGIN = 1;

    public LineBreakLayout(Context context) {
        super(context);
    }

    public LineBreakLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LineBreakLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //当一行的第一个子元素超过宽度时，不进行换行处理
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        int x = 0;
        int y = 0;
        int row = 0;

        int count = 0;
        for (int index = 0; index < childCount; index++) {
            final View child = getChildAt(index);
            if (child.getVisibility() != View.GONE) {
                child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                // 此处增加onlayout中的换行判断，用于计算所需的高度
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                x += width;
                y = row * height + height;
                count++;
                if (x > maxWidth) {
                    x = width;
                    if (count > 1) {
                        y = ++row * height + height;
                    } else {
                        y = row * height + height;
                        count = 1;
                    }
                }
            }
        }
        // 设置容器所需的宽度和高度
        setMeasuredDimension(maxWidth, y + row * BOTTOM_MARGIN + 5);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        int maxWidth = r - l;
        int x = 0;
        int y = 0;
        int row = 0;
        int count = 0;
        for (int i = 0; i < childCount; i++) {
            final View child = this.getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                x += width;
                y = row * height + height;
                count++;
                if (x > maxWidth) {
                    x = width;
                    if (count > 1) {
                        y = ++row * height + height;
                    } else {
                        y = row * height + height;
                        count = 1;
                    }
                }
                child.layout(x - width, row * BOTTOM_MARGIN + y - height, x,
                        row * BOTTOM_MARGIN + y);
            }
        }
    }
}

