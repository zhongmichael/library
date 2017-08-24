package com.chinaredstar.core.presenter;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hairui.xiang on 2017/8/4.
 */

public class Presenter<T> {
    private T mMvpView;
    private Context mContext;
    private Map<String, String> mHeaders;
    private Map<String, String> mParams;
    private String mUrl;
    private int mId;
    private Object mTag;
    private Class mClazz;


    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public void setTag(Object mTag) {
        this.mTag = mTag;
    }

    public void setClazz(Class mClazz) {
        this.mClazz = mClazz;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    public String getUrl() {
        return mUrl;
    }

    public int getId() {
        return mId;
    }

    public Object getTag() {
        return mTag;
    }

    public Class getClazz() {
        return mClazz;
    }

    public void addParams(String key, String value) {
        if (null == mParams) {
            mParams = new HashMap<>();
        }
        mParams.put(key, value);
    }

    public void addHeaders(String key, String value) {
        if (null == mHeaders) {
            mHeaders = new HashMap<>();
        }
        mHeaders.put(key, value);
    }


    public Presenter(T mvpView, Context context) {
        this.mMvpView = mvpView;
        this.mContext = context;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public Context getContext() {
        return mContext;
    }
}
