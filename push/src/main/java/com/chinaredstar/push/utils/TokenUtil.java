package com.chinaredstar.push.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hairui.xiang on 2017/8/8.
 */

public class TokenUtil {
    private static final String XH_PUSH_TOKEN_XML = "hx_push_token_xml";
    private static final String XH_PUSH_TOKEN_KEY = "hx_push_token_key";

    public static void setToken(Context context, String token) {
        SharedPreferences ss = context.getSharedPreferences(XH_PUSH_TOKEN_XML, Context.MODE_PRIVATE);
        ss.edit().putString(XH_PUSH_TOKEN_KEY, token).commit();
    }

    public static String getToken(Context context) {
        SharedPreferences ss = context.getSharedPreferences(XH_PUSH_TOKEN_XML, Context.MODE_PRIVATE);
        return ss.getString(XH_PUSH_TOKEN_KEY, null);
    }
}
