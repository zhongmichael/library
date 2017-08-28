package com.chinaredstar.core.presenter;

import android.content.Context;

import com.chinaredstar.core.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;

/**
 * Created by hairui.xiang on 2017/8/4.
 */

public class Presenter<T> {
    private T mMvpView;
    private Context mContext;
    private Map<String, String> mHeaders = new HashMap<>();
    private Map<String, String> mParams = new HashMap<>();
    private String mUrl;
    private int mId = -1;
    private Object mTag;
    private Class mClazz;
    private List<PostFormBuilder.FileInput> mFiles = new ArrayList<>();
    private Object mContent;
    private MediaType mMediaType;
    private boolean postJson;
    private File mFile;

    public void setContent(Object mContent) {
        this.mContent = mContent;
    }

    public void setMediaType(MediaType mMediaType) {
        this.mMediaType = mMediaType;
    }

    public boolean isPostJson() {
        return postJson;
    }

    public void setPostJson(boolean postJson) {
        this.postJson = postJson;
    }

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

    public Object getContent() {
        return mContent;
    }

    public MediaType getMediaType() {
        return mMediaType;
    }

    public File getFile() {
        return mFile;
    }

    public void setFile(File mFile) {
        this.mFile = mFile;
    }

    public List<PostFormBuilder.FileInput> getFiles() {
        return mFiles;
    }

    public void addFile(String name, String filename, File file) {
        mFiles.add(new PostFormBuilder.FileInput(name, filename, file));
    }

    public void addParams(String key, String value) {
        mParams.put(key, value);
    }

    public void addHeaders(String key, String value) {
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
