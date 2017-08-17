package com.chinaredstar.core.view.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by hairui.xiang on 2017/8/7.
 * 公用到底部加载view
 */

public class DefaultLoadMoreView extends RelativeLayout implements ILoadMoreView {
    State mState;
    public DefaultLoadMoreView(Context context) {
        super(context);
    }

    public DefaultLoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultLoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void showNoMore() {
        mState = State.NOMORE;
    }

    @Override
    public void showLoading() {
        mState = State.LOADING;
    }

    @Override
    public void showFail() {
        mState = State.FAIL;
    }

    @Override
    public View getFooterView() {
        return null;
    }

    @Override
    public State state() {
        return mState;
    }
}
