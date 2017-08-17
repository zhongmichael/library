package com.chinaredstar.core.base;

import com.chinaredstar.core.utils.JsonUtil;

import java.io.Serializable;

/**
 * Created by hairui.xiang on 2017/8/2.
 */

public class BaseBean implements Serializable {

    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }
}
