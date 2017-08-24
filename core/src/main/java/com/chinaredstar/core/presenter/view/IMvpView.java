package com.chinaredstar.core.presenter.view;

import java.util.List;

/**
 * Created by hairui.xiang on 2017/8/4.
 */

public interface IMvpView<T> {
    void onInitSuccess(List<T> datas);

    void onLoadSuccess(List<T> datas);

    void onRefreshSuccess(List<T> datas);

    void onInitSuccess(T data);

    void onLoadSuccess(T data);

    void onRefreshSuccess(T data);

    void onInitEmpty();

    void onLoadEmpty();

    void onRefreshEmpty();

    void onInitFailure(String code, String message);

    void onLoadFailure(String code, String message);

    void onRefreshFailure(String code, String message);

    void showLoadingDialog();

    void hideLoadingDialog();
}
