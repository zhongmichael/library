package com.chinaredstar.demo;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.eventbus.EventCenter;
import com.chinaredstar.core.task.CalculateCacheTask;
import com.chinaredstar.core.task.ClearCacheTask;
import com.chinaredstar.core.task.core.TaskResult;

import static com.chinaredstar.core.constant.EC.EC_CALCULATE_CACHE_SIZE;
import static com.chinaredstar.core.constant.EC.EC_CLEAR_CACHE;

/**
 * Created by hairui.xiang on 2017/9/14.
 */

public class TaskMangerDemo extends BaseActivity {
    TextView tv_show;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_taskdemo;
    }

    @Override
    protected boolean enabledEventBus() {
        return true;
    }

    @Override
    protected void onEventCallback(EventCenter event) {
        switch (event.code) {
            case EC_CALCULATE_CACHE_SIZE:
                tv_show.setText(((TaskResult) event.data).obj.toString());
                break;
            case EC_CLEAR_CACHE:
                Toast.makeText(mActivity, "清除完成", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tv_show = (TextView) findViewById(R.id.tv_show);
    }

    public void getCacheSize(View v) {
        execTask(new CalculateCacheTask(EC_CALCULATE_CACHE_SIZE));
    }

    public void clearCache(View v) {
        execTask(new ClearCacheTask(EC_CLEAR_CACHE));
    }
}
