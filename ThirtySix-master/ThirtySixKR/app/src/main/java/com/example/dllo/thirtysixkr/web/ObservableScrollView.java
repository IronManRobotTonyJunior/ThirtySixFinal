package com.example.dllo.thirtysixkr.web;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView {
    private OnScrollChangedCallback mOnScrollChangedCallback;
    private OnScrollChangedCallback2 mOnScrollChangedCallback2;

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ObservableScrollView(Context context) {
        super(context);
    }
    // t是纵向滑动, l是横向滑动
    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl,
                                   final int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l - oldl, t - oldt);
        }
        if (mOnScrollChangedCallback2 != null) {
            mOnScrollChangedCallback2.onScroll(l, t);
        }
    }


    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(
            final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    public OnScrollChangedCallback2 getmOnScrollChangedCallback2() {
        return mOnScrollChangedCallback2;
    }

    public void setmOnScrollChangedCallback2(OnScrollChangedCallback2 mOnScrollChangedCallback2) {
        this.mOnScrollChangedCallback2 = mOnScrollChangedCallback2;
    }

    /**
     * Impliment in the activity/fragment/view that you want to listen to the webview
     */
    public static interface OnScrollChangedCallback {
        void onScroll(int dx, int dy);
    }

    public static interface OnScrollChangedCallback2 {
        void onScroll(final int l, final int t);
    }
}
