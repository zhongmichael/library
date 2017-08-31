package com.chinaredstar.core.okhttp.callback;

import com.chinaredstar.core.okhttp.callback.tools.IGenericsTranslator;
import com.chinaredstar.core.okhttp.callback.tools.JsonTranslator;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import okhttp3.Response;


public abstract class GenericsCallback<T> extends Callback<T> {
    private IGenericsTranslator mGenericsSerializator;

    public GenericsCallback(IGenericsTranslator serializator) {
        mGenericsSerializator = serializator;
        if (null == mGenericsSerializator) {
            mGenericsSerializator = new JsonTranslator();
        }
    }

    public GenericsCallback() {
        mGenericsSerializator = new JsonTranslator();
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
