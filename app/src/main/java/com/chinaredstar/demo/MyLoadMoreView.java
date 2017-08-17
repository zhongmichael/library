package com.chinaredstar.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.chinaredstar.core.view.recyclerview.DefaultLoadMoreView;

/**
 * Created by hairui.xiang on 2017/8/16.
 */

public class MyLoadMoreView extends DefaultLoadMoreView {
    private TextView tv_state;

    public MyLoadMoreView(Context context) {
        super(context);
        init(context);
    }

    public MyLoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyLoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.footer, this);
        tv_state = findViewById(R.id.tv_state);
    }

    @Override
    public void showNoMore() {
        super.showNoMore();
        tv_state.setText(" no  more");
    }

    @Override
    public void showLoading() {
        super.showLoading();
        tv_state.setText(" loading  ");
    }

    @Override
    public void showFail() {
        super.showFail();
    }

    @Override
    public View getFooterView() {
        return this;
    }

    @Override
    public State state() {
        return super.state();
    }
}
