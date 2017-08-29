package com.chinaredstar.demo.utils;

import android.content.Context;

/**
 * Created by hairui.xiang on 2017/8/29.
 */

public class LeackTest {
    private static LeackTest mInstance;
    private Context mContext;

    private LeackTest(Context context) {
        this.mContext = context;
    }


    public static LeackTest getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LeackTest.class) {
                if (mInstance == null) {
                    mInstance = new LeackTest(context);
                }
            }
        }
        return mInstance;
    }

    public void dealData() {
    }
}
