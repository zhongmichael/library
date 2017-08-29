package com.chinaredstar.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.chinaredstar.core.view.recyclerview.BaseLoadMoreView;
import com.chinaredstar.demo.R;

/**
 * Created by hairui.xiang on 2017/8/16.
 */

public class MyLoadMoreView extends BaseLoadMoreView {
    private TextView tv_state;

    @Override
    protected int getResourceId() {
        return R.layout.footer;
    }

    @Override
    protected void initView() {
        tv_state = findViewById(R.id.tv_state);
    }

    public MyLoadMoreView(Context context) {
        super(context);
    }

    public MyLoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
}
