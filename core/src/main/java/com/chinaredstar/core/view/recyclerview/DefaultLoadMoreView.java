package com.chinaredstar.core.view.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import com.chinaredstar.core.R;

/**
 * Created by hairui.xiang on 2017/8/7.
 * 公用到底部加载view
 */

public class DefaultLoadMoreView extends BaseLoadMoreView {


    @Override
    protected int getResourceId() {
        return R.layout.libbase_loadmore_footer;
    }

    @Override
    protected void initView() {
    }

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
    }

    @Override
    public void showLoading() {
    }
}
