package com.fengyulong.android_common.view.gallery;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.woke.common.R;
import com.woke.common.view.nineoldandroids.view.ViewHelper;

import java.util.List;

/**
 * Created by liuguangrui on 16/4/27.
 */
@SuppressLint("NewApi")
public class GalleryView extends ScrollView implements View.OnTouchListener {

    private int mItemWidth;
    private int mItemHeight;

    private int mSpace;

    private int centerX;

    private int startScrollX;

    private int currentPos = 0;

    private Float lastMoveX;

    private Float moveStartX;

    private List<View> views;

    private LinearLayout mainView;

    private Handler handler = new Handler();

    public GalleryView(Context context) {
        this(context, null);
    }

    public GalleryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initExtendAttr(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GalleryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initExtendAttr(context, attrs, defStyleAttr);
        init();
    }

    private void initExtendAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.GalleryView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.GalleryView_g_itemWidth) {//子项的宽度
                mItemWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, getResources().getDisplayMetrics()));// 默认为100DP
            } else if (attr == R.styleable.GalleryView_g_space) {//每项横着的距离
                mSpace = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, getResources().getDisplayMetrics()));// 默认为10DP
            } else if (attr == R.styleable.GalleryView_g_itemHeight) {//子项的高度
                mItemHeight = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150f, getResources().getDisplayMetrics()));// 默认为150DP
            }
        }
    }

    private void init() {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        this.mainView = layout;
        addView(this.mainView);

        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        this.setOnTouchListener(this);
        startScrollX = -(screenWidth - mItemWidth) / 2;
    }

    @SuppressLint("NewApi")
    public void setViews(final List<View> newViews, final Integer curIndex) {
        this.views = newViews;
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (views != null && views.size() > 0) {
                    for (int i = 0; i < views.size(); i++) {
                        View v = views.get(i);
                        if (i != curIndex) {
                            ViewHelper.setScaleX(v, 0.8f);
                            ViewHelper.setScaleY(v, 0.8f);
                            v.setAlpha(0.5f);
                        }
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth, mItemHeight);
                        v.setLayoutParams(params);
                        mainView.addView(v);
                        LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(mSpace, getHeight());
                        LinearLayout space = new LinearLayout(getContext());
                        space.setAlpha(1);
                        space.setLayoutParams(spaceParams);
                        mainView.addView(space);
                    }
                    DisplayMetrics dm = new DisplayMetrics();
                    dm = getResources().getDisplayMetrics();
                    int screenWidth = dm.widthPixels;
                    mainView.scrollBy(startScrollX + (mItemWidth + mSpace) * curIndex, 0);
                    //计算每个View位于中央时的滚动值
                }
            }
        }, 200);
    }

    private void setEmptyView() {
        //TODO
    }

    public void clearViews() {
        mainView.removeAllViews();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moveStartX = event.getX();
                lastMoveX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //判断是否可以移动
                if (mainView != null && views != null) {
                    if (event.getX() > this.lastMoveX + 20) {//手势右移 兼容20像素
                        return true;
                    }
                    if (event.getX() < this.lastMoveX - 20) {
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moveStartX = event.getX();
                lastMoveX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //判断是否可以移动
                if (mainView != null && views != null) {
                    float curPos = (mainView.getScrollX() - startScrollX) * 1f / (mItemWidth + mSpace);
                    if (((curPos >= 0.0 && curPos <= 0.1) || curPos > views.size() * 1f - 1) && event.getX() > this.lastMoveX) {//右移
                        return false;
                    }
                    if (((curPos > views.size() - 1 && curPos <= views.size() + 0.1) || curPos < 0.0) && event.getX() < this.lastMoveX) {
                        return false;
                    }
                    if (event.getX() > this.lastMoveX) {//右移
                        currentPos = new Double(Math.floor(curPos)).intValue();//当前需要到达的位置
                    }
                    if (event.getX() < this.lastMoveX) {
                        currentPos = new Double(Math.ceil(curPos)).intValue();//当前需要到达的位置
                    }
                    /*for(int i=0;i<views.size();i++){
                        float scale = (i == currentPos)?1f:0.8f;
                        float alpha = (i == currentPos)?1f:0.5f;
                        View view = views.get(i);
                        view.setAlpha(alpha);
                        ViewHelper.setScaleX(view,scale);
                        ViewHelper.setScaleY(view,scale);
                    }*/
                    int lastCurPos = 0;
                    if (event.getX() > this.lastMoveX) {//右移
                        lastCurPos = currentPos + 1;//移动之前的当前页
                        if (lastCurPos >= views.size()) {//向右刚开始移动时，currentPos即为lastCurPos
                            lastCurPos = views.size() - 1;
                        }
                    }
                    if (event.getX() < this.lastMoveX) {
                        lastCurPos = currentPos - 1;//移动之前的当前页
                        if (lastCurPos < 0) {//向左刚开始移动时，currentPos即为lastCurPos
                            lastCurPos = 0;
                        }
                    }
                    View curView = views.get(lastCurPos);
                    //计算当前页的透明度以及大小
                    float curMaxScroll = mItemWidth + mSpace;//每一项最大移动距离
                    float curNowScrollX = Math.abs(event.getX() - moveStartX);//总共移动的距离
                    float coefficient = curNowScrollX / curMaxScroll;
                    if (coefficient > 1.0f) {
                        coefficient = 1.0f;
                    }

                    if (lastCurPos != currentPos && Math.abs(event.getX() - this.lastMoveX) > 5) {
                        //逐步变更透明度和缩放比例；当两者相等时，表示刚开始移动，不需要处理
                        //移动5之后再处理，首次进入该方法时，lastCurPos计算结果是上一次的页面
                        if (event.getX() > this.lastMoveX) {//右移
                            //当前页缩小并透明
                            curView.setAlpha(1.0f - (0.5f) * coefficient);
                            ViewHelper.setScaleX(curView, (1.0f - 0.2f * coefficient));
                            ViewHelper.setScaleY(curView, (1.0f - 0.2f * coefficient));
                            if (currentPos >= 0) {
                                View preView = views.get(currentPos);
                                preView.setAlpha(0.5f + (0.5f) * coefficient);
                                ViewHelper.setScaleX(preView, (0.8f + 0.2f * coefficient));
                                ViewHelper.setScaleY(preView, (0.8f + 0.2f * coefficient));
                            }
                        } else {
                            //当前页缩小并透明
                            curView.setAlpha(1.0f - (0.5f) * coefficient);
                            ViewHelper.setScaleX(curView, (1.0f - 0.2f * coefficient));
                            ViewHelper.setScaleY(curView, (1.0f - 0.2f * coefficient));
                            if (currentPos < views.size()) {
                                View nextView = views.get(currentPos);
                                nextView.setAlpha(0.5f + (0.5f) * coefficient);
                                ViewHelper.setScaleX(nextView, (0.8f + 0.2f * coefficient));
                                ViewHelper.setScaleY(nextView, (0.8f + 0.2f * coefficient));
                            }
                        }
                    }
                    //
                    Float newX = event.getX();
                    mainView.scrollBy(-(new Float(newX - lastMoveX)).intValue(), 0);
                    lastMoveX = newX;
                }
                break;
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < views.size(); i++) {//因为松开时，大小和透明度可能没有到达上线
                    float scale = (i == currentPos) ? 1f : 0.8f;
                    float alpha = (i == currentPos) ? 1f : 0.5f;
                    View view = views.get(i);
                    ViewHelper.setScaleX(view, scale);
                    ViewHelper.setScaleY(view, scale);
                    view.setAlpha(alpha);
                }
                //实际应该到的位置
                float destScrollX = startScrollX + (mItemWidth + mSpace) * currentPos;
                /*float nowScrollY = mainView.getScrollX();
                if(currentPos == 0){//等于0的时候，如果滑动距离较大，偏移有问题，暂时兼容处理
                    mainView.scrollTo(startScrollX,mainView.getScrollY());
                }
                else{
                    if(event.getX()>this.lastMoveX){//右移
                        mainView.scrollBy((new Float(nowScrollY - destScrollX)).intValue(),0);
                    }
                    else{
                        mainView.scrollBy(-(new Float(nowScrollY - destScrollX)).intValue(),0);
                    }
                }*/
                if (event.getX() > this.lastMoveX) {//右移
                    mainView.scrollTo((new Float(destScrollX)).intValue(), mainView.getScrollY());
                } else {
                    mainView.scrollTo((new Float(destScrollX)).intValue(), mainView.getScrollY());
                }
                break;
        }
        return false;
    }
}
