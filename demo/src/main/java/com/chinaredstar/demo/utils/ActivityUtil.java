package com.chinaredstar.demo.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.chinaredstar.core.utils.StringUtil;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

/**
 * Activity Navigator
 */
public class ActivityUtil {

    private static ActivityUtil sInstance;
    private Reference<Activity> mCurrentActivity;

    public static void install() {
        sInstance = new ActivityUtil();
    }

    public static void uninstall() {
        getInstance().mCurrentActivity.clear();
        getInstance().mCurrentActivity = null;
        sInstance = null;
    }

    private static ActivityUtil getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("You should call ActivityUtil.install() in you application first!");
        } else {
            return sInstance;
        }
    }

    public static Activity getCurrentActivity() {
        if (getInstance().mCurrentActivity == null) {
            throw new NullPointerException("You should setCurrentActivity first!");
        }
        return getInstance().mCurrentActivity.get();
    }

    public static void setCurrentActivity(@NonNull Activity mCurrentActivity) {
        getInstance().mCurrentActivity = new SoftReference<>(mCurrentActivity);
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    public static void navigateTo(Class<? extends Activity> clazz) {
        Activity activity = getCurrentActivity();
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    public static void navigateTo(Class<? extends Activity> clazz, Bundle bundle) {
        Activity activity = getCurrentActivity();
        Intent intent = new Intent(activity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    public static void navigateToThenKill(Class<? extends Activity> clazz) {
        Activity activity = getCurrentActivity();
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    public static void navigateToThenKill(Class<? extends Activity> clazz, Bundle bundle) {
        Activity activity = getCurrentActivity();
        Intent intent = new Intent(activity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     */
    public static void navigateToForResult(Class<? extends Activity> clazz, int requestCode) {
        navigateToForResult(null, clazz, requestCode);
    }

    /**
     * startActivityForResult with bundle
     */
    public static void navigateToForResult(Class<? extends Activity> clazz, int requestCode, Bundle bundle) {
        navigateToForResult(null, clazz, requestCode, bundle);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param context     上下文
     * @param clazz       跳转的activity
     * @param requestCode requestCode
     */
    public static void navigateToForResult(Object context, Class<? extends Activity> clazz, int requestCode) {
        navigateToForResult(context, clazz, requestCode, null);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param context     上下文
     * @param clazz       跳转的activity
     * @param requestCode requestCode
     * @param bundle      bundle
     */
    public static void navigateToForResult(Object context, Class<? extends Activity> clazz, int requestCode, Bundle bundle) {
        Activity activity = getCurrentActivity();
        Intent intent = new Intent(activity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        if (context == null) {
            context = activity;
        }
        startForResult(context, intent, requestCode);
    }

    public static void openDialPage(String tel) {
        if (StringUtil.isEmpty(tel)) return;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getCurrentActivity().startActivity(intent);
    }

    public static void finish() {
        getCurrentActivity().finish();
    }


    private static void startForResult(Object context, Intent intent, int request) {
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, request);
        } else if (context instanceof Fragment) {
            ((Fragment) context).startActivityForResult(intent, request);
        } else {
            throw new IllegalArgumentException("only use activity or fragment as a context:" + context);
        }
    }
}
