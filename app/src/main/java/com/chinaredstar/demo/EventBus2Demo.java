package com.chinaredstar.demo;

import android.view.View;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.eventbus.EventCenter;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hairui.xiang on 2017/8/31.
 */

public class EventBus2Demo extends BaseActivity {
    public void onRrefresh(View view) {
        EventBus.getDefault().post(new EventCenter<Boolean>(200, true));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.ebs_2;
    }

}
