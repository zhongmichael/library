package com.chinaredstar.push.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.chinaredstar.push.HxPushMessage;
import com.chinaredstar.push.HxPushReceiver;
import com.chinaredstar.push.utils.MsgProcessor;
import com.chinaredstar.push.utils.Platform;
import com.meizu.cloud.pushsdk.pushtracer.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by hairui.xiang on 2017/8/9.
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            Log.e(TAG, "[JPushReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            String messageId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
            String extraMessage = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.d(TAG, "[JPushReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...
//                JPushInterface.getRegistrationID(context));
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "[JPushReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//                processCustomMessage(context, bundle);
                HxPushMessage message = new HxPushMessage();
                message.setTitle(bundle.getString(JPushInterface.EXTRA_TITLE));
                message.setMessageID(messageId);
                message.setMessage(bundle.getString(JPushInterface.EXTRA_MESSAGE));
                message.setExtra(extraMessage);
                message.setPlatform(Platform.JPUSH);
                MsgProcessor.jpushMessage(context, message, HxPushReceiver.ACTION_MESSAGE_RECEIVED);
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "[JPushReceiver] 接收到推送下来的通知");
                Log.d(TAG, "[JPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);
                HxPushMessage message = new HxPushMessage();
                message.setNotifyID(notifactionId);
                message.setMessageID(messageId);
                message.setTitle(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
                message.setMessage(bundle.getString(JPushInterface.EXTRA_ALERT));
                message.setExtra(extraMessage);
                message.setPlatform(Platform.JPUSH);
                MsgProcessor.jpushMessage(context, message, HxPushReceiver.ACTION_NOTIFICATION_RECEIVED);
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.d(TAG, "[JPushReceiver] 用户点击打开了通知");
                HxPushMessage message = new HxPushMessage();
                message.setNotifyID(notifactionId);
                message.setMessageID(messageId);
                message.setTitle(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
                message.setMessage(bundle.getString(JPushInterface.EXTRA_ALERT));
                message.setExtra(extraMessage);
                message.setPlatform(Platform.JPUSH);
                MsgProcessor.jpushMessage(context, message, HxPushReceiver.ACTION_NOTIFICATION_CLICKED);
                //打开自定义的Activity
            /*    Intent i = new Intent(context, TestActivity.class);
                i.putExtras(bundle);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                context.startActivity(i);*/

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.d(TAG, "[JPushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.w(TAG, "[JPushReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Log.d(TAG, "[JPushReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logger.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
   /* private void processCustomMessage(Context context, Bundle bundle) {
        if (MainActivity.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
            if (!ExampleUtil.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (extraJson.length() > 0) {
                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {

                }

            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        }
    }*/
}

