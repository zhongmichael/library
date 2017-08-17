package com.chinaredstar.push.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by hairui.xiang on 2017/8/10.
 */

public class MetaDataUtil {

    private static final String FLYME_APPID = "FLYME_APPID";
    private static final String FLYME_APPKEY = "FLYME_APPKEY";
    private static final String MIUI_APPID = "MIUI_APPID";
    private static final String MIUI_APPKEY = "MIUI_APPKEY";

    public static Object getFlymeAppID(Context context) {
        return getAppMetaDataValue(context, FLYME_APPID);
    }

    public static Object getFlymeAppKey(Context context) {
        return getAppMetaDataValue(context, FLYME_APPKEY);
    }

    public static Object getMiuiAppID(Context context) {
        return getAppMetaDataValue(context, MIUI_APPID);
    }

    public static Object getMiuiAppKey(Context context) {
        return getAppMetaDataValue(context, MIUI_APPKEY);
    }

    /**
     * 获取Application中的meta-data.
     *
     * @return
     */
    private static Object getAppMetaDataValue(Context context, String key) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.get(key);
        } catch (Exception e) {
            Log.e("getMetaDataBundle", e.getMessage(), e);
        }
        return null;
    }
}

