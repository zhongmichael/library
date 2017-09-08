package com.chinaredstar.demo;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.demo.bean.Weatherinfo;
import com.chinaredstar.demo.presenter.WeatherPresenter;
import com.chinaredstar.demo.presenter.view.WeatherView;

/**
 * Created by hairui.xiang on 2017/8/30.
 */

public class NetRequestJsonParseDemo extends BaseActivity implements WeatherView {
    final WeatherPresenter mWeatherPresenter = new WeatherPresenter(this, this);

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_empty;
    }

    @Override
    protected void initValue() {
        mWeatherPresenter.setUrl("http://www.weather.com.cn/data/cityinfo/101010100.html");
    }

    @Override
    protected void initData() {
        super.initData();
        mWeatherPresenter.getWeather();
    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void hideLoadingDialog() {

    }

    @Override
    public void getWeather(Weatherinfo weatherinfo) {

    }
}
