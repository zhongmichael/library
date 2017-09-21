package com.chinaredstar.core.utils;

import android.net.Uri;
import android.support.v4.content.FileProvider;

import com.chinaredstar.core.base.BaseApplication;

import java.io.File;

/**
 * Created by hairui.xiang on 2017/9/20.
 */

public class FileProviderUtil {
    public static final String FILEPROVIDER_AUTHORITIES_VALUE = "com.chinaredstar.core.fileprovider";

    public static Uri getUriForFile(File file) {
        return FileProvider.getUriForFile(BaseApplication.getInstance(), FILEPROVIDER_AUTHORITIES_VALUE, file);
    }
}
