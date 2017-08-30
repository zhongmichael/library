package com.chinaredstar.core.utils;

import android.os.Build;
import android.os.StrictMode;

import com.chinaredstar.core.base.BaseApplication;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by hairui.xiang on 2017/8/1.
 */

public class LeakCanaryUtil {
    private static RefWatcher mRefWater;

    private LeakCanaryUtil() {
    }

    public static void initLeakCanary(boolean isOpenLeakCanary) {
        if (isOpenLeakCanary) {
            enabledStrictMode();
            if (!LeakCanary.isInAnalyzerProcess(BaseApplication.getInstance())) {
                mRefWater = LeakCanary.install(BaseApplication.getInstance());
            }
        }
    }

    public static RefWatcher getRefWatcher() {
        return mRefWater;
    }

    private static void enabledStrictMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            //耗时操作
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            //内存泄漏
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
    }
}
