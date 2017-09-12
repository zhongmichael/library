package com.chinaredstar.core.view.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chinaredstar.core.R;

/**
 * Created by hairui.xiang on 2017/8/7.
 * 公用到底部加载view
 */

public class DefaultLoadMoreView extends BaseLoadMoreView {
    ProgressBar pb_loading;
    TextView tv_loading_status;

    @Override
    protected int getResourceId() {
        return R.layout.libbase_loadmore_footer;
    }

    @Override
    protected void initView() {
        pb_loading = findViewById(R.id.pb_loading);
        tv_loading_status = findViewById(R.id.tv_loading_status);
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
        pb_loading.setVisibility(View.GONE);
        tv_loading_status.setText(getContext().getResources().getString(R.string.libbase_loaded_finished));
    }

    @Override
    public void showLoading() {
        pb_loading.setVisibility(View.VISIBLE);
        tv_loading_status.setText(getContext().getResources().getString(R.string.libbase_loading));
    }
}
