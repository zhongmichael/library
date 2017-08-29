package com.chinaredstar.demo;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.demo.utils.LeackTest;

/**
 * Created by hairui.xiang on 2017/8/29.
 */

public class LeackDemo extends BaseActivity {

    @Override
    protected void initWidget() {
        super.initWidget();
        LeackTest.getInstance(this).dealData();
    }
}
