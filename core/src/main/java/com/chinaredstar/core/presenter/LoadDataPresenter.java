package com.chinaredstar.core.presenter;

import android.content.Context;

import com.chinaredstar.core.presenter.view.ILoadDataMvpView;

/**
 * Created by hairui.xiang on 2017/8/4.
 */

public abstract class LoadDataPresenter<T> extends Presenter<ILoadDataMvpView<T>> {

    public LoadDataPresenter(ILoadDataMvpView<T> mvpView, Context context) {
        super(mvpView, context);
    }

    protected abstract void load();
}
