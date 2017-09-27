package com.chinaredstar.core.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.chinaredstar.core.base.BaseApplication;
import com.chinaredstar.core.cache.ss.SharedPreferencesHelper;

import java.io.File;

/**
 * Created by hairui.xiang on 2017/9/20.
 */

public class ApkUtil {

    /**
     * apk安装
     *
     * @param file
     */

    public static void install(File file) {
        if (file != null) {
            LogUtil.i("install file: " + file.getAbsolutePath());
            //apk文件的本地路径
            //会根据用户的数据类型打开android系统相应的Activity。
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProviderUtil.getUriForFile(file);
                //权限
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                uri = Uri.parse("file://" + file.getAbsolutePath());
            }
            //设置intent的数据类型是应用程序application
            intent.setDataAndType(uri, "application/vnd.android.package-archive");//
            //为这个新apk开启一个新的activity栈
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //开始安装
            BaseApplication.getInstance().startActivity(intent);

            if (SharedPreferencesHelper.getObj("killProcess", Boolean.class)) {
                //关闭旧版本的应用程序的进程
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }

    public static void install(Uri uri) {
        if (null != uri) {
            //会根据用户的数据类型打开android系统相应的Activity。
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if ("file".equals(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProviderUtil.getUriForFile(new File(uri.getPath()));
                //权限
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            //设置intent的数据类型是应用程序application
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            //为这个新apk开启一个新的activity栈
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //开始安装
            BaseApplication.getInstance().startActivity(intent);

            if (SharedPreferencesHelper.getObj("killProcess", Boolean.class)) {
                //关闭旧版本的应用程序的进程
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }
}
