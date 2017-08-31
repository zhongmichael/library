package com.chinaredstar.core.okhttp.callback.tools;

import com.chinaredstar.core.utils.JsonUtil;

import java.util.List;

/**
 * Created by hairui.xiang on 2017/8/2.
 */

public class JsonParser implements IGenericsParser {

    @Override
    public <T> T transformT(String response, Class<T> classOfT) {
        return JsonUtil.parse(response, classOfT);
    }

    @Override
    public <T> List<T> transformListT(String response, Class<T> classOfT) {
        return JsonUtil.parseList(response, classOfT);
    }
}
