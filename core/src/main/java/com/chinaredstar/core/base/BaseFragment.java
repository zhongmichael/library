package com.chinaredstar.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hairui.xiang on 2017/8/22.
 */

public abstract class BaseFragment extends Fragment {
    private View mRootView;
    private boolean isOnCreated;
    private boolean isVisible;
    private boolean isLoaded;

    protected abstract int getLayoutResID();

    protected void initWidget() {
    }

    protected void initValue() {
    }

    /**
     * 1，ViewPager + Fragment  懒加载
     * ,2， hide，show framgent  懒加载
     */
    private void lazyinit() {
        if (!isOnCreated || !isVisible) {
            return;
        }
        if (isLoaded) {
            return;
        }
        //因为fragment每次显示到屏幕上时，都会调用initData方法，isLoaded字段为了只调用一次initData方法
        isLoaded = true;
        initData();
    }

    /**
     * 此方法只会在fragment显示到屏幕时才会调用
     **/
    protected void initData() {

    }

    protected void initListener() {

    }

    /**
     * 默认关闭懒加载
     * 重写该方法，return true 开启懒加载
     */
    protected boolean openLazyinit() {
        return false;
    }

    /**
     * 根据id查找view
     *
     * @param res
     * @return
     */
    public <T extends View> T findViewById(int res) {
        if (null != mRootView) {
            return mRootView.findViewById(res);
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mRootView) {
            mRootView = inflater.inflate(getLayoutResID(), container, false);
        }
        ViewGroup mViewGroup = (ViewGroup) mRootView.getParent();
        if (null != mViewGroup) {
            mViewGroup.removeView(mRootView);
        }
        isOnCreated = true;
        initValue();
        initWidget();
        initListener();
        if (openLazyinit()) {
            lazyinit();
        } else {
            initData();
        }
        return mRootView;
    }

    /**
     * ViewPager + Fragment
     * 切换页面时会回调
     **/
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = getUserVisibleHint();
        if (isVisible) {
            lazyinit();
        }
    }

    /**
     * add多个fragment到同一个layout id的时，
     * 调用hide和show时回调
     **/
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisible = !hidden;
        if (isVisible) {
            lazyinit();
        }
    }
}
