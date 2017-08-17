package com.chinaredstar.core.okhttp.callback;

import com.chinaredstar.core.utils.JsonUtil;

import java.util.List;

import okhttp3.Response;

/**
 * Created by hairui.xiang on 2017/8/17.
 */

public abstract class ListCallback<T> extends Callback<List<T>> {
    private Class clazz;

    public ListCallback(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<T> parseNetworkResponse(Response response, int id) throws Exception {
        String jsonString = response.body().string();
        return JsonUtil.parseList(jsonString, clazz);
    }
}
