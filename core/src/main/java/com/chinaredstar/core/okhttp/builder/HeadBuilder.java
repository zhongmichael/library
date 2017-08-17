package com.chinaredstar.core.okhttp.builder;


import com.chinaredstar.core.okhttp.OkHttpUtils;
import com.chinaredstar.core.okhttp.request.OtherRequest;
import com.chinaredstar.core.okhttp.request.RequestCall;

public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
