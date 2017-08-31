package com.chinaredstar.core.eventbus;

import com.chinaredstar.core.base.BaseBean;

/**
 * Created by hairui.xiang on 2017/8/31.
 */

public class EventCenter<T> extends BaseBean {
    public int code;
    public T data;

    public EventCenter() {
    }

    public EventCenter(int code, T data) {
        this.code = code;
        this.data = data;
    }
}
