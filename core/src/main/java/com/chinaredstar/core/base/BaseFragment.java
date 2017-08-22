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
    private View mFragmentView;
    private boolean isLoading;
    private boolean isVisible;

    protected abstract int getLayoutResID();

    protected void initWidget() {
    }

    protected void initValue() {
    }

    private void lazyinit() {
        if (!isLoading || !isVisible) {
            return;
        }
        initData();
    }

    protected void initData() {

    }

    protected void initListener() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mFragmentView) {
            mFragmentView = inflater.inflate(getLayoutResID(), container, false);
        }
        ViewGroup mViewGroup = (ViewGroup) mFragmentView.getParent();
        if (null != mViewGroup) {
            mViewGroup.removeView(mFragmentView);
        }
        isLoading = true;
        initValue();
        initWidget();
        initListener();
        lazyinit();
        return mFragmentView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = getUserVisibleHint();
        if (isVisible) {
            lazyinit();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
