package com.chinaredstar.core.view.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by hairui.xiang on 2017/8/4.
 */

public class PulToRefreshLayout extends Issues282PtrFrameLayout {

    public PulToRefreshLayout(Context context) {
        super(context);
        init();
    }

    public PulToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PulToRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // the following are default settings
        this.setResistance(1.7f);
        this.setRatioOfHeaderHeightToRefresh(1.2f);
        this.setDurationToClose(200);
        this.setDurationToCloseHeader(1000);
        // default is false
        this.setPullToRefresh(false);
        // default is true
        this.setKeepHeaderWhenRefresh(true);
        // 这里初始化上面的头View：
        //
        // 这里设置头View为上面自定义的头View：
//        this.setHeaderView();
        // 下拉和刷新状态监听：
        // 因为ParallaxHeader已经实现过PtrUIHandler接口，所以直接设置为ParallaxHeader：
//        this.addPtrUIHandler();
    }
}
