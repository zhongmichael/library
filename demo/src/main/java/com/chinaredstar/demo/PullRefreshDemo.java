package com.chinaredstar.demo;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.view.pulltorefresh.PulToRefreshLayout;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by hairui.xiang on 2017/9/8.
 */

public class PullRefreshDemo extends BaseActivity {
    PulToRefreshLayout mPtrLayout;

    @Override
    protected void initListener() {
        mPtrLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                System.out.println("---PullRefreshDemo---onRefreshBegin--");
                getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrLayout.refreshComplete();
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void initWidget() {
        mPtrLayout = findViewById(R.id.ptr_view);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_pullfresh;
    }
}
