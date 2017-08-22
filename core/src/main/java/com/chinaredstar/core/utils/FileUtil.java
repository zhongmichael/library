package com.chinaredstar.core.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by hairui.xiang on 2017/8/22.
 * "/Android/data/" + context.getPackageName() + "/cache/"
 */

public class FileUtil {
    public static final String H5_ASSET_PREFIX = "file:///android_asset/";
    public static final String H5_SDCARD_PREFIX = "content://com.android.htmlfileprovider/";

    public static File getAppCacheDir(Context context) {
        File dir = isExistSDCard() ? getExternalCacheDir(context) : getInnerCacheDir(context);
        if (dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * sd card
     */
    public static File getExternalCacheDir(Context context) {
        return new File(Environment.getExternalStorageState(), "/Android/data/" + context.getPackageName() + "/cache/");
    }

    /**
     * RAM
     */
    public static File getInnerCacheDir(Context context) {
        return context.getCacheDir();
    }

    public static boolean isExistSDCard() {
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static boolean isExistAssetFile(Context context, String path, String filename) {
        try {
            String[] names = context.getAssets().list(path);
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(filename.trim())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
