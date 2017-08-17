package com.chinaredstar.push.utils;

import android.os.Looper;

public class JHandler {
    private static android.os.Handler mHandler = null;

    public static android.os.Handler handler() {
        if (mHandler == null) {
            synchronized (JHandler.class) {
                mHandler = new android.os.Handler(Looper.getMainLooper());
            }
        }
        return mHandler;
    }

}
