package com.chinaredstar.core.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.chinaredstar.core.R;
import com.chinaredstar.core.eventbus.EventCenter;
import com.chinaredstar.core.task.core.ITask;
import com.chinaredstar.core.task.core.TaskManager;
import com.chinaredstar.core.utils.ActivityStack;
import com.chinaredstar.core.utils.HandlerUtil;
import com.chinaredstar.core.utils.NetworkUtil;
import com.chinaredstar.core.utils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.chinaredstar.core.utils.NetworkUtil.NETWORK_CHANGE_ACTION;

/**
 * Created by hairui.xiang on 2017/8/1.
 */

public class BaseActivity extends PermissionsActivity {
    protected Activity mActivity;
    private View mStatusBar;
    private LinearLayout mRootView;
    private View mHeaderView;
    private View mContentView;
    private FrameLayout mLoadingViewContainer;
    private LayoutInflater mLayoutInflater;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.pop(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.push(this);
        if (enabledEventBus()) {
            EventBus.getDefault().register(this);
        }
        mActivity = this;
        mLayoutInflater = LayoutInflater.from(this);
        setContentView(R.layout.activity_libbase_layout);
        mStatusBar = findViewById(R.id.id_statusbar_view);
        mRootView = findViewById(R.id.id_root_view);

        mLoadingViewContainer = (FrameLayout) inflate(R.layout.libbase_content_layout);

        if (getHeaderLayoutId() > -1) {
            mHeaderView = inflate(getHeaderLayoutId());
        } else if (null != getHeaderLayoutView()) {
            mHeaderView = getHeaderLayoutView();
        }
        if (getContentLayoutId() > -1) {
            mContentView = inflate(getContentLayoutId());
        } else if (null != getContentLayoutView()) {
            mContentView = getContentLayoutView();
        }
        if (null != mContentView) {
            mLoadingViewContainer.addView(mContentView);
        }
        if (null != mHeaderView) {
            mRootView.addView(mHeaderView, -1, -2);
        }

        mRootView.addView(this.mLoadingViewContainer, -1, -1);

        if (enabledImmersiveStyle()) {
            initImmersiveStyle();
        }
        if (!NetworkUtil.isNetworkAvailable(this)) {
            onNetworkInvalid();
        }
        this.initValue();
        this.initWidget();
        this.initListener();
        this.initData();
    }

    protected final View inflate(int resLayoutId) {
        return getInflater().inflate(resLayoutId, null);
    }

    protected final LayoutInflater getInflater() {
        return mLayoutInflater;
    }

    /**
     * 只有当 enabledImmersiveStyle( ) 方法 return true 时有效
     * <p>
     * 保留状态栏高度
     *
     * @return <li>true 创建一个和状态栏高度一样的view</li>
     * <li>false 页面扑满屏幕</li>
     */
    protected boolean retainStatusBarHeight() {
        return true;
    }

    /**
     * 状态栏颜色
     */
    protected int getStatusBarBackgroundColor() {
        return R.color.colorPrimary;
    }

    /**
     * 标题栏颜色
     */
    protected int getTitlebarBackgroundColor() {
        return R.color.colorPrimary;
    }

    private void initImmersiveStyle() {
        if (null != this.mStatusBar && retainStatusBarHeight()) {
            this.mStatusBar.setBackgroundResource(getStatusBarBackgroundColor());
            StatusBarUtil.setImmersiveStatusBar(mStatusBar, this);
        }
        if (null != this.mHeaderView) {
            this.mHeaderView.setBackgroundResource(getTitlebarBackgroundColor());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //此FLAG可使状态栏透明，且当前视图在绘制时，从屏幕顶端开始即top = 0开始绘制，这也是实现沉浸效果的基础
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 沉浸式
     *
     * @return true 沉浸式 ，false 普通模式
     */
    protected boolean enabledImmersiveStyle() {
        return true;
    }

    protected int getHeaderLayoutId() {
        return R.layout.libbase_header_layout;
    }

    protected View getHeaderLayoutView() {
        return null;
    }

    protected int getContentLayoutId() {
        return -1;
    }

    protected View getContentLayoutView() {
        return null;
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

    public final Handler getHandler() {
        return HandlerUtil.handler();
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

    /**
     * POSTING, 该事件在哪个线程发布出来的，就会在这个线程中运行，也就是说发布事件和接收事件线程在同一个线程。
     * 使用这个方法时，不能执行耗时操作，如果执行耗时操作容易导致事件分发延迟。
     * <p>
     * MAIN, 不论事件是在哪个线程中发布出来的，都会在UI线程中执行，接收事件就会在UI线程中运行，所以在方法中是不能执行耗时操作的。
     * <p>
     * BACKGROUND, 如果事件是在UI线程中发布出来的，会在子线程中运行，如果事件本来就是子线程中发布出来的，那么函数直接在该子线程中执行。
     * <p>
     * ASYNC 无论事件在哪个线程发布，都会创建新的子线程在执行
     */
    @Subscribe(threadMode = ThreadMode.MAIN)//, priority = 100
    public final void onEventCenter(EventCenter event) {
        if (null != event) {
            onEventCallback(event);
        }
    }

    /**
     * 根据code区分当前事件类型
     */
    protected void onEventCallback(EventCenter event) {
        // handle event
    }

    /**
     * 注册event bus
     *
     * @return true 自动注册eventbus
     */
    protected boolean enabledEventBus() {
        return false;
    }

    /**
     * 执行异步任务
     * <p>
     * 执行结果将在onEventCallback(EventCenter event)方法里返回，task的id 相当于event的code
     *
     * @param task 计算缓存大小   {@link com.chinaredstar.core.task.CalculateCacheTask }
     *             清除缓存       {@link com.chinaredstar.core.task.ClearCacheTask}
     *             压缩图片       {@link com.chinaredstar.core.task.CompressImageTask}
     */
    protected final void execTask(ITask task) {
        if (null != task) {
            TaskManager.getInstance().excute(task);
        }
    }

    /**
     * 页面内加载框
     */
    protected View getLoadingView() {
        return null;
    }

    protected boolean isShowingLoadingView() {
        return null != getLoadingView() && mLoadingViewContainer.indexOfChild(getLoadingView()) >= 0;
    }

    protected void showLoading() {
        if (!isShowingLoadingView()) {
            mLoadingViewContainer.addView(getLoadingView());
        }
    }

    protected void hideLoading() {
        if (isShowingLoadingView()) {
            mLoadingViewContainer.removeView(getLoadingView());
        }
    }
}
