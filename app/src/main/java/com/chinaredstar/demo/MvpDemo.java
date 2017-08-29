package com.chinaredstar.demo;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.presenter.BasePresenter;
import com.chinaredstar.core.presenter.view.MvpView;

/**
 * Created by hairui.xiang on 2017/8/29.
 */

public class MvpDemo extends BaseActivity {
    @Override
    protected boolean retainStatusBarHeight() {
        return super.retainStatusBarHeight();
    }

    @Override
    protected void setStatusBarBackgroundColor(int color) {
        super.setStatusBarBackgroundColor(color);
    }

    @Override
    protected int getHeaderLayoutId() {
        return super.getHeaderLayoutId();
    }

    @Override
    protected int getContentLayoutId() {
        return super.getContentLayoutId();
    }

    @Override
    protected void initValue() {
        super.initValue();
        BasePresenter<User> mBasePresenter = new BasePresenter<User>(new MvpView<User>(){
            @Override
            public void onInitSuccess(User data) {
                super.onInitSuccess(data);
            }

            @Override
            public void onLoadSuccess(User data) {
                super.onLoadSuccess(data);
            }

            @Override
            public void onRefreshSuccess(User data) {
                super.onRefreshSuccess(data);
            }

            @Override
            public void showLoadingDialog() {
                super.showLoadingDialog();
            }

            @Override
            public void hideLoadingDialog() {
                super.hideLoadingDialog();
            }
        },this);
        mBasePresenter.setUrl("");
        mBasePresenter.addParams("","");
    }

    @Override
    protected void initWidget() {
        super.initWidget();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
