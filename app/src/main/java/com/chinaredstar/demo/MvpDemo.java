package com.chinaredstar.demo;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.presenter.view.MvpView;
import com.chinaredstar.demo.bean.User;
import com.chinaredstar.demo.persenter.UserPersenter;

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
        UserPersenter mUserPersenter = new UserPersenter(new MvpView<User>() {
            @Override
            public void onInitSuccess(User data) {
                super.onInitSuccess(data);
            }
        }, this);
        mUserPersenter.setUrl("");
        mUserPersenter.addParams("", "");
        mUserPersenter.loadData();
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
