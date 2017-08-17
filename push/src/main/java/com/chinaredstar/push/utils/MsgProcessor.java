package com.chinaredstar.push.utils;

import android.content.Context;
import android.content.Intent;

import com.chinaredstar.push.HxPushMessage;
import com.chinaredstar.push.HxPushReceiver;
import com.xiaomi.mipush.sdk.MiPushMessage;

/**
 * Created by hairui.xiang on 2017/8/8.
 */

public class MsgProcessor {

    public static void miuiMessage(Context context, MiPushMessage message, String action) {
        HxPushMessage mHxPushMessage = new HxPushMessage();
        mHxPushMessage.setMessageID(message.getMessageId());
        mHxPushMessage.setTitle(message.getTitle());
        mHxPushMessage.setMessage(message.getContent());
        mHxPushMessage.setPlatform(Platform.MIUI);
        try {
//                result.setExtra(JsonUtils.setJson(message.getExtra()).toString());
            mHxPushMessage.setExtra(message.getContent());
        } catch (Exception e) {
            mHxPushMessage.setExtra("{}");
        }
        Intent intent = new Intent(action);
        intent.putExtra(HxPushReceiver.HX_PUSH_MESSAGE, mHxPushMessage);
        context.sendBroadcast(intent);
    }

    public static void emuiMessage(Context context, HxPushMessage message, String action) {
        process(context, message, action);
    }

    public static void flymeMessage(Context context, HxPushMessage message, String action) {
        process(context, message, action);
    }

    public static void jpushMessage(Context context, HxPushMessage message, String action) {
        process(context, message, action);
    }

    private static void process(Context context, HxPushMessage message, String action) {
        Intent intent = new Intent(action);
        intent.putExtra(HxPushReceiver.HX_PUSH_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
