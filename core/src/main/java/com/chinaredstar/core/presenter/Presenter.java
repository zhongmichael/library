package com.chinaredstar.core.presenter;

import android.content.Context;

/**
 * Created by hairui.xiang on 2017/8/4.
 */

public class Presenter<T> {
    protected T mvpView;
    protected Context context;

    public Presenter(T mvpView, Context context) {
        this.mvpView = mvpView;
        this.context = context;
    }
}
