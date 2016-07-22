package com.fengyulong.android_common.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.fengyulong.android_common.R;

/**
 * Created by COOLWEN on 2016/5/31.
 */
public class ToolbarAlphaBehavior extends CoordinatorLayout.Behavior<Toolbar> {
    private static final String TAG = "ToolbarAlphaBehavior";
    private int offset = 0;
    private int startOffset = 0;
    private int endOffset = 0;
    private Context context;

    public ToolbarAlphaBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }


    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, final Toolbar toolbar, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        startOffset = 0;
        if (endOffset == 0) {
            View headerView = target.findViewById(R.id.nested_header_view);
            if (headerView == null) {
                return;
            }
            endOffset = headerView.getHeight() - toolbar.getHeight();
        }
        if (target instanceof NestedScrollView) {
            NestedScrollView nestedScrollView = (NestedScrollView) target;
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    offset = v.getScrollY();
                    if (offset <= startOffset) {  //alpha为0
                        toolbar.getBackground().setAlpha(0);
                    } else if (offset > startOffset && offset < endOffset) { //alpha为0到255
                        float precent = (float) (offset - startOffset) / endOffset;
                        int alpha = Math.round(precent * 255);
                        toolbar.getBackground().setAlpha(alpha);
                    } else if (offset >= endOffset) {  //alpha为255
                        toolbar.getBackground().setAlpha(255);
                    }
                }
            });
        }
    }

}
