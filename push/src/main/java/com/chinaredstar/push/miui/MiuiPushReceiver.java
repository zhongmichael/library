package com.chinaredstar.push.miui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.chinaredstar.push.HxPushReceiver;
import com.chinaredstar.push.utils.MsgProcessor;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hairui.xiang on 2017/8/8.
 * <p>
 * 1、PushMessageReceiver 是个抽象类，该类继承了 BroadcastReceiver。<br/>
 * 2、需要将自定义的 DemoMessageReceiver 注册在 AndroidManifest.xml 文件中：
 * <pre>
 * {@code
 *  <receiver
 *      android:name="com.xiaomi.mipushdemo.DemoMessageReceiver"
 *      android:exported="true">
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.ERROR" />
 *      </intent-filter>
 *  </receiver>
 *  }</pre>
 * 3、DemoMessageReceiver 的 onReceivePassThroughMessage 方法用来接收服务器向客户端发送的透传消息。<br/>
 * 4、DemoMessageReceiver 的 onNotificationMessageClicked 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法会在用户手动点击通知后触发。<br/>
 * 5、DemoMessageReceiver 的 onNotificationMessageArrived 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。<br/>
 * 6、DemoMessageReceiver 的 onCommandResult 方法用来接收客户端向服务器发送命令后的响应结果。<br/>
 * 7、DemoMessageReceiver 的 onReceiveRegisterResult 方法用来接收客户端向服务器发送注册命令后的响应结果。<br/>
 * 8、以上这些方法运行在非 UI 线程中。
 */
public class MiuiPushReceiver extends PushMessageReceiver {
    private static final String TAG = "MiuiPushReceiver";
    private String mRegId;
    private String mTopic;
    private String mAlias;
    private String mAccount;
    private String mStartTime;
    private String mEndTime;

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        Log.v(TAG, "onReceivePassThroughMessage is called. " + message.toString());
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
        MsgProcessor.miuiMessage(context, message, HxPushReceiver.ACTION_MESSAGE_RECEIVED);
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        Log.v(TAG, "onNotificationMessageClicked is called. " + message.toString());
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
        MsgProcessor.miuiMessage(context, message, HxPushReceiver.ACTION_NOTIFICATION_CLICKED);
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        Log.v(TAG,
                "onNotificationMessageArrived is called. " + message.toString());
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
        MsgProcessor.miuiMessage(context, message, HxPushReceiver.ACTION_NOTIFICATION_RECEIVED);
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        Log.v(TAG,
                "onCommandResult is called. " + message.toString());
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        String log;
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;//register_success
            } else {//register_fail

            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {//set_alias_success
                mAlias = cmdArg1;
            } else {//set_alias_fail

            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {//unset_alias_success
                mAlias = cmdArg1;
            } else {//unset_alias_fail

            }
        } else if (MiPushClient.COMMAND_SET_ACCOUNT.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {//set_account_success
                mAccount = cmdArg1;
            } else {//set_account_fail

            }
        } else if (MiPushClient.COMMAND_UNSET_ACCOUNT.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {//unset_account_success
                mAccount = cmdArg1;
            } else {//unset_account_fail

            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {//subscribe_topic_success
                mTopic = cmdArg1;
            } else {//subscribe_topic_fail

            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {//unsubscribe_topic_success
                mTopic = cmdArg1;
            } else {//unsubscribe_topic_fail

            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {//set_accept_time_success
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
            } else {//set_accept_time_fail

            }
        } else {
            log = message.getReason();
        }
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        Log.v(TAG,
                "onReceiveRegisterResult is called. " + message.toString());
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String log;
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {//register_success
                mRegId = cmdArg1;
//                MiPushClient.getRegId(context);
//                TokenUtil.setToken(context, mRegId);
            } else {//register_fail

            }
        } else {
            log = message.getReason();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private static String getSimpleDate() {
        return new SimpleDateFormat("MM-dd hh:mm:ss").format(new Date());
    }

}