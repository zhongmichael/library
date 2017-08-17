package com.chinaredstar.http.okhttp.builder;


import com.chinaredstar.http.okhttp.OkHttpUtils;
import com.chinaredstar.http.okhttp.request.OtherRequest;
import com.chinaredstar.http.okhttp.request.RequestCall;

public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
