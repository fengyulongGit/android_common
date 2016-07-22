package com.fengyulong.android_common.view.autolist;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class CustomListView extends RelativeLayout {
    private static boolean addChildType;
    private String TAG = CustomListView.class.getSimpleName();
    private CustomAdapter myCustomAdapter;
    private final Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                if (msg.getData().containsKey("getRefreshThreadHandler")) {
                    CustomListView.setAddChildType(false);
                    CustomListView.this.myCustomAdapter.notifyCustomListView(CustomListView.this);
                }
            } catch (Exception e) {
//                Log.w(CustomListView.this.TAG, e);
            }
        }
    };
    private int dividerHeight = 0;
    private int dividerWidth = 0;

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    static final boolean isAddChildType() {
        return addChildType;
    }

    public static void setAddChildType(boolean addChildType) {
        addChildType = addChildType;
    }

    protected void onLayout(boolean arg0, int argLeft, int argTop, int argRight, int argBottom) {
//        Log.i(this.TAG, "L:" + argLeft + " T:" + argTop + " R:" + argRight + " B:" + argBottom);
        int count = getChildCount();
        int row = 0;
        int lengthX = 0;
        int lengthY = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();

            if (lengthX == 0)
                lengthX += width;
            else {
                lengthX += width + getDividerWidth();
            }

            if ((i == 0) && (lengthX <= argRight - argLeft)) {
                lengthY += height;
            }

            if (lengthX > argRight - argLeft) {
                lengthX = width;
                lengthY += getDividerHeight() + height;
                row++;
                child.layout(lengthX - width, lengthY - height, lengthX, lengthY);
            } else {
                child.layout(lengthX - width, lengthY - height, lengthX, lengthY);
            }
        }
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = lengthY;
        setLayoutParams(lp);
        if (isAddChildType())
            new Thread(new RefreshCustomThread()).start();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(0, 0);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    final int getDividerHeight() {
        return this.dividerHeight;
    }

    public void setDividerHeight(int dividerHeight) {
        this.dividerHeight = dividerHeight;
    }

    final int getDividerWidth() {
        return this.dividerWidth;
    }

    public void setDividerWidth(int dividerWidth) {
        this.dividerWidth = dividerWidth;
    }

    public void setAdapter(CustomAdapter adapter) {
        this.myCustomAdapter = adapter;
        setAddChildType(true);
        adapter.notifyCustomListView(this);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.myCustomAdapter.setOnItemClickListener(listener);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.myCustomAdapter.setOnItemLongClickListener(listener);
    }

    private final void sendMsgHanlder(Handler handler, Bundle data) {
        Message msg = handler.obtainMessage();
        msg.setData(data);
        handler.sendMessage(msg);
    }

    private final class RefreshCustomThread
            implements Runnable {
        private RefreshCustomThread() {
        }

        public void run() {
            Bundle b = new Bundle();
            try {
                Thread.sleep(50L);
            } catch (Exception localException) {
            } finally {
                b.putBoolean("getRefreshThreadHandler", true);
                CustomListView.this.sendMsgHanlder(CustomListView.this.handler, b);
            }
        }
    }
}

/* Location:           C:\Users\BenBen\Desktop\android-custom-vg.2.6.0.jar
 * Qualified Name:     com.custom.vg.list.CustomListView
 * JD-Core Version:    0.6.0
 */