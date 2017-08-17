package com.chinaredstar.core.view.recyclerview;

import android.view.View;

/**
 * Created by hairui.xiang on 2017/8/7.
 */

public interface ILoadMoreView {
    enum State {
        NOMORE, LOADING, FAIL
    }

    /**
     * 显示已经加载完成，没有更多数据的布局
     */
    void showNoMore();

    /**
     * 显示正在加载中的布局
     */
    void showLoading();

    /**
     * 显示加载失败的布局
     */
    void showFail();

    /**
     * 获取footerview
     *
     * @return
     */
    View getFooterView();

    State state();
}
