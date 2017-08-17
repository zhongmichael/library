package com.chinaredstar.push.emui;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.chinaredstar.push.HxPushMessage;
import com.chinaredstar.push.HxPushReceiver;
import com.chinaredstar.push.utils.JsonUtils;
import com.chinaredstar.push.utils.MsgProcessor;
import com.chinaredstar.push.utils.Platform;
import com.chinaredstar.push.utils.TokenUtil;
import com.huawei.android.pushagent.api.PushEventReceiver;

/**
 * Created by hairui.xiang on 2017/8/8.
 */

public class EmuiPushReceiver extends PushEventReceiver {
    private static final String TAG = "EmuiPushReceiver";

    @Override
    public void onToken(Context context, String token, Bundle extras) {
        String belongId = extras.getString("belongId");
        String content = "获取token和belongId成功，token = " + token + ",belongId = " + belongId;
        Log.d(TAG, content);
        //RECEIVE_TOKEN_MSG
        TokenUtil.setToken(context, token);
    }


    @Override
    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
        //这里是透传消息， msg是透传消息的字节数组 bundle字段没用
        try {
            String content = "收到一条Push消息： " + new String(msg, "UTF-8");
            Log.d(TAG, content);
            //RECEIVE_PUSH_MSG

            content = new String(msg, "UTF-8");
            final HxPushMessage message = new HxPushMessage();
            message.setMessage(content);
            //华为的sdk在透传的时候无法实现extra字段，这里要注意
            message.setExtra("{}");
            message.setPlatform(Platform.EMUI);
            MsgProcessor.emuiMessage(context, message, HxPushReceiver.ACTION_MESSAGE_RECEIVED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void onEvent(Context context, Event event, Bundle extras) {
        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            int notifyId = extras.getInt(BOUND_KEY.pushNotifyId, 0);
            if (0 != notifyId) {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(notifyId);
            }
            String content = "收到通知附加消息： " + extras.getString(BOUND_KEY.pushMsgKey);
            Log.d(TAG, content);
            //RECEIVE_NOTIFY_CLICK_MSG
            final HxPushMessage message = new HxPushMessage();
            message.setTitle("");
            message.setNotifyID(notifyId);
            message.setMessage(extras.getString(BOUND_KEY.pushMsgKey));
            message.setExtra(JsonUtils.getJson(extras));
            message.setPlatform(Platform.EMUI);
            MsgProcessor.emuiMessage(context, message, HxPushReceiver.ACTION_NOTIFICATION_CLICKED);
        } else if (Event.PLUGINRSP.equals(event)) {
            final int TYPE_LBS = 1;
            final int TYPE_TAG = 2;
            int reportType = extras.getInt(BOUND_KEY.PLUGINREPORTTYPE, -1);
            boolean isSuccess = extras.getBoolean(BOUND_KEY.PLUGINREPORTRESULT, false);
            String message = "";
            if (TYPE_LBS == reportType) {
                message = "LBS report result :";
            } else if (TYPE_TAG == reportType) {
                message = "TAG report result :";
            }
            Log.d(TAG, message + isSuccess);
            //RECEIVE_TAG_LBS_MSG
        }
        super.onEvent(context, event, extras);
    }
}
