package com.chinaredstar.core.okhttp.callback;

import com.chinaredstar.core.okhttp.callback.tools.IGenericsTranslator;
import com.chinaredstar.core.okhttp.callback.tools.JsonTranslator;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import okhttp3.Response;


public abstract class GenericsListCallback<T> extends Callback<List<T>> {
    private IGenericsTranslator mGenericsSerializator;

    public GenericsListCallback(IGenericsTranslator serializator) {
        mGenericsSerializator = serializator;
    }

    public GenericsListCallback() {
        mGenericsSerializator = new JsonTranslator();
    }

    @Override
    public List<T> parseNetworkResponse(Response response, int id) throws IOException {
        String string = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return mGenericsSerializator.transformListT(string, entityClass);
    }
}
