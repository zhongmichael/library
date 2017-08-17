package com.chinaredstar.push;

import android.content.Context;

import com.chinaredstar.push.jpush.JPushProvider;
import com.chinaredstar.push.utils.Platform;
import com.chinaredstar.push.utils.RomUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hairui.xiang on 2017/8/8.
 */

public class HxPush {
    private static Map<Platform, IHxPushProvider> mPushProviders = new HashMap<>();
    private static Platform mPlatform = Platform.JPUSH;

    public static void addPushProvider(IHxPushProvider provider) {
        mPushProviders.put(provider.name(), provider);
    }

    private static void build() {
        if (RomUtil.rom() == Platform.MIUI) {
            mPlatform = Platform.MIUI;
        } else if ((RomUtil.rom() == Platform.EMUI)) {
            mPlatform = Platform.EMUI;
        } else if ((RomUtil.rom() == Platform.FLYME)) {
            mPlatform = Platform.FLYME;
        } else {
            mPlatform = Platform.JPUSH;
        }
        System.out.println("[rom: " + mPlatform.name() + "]");
    }


    public static void register(Context context) {
        build();
        if (!hasProvider()) {
            intDefaultProvider();
        }
        getPushProvider().registerPush(context.getApplicationContext());
    }

    public static void unregister(Context context) {
        getPushProvider().unRegisterPush(context.getApplicationContext());
    }

    public static void setAlias(Context context, String alias) {
        getPushProvider().setAlias(context.getApplicationContext(), alias);
    }

    public static void unsetAlias(Context context, String alias) {
        getPushProvider().unsetAlias(context.getApplicationContext(), alias);
    }

    public static void setTags(Context context, String... tags) {
        getPushProvider().setTags(context.getApplicationContext(), tags);
    }

    public static void unsetTags(Context context, String... tags) {
        getPushProvider().unsetTags(context.getApplicationContext(), tags);
    }

    public static void pause(Context context) {
        getPushProvider().pause(context.getApplicationContext());
    }

    public static void resume(Context context) {
        getPushProvider().resume(context.getApplicationContext());
    }

    public static IHxPushProvider getPushProvider() {
        if (null == mPlatform) {
            throw new RuntimeException("you need call register method");
        }
        return mPushProviders.get(mPlatform);
    }

    private static boolean hasProvider() {
        return 0 != mPushProviders.size();
    }

    private static void intDefaultProvider() {
        mPlatform = Platform.JPUSH;
        addPushProvider(new JPushProvider());
    }
}
