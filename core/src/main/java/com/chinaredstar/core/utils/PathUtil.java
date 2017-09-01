package com.chinaredstar.core.utils;

import android.os.Environment;

import com.chinaredstar.core.base.BaseApplication;

import java.io.File;

/**
 * Created by hairui.xiang on 2017/8/22.
 * "/Android/data/" + context.getPackageName() + "/cache/"
 */

public class PathUtil {
    public static final String H5_ASSET_PREFIX = "file:///android_asset/";
    public static final String H5_SDCARD_PREFIX = "content://com.android.htmlfileprovider/";

    public static File getAppCacheDir() {
        File dir = isExistSDCard() ? getExternalCacheDir() : getInnerCacheDir();
        if (null != dir && !dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static File getAppFilesDir() {
        File dir = isExistSDCard() ? getExternalFilesDir() : getInnerFilesDir();
        if (null != dir && !dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }


    /**
     * sd card
     */
    public static File getExternalCacheDir() {
        return new File(Environment.getExternalStorageState(), "/Android/data/" + BaseApplication.getInstance().getPackageName() + "/cache/");
    }

    /**
     * RAM
     */
    public static File getInnerCacheDir() {
        return BaseApplication.getInstance().getCacheDir();
    }

    public static File getExternalFilesDir() {
        return new File(Environment.getExternalStorageState(), "/Android/data/" + BaseApplication.getInstance().getPackageName() + "/files/");
    }

    public static File getInnerFilesDir() {
        return BaseApplication.getInstance().getFilesDir();
    }


    public static boolean isExistSDCard() {
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }
}
