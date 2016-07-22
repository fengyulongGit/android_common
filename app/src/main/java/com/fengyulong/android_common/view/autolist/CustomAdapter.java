package com.fengyulong.android_common.view.autolist;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

public class CustomAdapter {
    private String TAG = CustomAdapter.class.getSimpleName();
    private View myView;
    private ViewGroup myViewGroup;
    private CustomListView myCustomListView;
    private OnItemClickListener listener;
    private OnItemLongClickListener longListener;

    public int getCount() {
        return 0;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0L;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    private final void getAllViewAddSexangle() {
        this.myCustomListView.removeAllViews();
        for (int i = 0; i < getCount(); i++) {
            View viewItem = getView(i, this.myView, this.myViewGroup);
            this.myCustomListView.addView(viewItem, i);
        }
    }

    public void notifyDataSetChanged() {
        CustomListView.setAddChildType(true);
        notifyCustomListView(this.myCustomListView);
    }

    public void notifyCustomListView(CustomListView formateList) {
        this.myCustomListView = formateList;
        this.myCustomListView.removeAllViews();
        getAllViewAddSexangle();
        setOnItemClickListener(this.listener);
        setOnItemLongClickListener(this.longListener);
    }

    public void setOnItemClickListener(final OnItemClickListener listener) {
        this.listener = listener;
        for (int i = 0; i < this.myCustomListView.getChildCount(); i++) {
            final int parame = i;
            View view = this.myCustomListView.getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
//                    Log.i(CustomAdapter.this.TAG, "当前Item的值 : " + parame);
                    if (CustomAdapter.this.listener != null) {
                        CustomAdapter.this.listener.onItemClick(null, v, parame, CustomAdapter.this.getCount());
                    }
                }
            });
        }
    }

    public void setOnItemLongClickListener(final OnItemLongClickListener listener) {
        this.longListener = listener;
        for (int i = 0; i < this.myCustomListView.getChildCount(); i++) {
            final int parame = i;
            View view = this.myCustomListView.getChildAt(i);
            view.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View v) {

                    if (CustomAdapter.this.longListener != null) {
                        try {
                            CustomAdapter.this.longListener.onItemLongClick(null, v, parame, CustomAdapter.this.getCount());
                        } catch (Exception e) {

                        }
                    }
                    return true;
                }
            });
        }
    }
}

/* Location:           C:\Users\BenBen\Desktop\android-custom-vg.2.6.0.jar
 * Qualified Name:     com.custom.vg.list.CustomAdapter
 * JD-Core Version:    0.6.0
 */