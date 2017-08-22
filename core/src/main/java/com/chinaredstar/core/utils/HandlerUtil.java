package com.chinaredstar.core.utils;

import android.os.Looper;

/**
 * Created by hairui.xiang on 2017/8/17.
 */

public class HandlerUtil {
    private static android.os.Handler mHandler = null;

    public static android.os.Handler handler() {
        if (mHandler == null) {
            synchronized (HandlerUtil.class) {
                mHandler = new android.os.Handler(Looper.getMainLooper());
            }
        }
        return mHandler;
    }

}
