package com.chinaredstar.push.flyme;

import android.content.Context;

import com.chinaredstar.push.IHxPushProvider;
import com.chinaredstar.push.utils.MetaDataUtil;
import com.chinaredstar.push.utils.Platform;
import com.meizu.cloud.pushsdk.PushManager;

/**
 * Created by hairui.xiang on 2017/8/9.
 */

public class FlymePushProvider implements IHxPushProvider {
    private String appID;
    private String appKey;

    public FlymePushProvider(Context context) {
        appID = MetaDataUtil.getFlymeAppID(context).toString();
        appKey = MetaDataUtil.getFlymeAppKey(context).toString();
    }

    public FlymePushProvider(String appID, String appKey) {
        this.appID = appID;
        this.appKey = appKey;
    }

    @Override
    public void registerPush(Context context) {
        com.meizu.cloud.pushsdk.PushManager.register(context, appID, appKey);
    }

    @Override
    public void unRegisterPush(Context context) {
        com.meizu.cloud.pushsdk.PushManager.unRegister(context, appID, appKey);
    }

    @Override
    public void setAlias(Context context, String alias) {
        com.meizu.cloud.pushsdk.PushManager.subScribeAlias(context, appID, appKey, PushManager.getPushId(context), alias);
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        com.meizu.cloud.pushsdk.PushManager.unSubScribeAlias(context, appID, appKey, PushManager.getPushId(context), alias);
    }

    @Override
    public void setTags(Context context, String... tags) {
        for (String tag : tags) {
            com.meizu.cloud.pushsdk.PushManager.subScribeTags(context, appID, appKey, PushManager.getPushId(context), tag);
        }
    }

    @Override
    public void unsetTags(Context context, String... tags) {
        for (String tag : tags) {
            com.meizu.cloud.pushsdk.PushManager.unSubScribeTags(context, appID, appKey, PushManager.getPushId(context), tag);
        }
    }

    @Override
    public void pause(Context context) {
        com.meizu.cloud.pushsdk.PushManager.unRegister(context, appID, appKey);
    }

    @Override
    public void resume(Context context) {
        com.meizu.cloud.pushsdk.PushManager.register(context, appID, appKey);
    }

    @Override
    public Platform name() {
        return Platform.FLYME;
    }
}
