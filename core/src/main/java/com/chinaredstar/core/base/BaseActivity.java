package com.chinaredstar.core.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.chinaredstar.core.R;
import com.chinaredstar.core.utils.ActivityStack;
import com.chinaredstar.core.utils.NetworkUtil;
import com.chinaredstar.core.utils.StatusBarUtil;

import static com.chinaredstar.core.utils.NetworkUtil.NETWORK_CHANGE_ACTION;

/**
 * Created by hairui.xiang on 2017/8/1.
 */

public class BaseActivity extends PermissionsActivity {
    private Handler mHandler;
    private View mStatusBar;
    private LinearLayout mRootView;
    private ViewGroup mHeaderView;
    private ViewGroup mContentView;
    private LayoutInflater mLayoutInflater;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.pop(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.push(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //此FLAG可使状态栏透明，且当前视图在绘制时，从屏幕顶端开始即top = 0开始绘制，这也是实现沉浸效果的基础
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        this.setContentView(R.layout.activity_libbase_layout);
        this.mStatusBar = findViewById(R.id.id_statusbar_view);
        this.mRootView = findViewById(R.id.id_root_view);
        if (retainStatusBarHeight()) {
            StatusBarUtil.setImmersiveStatusBar(mStatusBar, this);
        }
        if (getHeaderLayoutId() > -1) {
            this.mHeaderView = (ViewGroup) this.getInflater().inflate(this.getHeaderLayoutId(), null);
            this.mRootView.addView(this.mHeaderView, -1, -2);
        }
        if (this.getContentLayoutId() > -1) {
            this.mContentView = (ViewGroup) this.getInflater().inflate(this.getContentLayoutId(), null);
            this.mRootView.addView(this.mContentView, -1, -1);
        }
        if (!NetworkUtil.isNetworkAvailable(this)) {
            onNetworkInvalid();
        }
        this.initValue();
        this.initWidget();
        this.initListener();
        this.initData();
    }

    protected LayoutInflater getInflater() {
        if (null == this.mLayoutInflater) {
            this.mLayoutInflater = LayoutInflater.from(this);
        }
        return this.mLayoutInflater;
    }

    /**
     * 保留状态栏高度
     *
     * @return <li>true 创建一个和状态栏高度一样的view</li>
     * <li>false 页面扑满屏幕</li>
     */
    protected boolean retainStatusBarHeight() {
        return true;
    }

    protected void setStatusBarBackgroundColor(int color) {
        this.mStatusBar.setBackgroundColor(color);
    }

    protected int getHeaderLayoutId() {
        return -1;
    }

    protected int getContentLayoutId() {
        return -1;
    }

    protected void initValue() {
    }

    protected void initWidget() {
    }

    protected void initListener() {
    }

    protected void initData() {
    }

    protected static void onNetworkInvalid() {
    }

    protected static void onNetworkAvailable() {
    }

    public Handler getHandler() {
        if (null == this.mHandler) {
            this.mHandler = new Handler(this.getMainLooper());
        }
        return this.mHandler;
    }

    private final static BroadcastReceiver mNetworkMonitorReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (NETWORK_CHANGE_ACTION.equals(intent.getAction())) {
                if (!NetworkUtil.isNetworkAvailable(context)) { // network invalid
                    onNetworkInvalid();
                } else if (NetworkUtil.isConnectedWifi(context)) { // valid wifi
                    onNetworkAvailable();
                } else if (NetworkUtil.isConnectedMobile(context)) {// valid mobile
                    onNetworkAvailable();
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mNetworkMonitorReceiver, NetworkUtil.getNetworkChangeFilter());
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mNetworkMonitorReceiver);
    }
}
