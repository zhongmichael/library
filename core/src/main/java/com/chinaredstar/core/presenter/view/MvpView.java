package com.chinaredstar.core.presenter.view;

import java.util.List;

/**
 * Created by hairui.xiang on 2017/8/24.
 */

public class MvpView<T> implements IMvpView<T> {
    @Override
    public void onInitSuccess(List<T> datas) {

    }

    @Override
    public void onLoadSuccess(List<T> datas) {

    }

    @Override
    public void onRefreshSuccess(List<T> datas) {

    }

    @Override
    public void onInitSuccess(T data) {

    }

    @Override
    public void onLoadSuccess(T data) {

    }

    @Override
    public void onRefreshSuccess(T data) {

    }

    @Override
    public void onInitEmpty() {

    }

    @Override
    public void onLoadEmpty() {

    }

    @Override
    public void onRefreshEmpty() {

    }

    @Override
    public void onInitFailure(String code, String message) {

    }

    @Override
    public void onLoadFailure(String code, String message) {

    }

    @Override
    public void onRefreshFailure(String code, String message) {

    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void hideLoadingDialog() {

    }
}
