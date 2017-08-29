package com.chinaredstar.demo.persenter;

import android.content.Context;

import com.chinaredstar.core.okhttp.callback.GenericsCallback;
import com.chinaredstar.core.okhttp.callback.JsonGenericsSerializator;
import com.chinaredstar.core.presenter.BasePresenter;
import com.chinaredstar.core.presenter.view.IMvpView;
import com.chinaredstar.demo.bean.User;

import okhttp3.Call;

/**
 * Created by hairui.xiang on 2017/8/29.
 */

public class UserPersenter extends BasePresenter<User> {
    public UserPersenter(IMvpView<User> mvpView, Context context) {
        super(mvpView, context);
    }

    public void loadData() {
        post().execute(new GenericsCallback<User>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(User response, int id) {

            }
        });
    }

}
