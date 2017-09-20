package com.chinaredstar.core.utils;

import android.content.Intent;
import android.net.Uri;

import com.chinaredstar.core.base.BaseApplication;

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
            //apk文件的本地路径
            //会根据用户的数据类型打开android系统相应的Activity。
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //设置intent的数据类型是应用程序application
            intent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
            //为这个新apk开启一个新的activity栈
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //开始安装
            BaseApplication.getInstance().startActivity(intent);
            //关闭旧版本的应用程序的进程
//            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    public static void install(Uri uri) {
        if (null != uri) {
            //apk文件的本地路径
            //会根据用户的数据类型打开android系统相应的Activity。
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //设置intent的数据类型是应用程序application
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            //为这个新apk开启一个新的activity栈
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //开始安装
            BaseApplication.getInstance().startActivity(intent);
            //关闭旧版本的应用程序的进程
//            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
