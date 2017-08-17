package com.chinaredstar.core.presenter.view;

import java.util.List;

/**
 * Created by hairui.xiang on 2017/8/4.
 */

public interface IPagerMvpView<T> {
    void onPageFirst(List<T> datas);

    void onPageNext(List<T> datas);

    void onFail();
}
