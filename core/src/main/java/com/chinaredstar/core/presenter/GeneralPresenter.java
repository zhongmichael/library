package com.chinaredstar.core.presenter;

import android.content.Context;

import com.chinaredstar.core.presenter.view.IMvpView;

/**
 * Created by hairui.xiang on 2017/8/24.
 */

public class GeneralPresenter<T> extends Presenter<IMvpView<T>> {

    public GeneralPresenter(IMvpView<T> mvpView, Context context) {
        super(mvpView, context);
    }
}
