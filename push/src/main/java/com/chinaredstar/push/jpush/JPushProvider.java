package com.chinaredstar.push.jpush;

import android.content.Context;

import com.chinaredstar.push.IHxPushProvider;
import com.chinaredstar.push.utils.Platform;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by hairui.xiang on 2017/8/9.
 */

public class JPushProvider implements IHxPushProvider {
    // 用户自定义的操作序列号, 同操作结果一起返回，用来标识一次操作的唯一性。
    private static int sequence = 1;

    private static int getSequence() {
        return sequence++;
    }

    @Override
    public void registerPush(Context context) {
        System.out.println("--------------------JPushProvider registerPush----------");
        JPushInterface.setDebugMode(true);
        JPushInterface.init(context);
    }

    @Override
    public void unRegisterPush(Context context) {
        JPushInterface.stopPush(context);
    }

    @Override
    public void setAlias(Context context, String alias) {
        JPushInterface.setAlias(context, getSequence(), alias);
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        JPushInterface.deleteAlias(context, getSequence());
    }

    @Override
    public void setTags(Context context, String... tags) {
        Set<String> tagsSet = new HashSet<>(Arrays.asList(tags));
        JPushInterface.setTags(context, getSequence(), tagsSet);
    }

    @Override
    public void unsetTags(Context context, String... tags) {
        Set<String> tagsSet = new HashSet<>(Arrays.asList(tags));
        JPushInterface.deleteTags(context, getSequence(), tagsSet);
    }

    @Override
    public void pause(Context context) {
        if (!JPushInterface.isPushStopped(context)) {
            JPushInterface.stopPush(context);
        }
    }

    @Override
    public void resume(Context context) {
        if (JPushInterface.isPushStopped(context)) {
            JPushInterface.resumePush(context);
        }
    }

    @Override
    public Platform name() {
        return Platform.JPUSH;
    }
}
