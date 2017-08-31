package com.chinaredstar.demo.presenter;

import android.content.Context;

import com.chinaredstar.core.okhttp.callback.GenericsCallback;
import com.chinaredstar.core.presenter.BasePresenter;
import com.chinaredstar.demo.bean.Weatherinfo;
import com.chinaredstar.demo.presenter.view.WeatherView;

import okhttp3.Call;

/**
 * Created by hairui.xiang on 2017/8/31.
 */

public class WeatherPresenter extends BasePresenter<WeatherView> {
    public WeatherPresenter(WeatherView mvpView, Context context) {
        super(mvpView, context);
    }

    public void getWeather() {
        getMvpView().showLoadingDialog();

        get().execute(new GenericsCallback<Weatherinfo>() {
            @Override
            public void onError(Call call, Exception e, int id) {

                getMvpView().hideLoadingDialog();

            }

            @Override
            public void onResponse(Weatherinfo response, int id) {

                getMvpView().hideLoadingDialog();

                System.out.println("callback response: " + response);
            }
        });
    }
}