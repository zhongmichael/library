package com.chinaredstar.push.emui;

import android.content.Context;

import com.chinaredstar.push.IHxPushProvider;
import com.chinaredstar.push.utils.Platform;
import com.chinaredstar.push.utils.TokenUtil;
import com.huawei.android.pushagent.api.PushManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hairui.xiang on 2017/8/8.
 */

public class EmuiPushProvider implements IHxPushProvider {
    @Override
    public void registerPush(Context context) {
        // 获取客户端AccessToken,获取之前请先确定该应用（包名）已经在开发者联盟上创建成功，并申请、审核通过Push权益
        // 该测试应用已经注册过
        PushManager.requestToken(context);
    }

    @Override
    public void unRegisterPush(Context context) {
        PushManager.deregisterToken(context, TokenUtil.getToken(context));
    }

    /**
     * 设置别名，
     * 华为不支持alias的写法，所以只能用tag，tag只能放map，所以alias作为value,key为name
     */
    @Override
    public void setAlias(Context context, String alias) {
        Map<String, String> tag = new HashMap<>();
        tag.put("name", alias);
        PushManager.setTags(context, tag);
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        List<String> keys = new ArrayList<>();
        keys.add("name");
        PushManager.deleteTags(context, keys);
    }

    @Override
    public void setTags(Context context, String... tags) {

    }

    @Override
    public void unsetTags(Context context, String... tags) {

    }

    @Override
    public void pause(Context context) {
        PushManager.enableReceiveNormalMsg(context, false);
        PushManager.enableReceiveNotifyMsg(context, false);
    }

    @Override
    public void resume(Context context) {
        PushManager.enableReceiveNormalMsg(context, true);
        PushManager.enableReceiveNotifyMsg(context, true);
    }

    @Override
    public Platform name() {
        return Platform.EMUI;
    }
}
