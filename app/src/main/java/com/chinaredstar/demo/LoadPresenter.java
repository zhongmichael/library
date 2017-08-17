package com.chinaredstar.demo;

import android.content.Context;

import com.chinaredstar.core.presenter.LoadDataPresenter;
import com.chinaredstar.core.presenter.view.ILoadDataMvpView;

/**
 * Created by hairui.xiang on 2017/8/4.
 */

public class LoadPresenter extends LoadDataPresenter<User> {
    public LoadPresenter(ILoadDataMvpView<User> mvpView, Context context) {
        super(mvpView, context);
    }

    @Override
    protected void load() {

    }
}
