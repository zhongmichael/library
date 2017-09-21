package com.chinaredstar.core.utils.download;

import com.chinaredstar.core.eventbus.EventCenter;
import com.chinaredstar.core.okhttp.OkHttpUtils;
import com.chinaredstar.core.okhttp.callback.FileCallBack;
import com.chinaredstar.core.utils.ApkUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import okhttp3.Call;

/**
 * Created by hairui.xiang on 2017/9/20.
 */

public class DownloadUtil {

    /***
     * @param url 下载地址
     * @param destFileDir 目标文件存储的文件夹路径
     * @param destFileName 目标文件存储的文件名
     * */
    public static void download(String url, final String destFileDir, final String destFileName, final int eventCode) {
        OkHttpUtils.get().url(url).build().execute(new FileCallBack(destFileDir, destFileName) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(File response, int id) {
                ApkUtil.install(response);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                EventCenter<DownloadProgress> ec = new EventCenter<>();
                ec.code = eventCode;
                ec.data = new DownloadProgress(progress, total);
                EventBus.getDefault().post(ec);
            }
        });
    }
}
