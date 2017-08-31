package com.chinaredstar.core.okhttp.callback;

import com.chinaredstar.core.okhttp.callback.tools.IGenericsParser;
import com.chinaredstar.core.okhttp.callback.tools.JsonParser;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import okhttp3.Response;


public abstract class GenericsListCallback<T> extends Callback<List<T>> {
    private IGenericsParser mGenericsSerializator;

    public GenericsListCallback(IGenericsParser serializator) {
        mGenericsSerializator = serializator;
        if (null == mGenericsSerializator) {
            mGenericsSerializator = new JsonParser();
        }
    }

    public GenericsListCallback() {
        mGenericsSerializator = new JsonParser();
    }

    @Override
    public List<T> parseNetworkResponse(Response response, int id) throws IOException {
        String string = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return mGenericsSerializator.transformListT(string, entityClass);
    }
}
