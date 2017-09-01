package com.chinaredstar.core.utils;

import com.chinaredstar.core.base.BaseApplication;

/**
 * Created by hairui.xiang on 2017/9/1.
 */

public class AssetsUtil {

    public static boolean isExist(String dir, String filename) {
        try {
            String[] names = BaseApplication.getInstance().getAssets().list(dir);
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
