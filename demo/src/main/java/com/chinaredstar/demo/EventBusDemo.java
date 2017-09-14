package com.chinaredstar.demo;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.eventbus.EventCenter;

/**
 * Created by hairui.xiang on 2017/8/31.
 */

public class EventBusDemo extends BaseActivity {
    TextView tv_content;

    @Override
    protected void initWidget() {
        super.initWidget();
        tv_content = findViewById(R.id.tv_content);
    }

    public void onGo(View view) {
        startActivity(new Intent(this, EventBus2Demo.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.ebs_1;
    }

    @Override
    protected boolean enabledEventBus() {
        return true;
    }

    @Override
    protected void onEventCallback(EventCenter event) {
        if (event.code == 200) {
            tv_content.setText("refresh");
            tv_content.setBackgroundColor(Color.RED);
        }
    }
}
