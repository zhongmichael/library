package com.chinaredstar.core.presenter.view;

/**
 * Created by hairui.xiang on 2017/8/4.
 */

public interface ILoadDataMvpView<T> {
    void loadSuccess(T data);

    void loadFail(String code, String msg);
}

