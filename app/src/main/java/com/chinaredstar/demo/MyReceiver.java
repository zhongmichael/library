package com.chinaredstar.demo;

import android.content.Context;
import android.content.Intent;

import com.chinaredstar.core.utils.JsonUtil;
import com.chinaredstar.push.HxPushMessage;
import com.chinaredstar.push.HxPushReceiver;

/**
 * Created by hairui.xiang on 2017/8/9.
 */

public class MyReceiver extends HxPushReceiver {
    @Override
    public void onReceivePassThroughMessage(Context context, HxPushMessage message) {
        System.out.println("MyReceiver onReceivePassThroughMessage:" + JsonUtil.toJsonString(message));
    }

    @Override
    public void onNotificationMessageClicked(Context context, HxPushMessage message) {
        System.out.println("MyReceiver onNotificationMessageClicked:" + JsonUtil.toJsonString(message));
        //打开自定义的Activity
        Intent i = new Intent(context, MainActivity.class);
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
    }

    @Override
    public void onNotificationMessageArrived(Context context, HxPushMessage message) {
        System.out.println("MyReceiver onNotificationMessageArrived:" + JsonUtil.toJsonString(message));


    }
}
