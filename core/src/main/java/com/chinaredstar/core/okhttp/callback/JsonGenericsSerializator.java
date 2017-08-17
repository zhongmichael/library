package com.chinaredstar.core.okhttp.callback;

import com.chinaredstar.core.utils.JsonUtil;

/**
 * Created by hairui.xiang on 2017/8/2.
 */

public class JsonGenericsSerializator implements IGenericsSerializator {
    @Override
    public <T> T transform(String response, Class<T> classOfT) {
        return JsonUtil.parse(response, classOfT);
    }
}
