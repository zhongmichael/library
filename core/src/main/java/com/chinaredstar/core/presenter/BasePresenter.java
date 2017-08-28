package com.chinaredstar.core.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.chinaredstar.core.okhttp.OkHttpUtils;
import com.chinaredstar.core.okhttp.builder.GetBuilder;
import com.chinaredstar.core.okhttp.builder.PostFileBuilder;
import com.chinaredstar.core.okhttp.builder.PostFormBuilder;
import com.chinaredstar.core.okhttp.builder.PostStringBuilder;
import com.chinaredstar.core.okhttp.request.RequestCall;
import com.chinaredstar.core.presenter.view.IMvpView;
import com.chinaredstar.core.utils.JsonUtil;

import okhttp3.MediaType;

/**
 * Created by hairui.xiang on 2017/8/24.
 */

public class BasePresenter<T> extends Presenter<IMvpView<T>> {

    public BasePresenter(IMvpView<T> mvpView, Context context) {
        super(mvpView, context);
    }

    /**
     */
    protected RequestCall post() {
        PostFormBuilder builder = OkHttpUtils.post();
        if (-1 != getId()) {
            builder.id(getId());
        }
        if (null != getTag()) {
            builder.tag(getTag());
        }
        if (!TextUtils.isEmpty(getUrl())) {
            builder.url(getUrl());
        }
        if (getHeaders().size() > 0) {
            builder.headers(getHeaders());
        }
        if (getParams().size() > 0) {
            builder.params(getParams());
        }
        if (getFiles().size() > 0) {
            builder.files(getFiles());
        }
        return builder.build();
    }

    /**
     */
    protected RequestCall get() {
        GetBuilder builder = OkHttpUtils.get();
        if (-1 != getId()) {
            builder.id(getId());
        }
        if (null != getTag()) {
            builder.tag(getTag());
        }
        if (!TextUtils.isEmpty(getUrl())) {
            builder.url(getUrl());
        }
        if (getHeaders().size() > 0) {
            builder.headers(getHeaders());
        }
        if (getParams().size() > 0) {
            builder.params(getParams());
        }
        return builder.build();
    }

    /**
     * application/json
     */
    protected RequestCall postString() {
        PostStringBuilder builder = OkHttpUtils.postString();
        if (-1 != getId()) {
            builder.id(getId());
        }
        if (null != getTag()) {
            builder.tag(getTag());
        }
        if (!TextUtils.isEmpty(getUrl())) {
            builder.url(getUrl());
        }
        if (getHeaders().size() > 0) {
            builder.headers(getHeaders());
        }
        if (null != getContent()) {//
            if (isPostJson()) {
                builder.content(JsonUtil.toJsonString(getContent()));
            } else {
                builder.content(getContent().toString());
            }
        }
        if (isPostJson()) {
            builder.mediaType(MediaType.parse("application/json; charset=utf-8"));
        } else {
            if (null != getMediaType()) {
                builder.mediaType(getMediaType());
            }
        }
        return builder.build();
    }

    /**
     */
    protected RequestCall postFile() {
        PostFileBuilder builder = OkHttpUtils.postFile();
        if (-1 != getId()) {
            builder.id(getId());
        }
        if (null != getTag()) {
            builder.tag(getTag());
        }
        if (!TextUtils.isEmpty(getUrl())) {
            builder.url(getUrl());
        }
        if (getHeaders().size() > 0) {
            builder.headers(getHeaders());
        }
        if (null != getFile()) {
            builder.file(getFile());
        }
        if (null != getMediaType()) {//default value:MediaType.parse("application/octet-stream")
            builder.mediaType(getMediaType());
        }
        return builder.build();
    }
}
