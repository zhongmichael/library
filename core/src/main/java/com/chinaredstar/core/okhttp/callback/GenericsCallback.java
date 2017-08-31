package com.chinaredstar.core.okhttp.callback;

import com.chinaredstar.core.okhttp.callback.tools.IGenericsParser;
import com.chinaredstar.core.okhttp.callback.tools.JsonParser;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import okhttp3.Response;


public abstract class GenericsCallback<T> extends Callback<T> {
    private IGenericsParser mGenericsSerializator;

    public GenericsCallback(IGenericsParser serializator) {
        mGenericsSerializator = serializator;
        if (null == mGenericsSerializator) {
            mGenericsSerializator = new JsonParser();
        }
    }

    public GenericsCallback() {
        mGenericsSerializator = new JsonParser();
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {
        String string = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (entityClass == String.class) {
            return (T) string;
        }
        return mGenericsSerializator.transformT(string, entityClass);
    }
}
