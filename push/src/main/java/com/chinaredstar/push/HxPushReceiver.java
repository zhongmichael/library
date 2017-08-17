package com.chinaredstar.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by hairui.xiang on 2017/8/8.
 */

public abstract class HxPushReceiver extends BroadcastReceiver {
    public static final String HX_PUSH_MESSAGE = "push_message";
    /**
     * 收到透传消息
     */
    public static final String ACTION_MESSAGE_RECEIVED = "com.chinaredstar.RECEIVE_THROUGH_MESSAGE";
    /**
     * 通知栏消息到达
     */
    public static final String ACTION_NOTIFICATION_RECEIVED = "com.chinaredstar.NOTIFICATION_ARRIVED";
    /**
     * 通知栏消息被点击
     */
    public static final String ACTION_NOTIFICATION_CLICKED = "com.chinaredstar.NOTIFICATION_CLICKED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            try {
                HxPushMessage message = (HxPushMessage) intent.getSerializableExtra(HX_PUSH_MESSAGE);
                if (ACTION_MESSAGE_RECEIVED.equals(action)) {
                    onReceivePassThroughMessage(context, message);
                } else if (ACTION_NOTIFICATION_RECEIVED.equals(action)) {
                    onNotificationMessageArrived(context, message);
                } else if (ACTION_NOTIFICATION_CLICKED.equals(action)) {
                    onNotificationMessageClicked(context, message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 透传消息
     */
    public abstract void onReceivePassThroughMessage(Context context, HxPushMessage message);

    /**
     * 通知栏消息点击
     */
    public abstract void onNotificationMessageClicked(Context context, HxPushMessage message);

    /**
     * 通知栏消息到达,
     */
    public abstract void onNotificationMessageArrived(Context context, HxPushMessage message);
}
