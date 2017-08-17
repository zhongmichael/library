package com.chinaredstar.push.miui;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;

import com.chinaredstar.push.IHxPushProvider;
import com.chinaredstar.push.utils.MetaDataUtil;
import com.chinaredstar.push.utils.Platform;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * Created by hairui.xiang on 2017/8/8.
 */

public class MiuiPushProvider implements IHxPushProvider {
    private String appID;
    private String appKey;

    public MiuiPushProvider(Context context) {
        try {
            appID = MetaDataUtil.getMiuiAppID(context).toString().replace("MIUI", "");
            appKey = MetaDataUtil.getMiuiAppKey(context).toString().replace("MIUI", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MiuiPushProvider(String appID, String appKey) {
        try {
            this.appID = appID.replace("MIUI", "");
            this.appKey = appKey.replace("MIUI", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerPush(Context context) {
        System.out.println("init  miui  push -----------" + appID + "," + appKey);
        if (shouldInit(context)) {
            System.out.println("registerPush  miui  push -----------" + appID + "," + appKey);
            MiPushClient.registerPush(context, appID, appKey);
            LoggerInterface newLogger = new LoggerInterface() {
                @Override
                public void setTag(String tag) {
                    // ignore
                }

                @Override
                public void log(String content, Throwable t) {

                }

                @Override
                public void log(String content) {
                }

            };
            Logger.setLogger(context, newLogger);
        }
    }

    @Override
    public void unRegisterPush(Context context) {
        MiPushClient.unregisterPush(context);
    }

    @Override
    public void setAlias(Context context, String alias) {
        MiPushClient.setAlias(context, alias, null);
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        MiPushClient.unsetAlias(context, alias, null);
    }

    @Override
    public void setTags(Context context, String... tags) {
        for (String tag : tags) {
            MiPushClient.subscribe(context, tag, null);
        }
    }

    @Override
    public void unsetTags(Context context, String... tags) {
        for (String tag : tags) {
            MiPushClient.unsubscribe(context, tag, null);
        }
    }

    @Override
    public void pause(Context context) {
        MiPushClient.pausePush(context, null);
    }

    @Override
    public void resume(Context context) {
        MiPushClient.resumePush(context, null);
    }

    @Override
    public Platform name() {
        return Platform.MIUI;
    }

    /**
     * 用于小米推送的注册
     *
     * @param context
     * @return
     */
    private static boolean shouldInit(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        System.out.println("shouldInit false");
        return false;
    }
}
