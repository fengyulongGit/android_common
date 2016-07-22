package com.fengyulong.android_common.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.TranslateAnimation;

import com.woke.common.R;
import com.woke.common.utils.CommonUtil;

/**
 * Created by COOLWEN on 2016/5/31.
 */
public class NestedScrollBehavior4Info extends CoordinatorLayout.Behavior<Toolbar> {
    private static final String TAG = "NestedScrollBehavior4Info";
    private int startOffset = 0;
    private int endOffset4HeaderView = 0;
    private int endOffset4HeaderBackgroundView = 0;
    private Context context;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private View target;

    public NestedScrollBehavior4Info(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }


    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, final Toolbar toolbar, final View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        this.coordinatorLayout = coordinatorLayout;
        this.toolbar = toolbar;
        this.target = target;

        if (target instanceof NestedScrollView) {
            ((NestedScrollView) target).setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    NestedScrollBehavior4Info.this.onScrollChange(v.getScrollY());
                }
            });
        }
    }

    private void onScrollChange(int offset) {
        startOffset = 0;

        //初始化头部整体高度滑动区间（包含了logo）
        if (endOffset4HeaderView == 0) {
            View headerView = findViewById(R.id.nested_header_view);
            if (headerView != null && toolbar != null) {
                endOffset4HeaderView = headerView.getHeight() - toolbar.getHeight();
            }
        }

        //初始化头部背景高度滑动区间（不包含logo）
        if (endOffset4HeaderBackgroundView == 0) {
            View headerBackgroundView = findViewById(R.id.nested_header_background_view);
            if (headerBackgroundView != null && toolbar != null) {
                endOffset4HeaderBackgroundView = headerBackgroundView.getHeight() - toolbar.getHeight();
            }
        }

        //头部高度处理的业务为：title
        if (endOffset4HeaderView > 0) {
            if (offset <= startOffset) {
                //当滑动到顶部时，显示title1，隐藏title2
                title1Alpha(1f);
                title2Alpha(0f);
                title3Alpha(1f);
            } else if (offset > startOffset && offset < endOffset4HeaderView) {
                //依据区间的滑动差值计算title1的透明度，同时，在区间内隐藏title2
                float precent = (float) (offset - startOffset) / endOffset4HeaderView;
                float alpha = 1 - precent * 0.9f;
                title1Alpha(alpha);
                title2Alpha(0f);
                title3Alpha(1f);
//                int alpha = Math.round(precent * 255);
//                toolbar.getBackground().setAlpha(alpha);
            } else if (offset >= endOffset4HeaderView) {
                //达到滑动区间范围，则隐藏title1，动画显示title2
                title1Alpha(0f);
                title2Translate();
                title3Alpha(0f);
            }
        }

        if (endOffset4HeaderBackgroundView > 0) {
            if (offset <= startOffset) {
                //当滑动到顶部时，toolbar的背景全透明，logo不缩放
                toolbarAlpha(0);
                logoScale(1f);
            } else if (offset > startOffset && offset < endOffset4HeaderBackgroundView) {
                //依据区间的滑动差值计算logo的缩放大小，同时，在区间内toolbar的背景全透明
                toolbarAlpha(0);

                float precent = (float) (offset - startOffset) / endOffset4HeaderBackgroundView;
                float scale = 1 - precent * 0.5f;
                logoScale(scale);
//                int alpha = Math.round(precent * 255);
//                toolbar.getBackground().setAlpha(alpha);
            } else if (offset >= endOffset4HeaderBackgroundView) {
                //达到滑动区间范围，则toolbar背景不透明，logo的缩放大小为0
                toolbarAlpha(255);
//                logoScale(0f);
            }
        }
    }

    /**
     * @param alpha alpha为0到255
     */
    private void toolbarAlpha(int alpha) {
        if (toolbar != null) {
            toolbar.getBackground().setAlpha(alpha);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void title1Alpha(float alpha) {
        View view = findViewById(R.id.nested_title1);
        if (view != null) {
            view.setAlpha(alpha);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void title2Alpha(float alpha) {
        View view = findViewById(R.id.nested_title2);
        if (view != null) {
            view.setAlpha(alpha);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void title2Translate() {
        View view = findViewById(R.id.nested_title2);
        if (view != null) {
            if (view.getAlpha() != 1f) {//确保只处理一次
                view.setAlpha(1f);
                float fromXDelta = 0f;
                float toXDelta = 0f;
                float fromYDelta = CommonUtil.dip2px(context, 50);
                float toYDelta = 0f;
                TranslateAnimation ta = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
                ta.setDuration(200);
                view.startAnimation(ta);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void title3Alpha(float alpha) {
        View view = findViewById(R.id.nested_title3);
        if (view != null) {
            view.setAlpha(alpha);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void logoScale(float scale) {
        View view = findViewById(R.id.nested_logo);
        if (view != null) {
            view.setScaleX(scale);
            view.setScaleY(scale);
        }
    }

    private View findViewById(int id) {
        if (coordinatorLayout != null) {
            return coordinatorLayout.findViewById(id);
        }
        return null;
    }

}
