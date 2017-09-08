package com.chinaredstar.demo.presenter.view;

import com.chinaredstar.core.presenter.view.IMvpView;
import com.chinaredstar.demo.bean.Weatherinfo;

/**
 * Created by hairui.xiang on 2017/8/31.
 */

public interface WeatherView extends IMvpView{
    void getWeather(Weatherinfo weatherinfo);
}
