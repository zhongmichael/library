package com.chinaredstar.core.utils;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by hairui.xiang on 2017/8/1.
 */

public class LeakCanaryUtil {
    private static RefWatcher mRefWater;

    private LeakCanaryUtil() {
    }

    public static void initLeakCanary(Application application) {
        if (!LeakCanary.isInAnalyzerProcess(application)) {
            enabledStrictMode();
            mRefWater = LeakCanary.install(application);
        }
    }

    public static RefWatcher getRefWatcher() {
        return mRefWater;
    }

    private static void enabledStrictMode() {
        if (Build.VERSION.SDK_INT >= 9) {
            //耗时操作
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            //内存泄漏
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }

    }
}
