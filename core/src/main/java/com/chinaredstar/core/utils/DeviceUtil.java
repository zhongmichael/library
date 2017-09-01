package com.chinaredstar.core.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.chinaredstar.core.base.BaseApplication;

/**
 * Created by hairui.xiang on 2017/9/1.
 */

public class DeviceUtil {
    /**
     */
    public static TelephonyManager getTelephonyManager() {
        return (TelephonyManager) BaseApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
    }

    //版本名
    public static String getVersionName() {
        return getPackageInfo().versionName;
    }

    //版本号
    public static int getVersionCode() {
        return getPackageInfo().versionCode;
    }

    private static PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            PackageManager pm = BaseApplication.getInstance().getPackageManager();
            pi = pm.getPackageInfo(BaseApplication.getInstance().getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }
}
