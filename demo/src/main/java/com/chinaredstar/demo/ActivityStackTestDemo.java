package com.chinaredstar.demo;

import android.content.Intent;
import android.view.View;

import com.chinaredstar.core.base.BaseActivity;

/**
 * Created by hairui.xiang on 2017/8/31.
 */

public class ActivityStackTestDemo extends BaseActivity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.layout;
    }

    public void onNext(View view) {
        startActivity(new Intent(this, SqlSSCacheDemo.class));
    }
}
