package com.chinaredstar.core.view.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by hairui.xiang on 2017/8/7.
 */

public abstract class BaseLoadMoreView extends RelativeLayout implements ILoadMoreView {
    private State mState;

    /**
     * get layout id
     */
    protected abstract int getResourceId();

    /**
     * init view
     */
    protected abstract void initView();

    public BaseLoadMoreView(Context context) {
        super(context);
        init(context);
    }

    public BaseLoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseLoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, getResourceId(), this);
        initView();
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
        return this;
    }

    @Override
    public State state() {
        return mState;
    }
}
