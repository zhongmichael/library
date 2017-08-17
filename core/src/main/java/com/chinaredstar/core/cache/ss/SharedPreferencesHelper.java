package com.chinaredstar.core.cache.ss;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.chinaredstar.core.R;
import com.chinaredstar.core.utils.JsonUtil;

import java.util.List;

public class SharedPreferencesHelper {


    public static SharedPreferences getSharedpreFrences(@NonNull Context context) {
        try {
            return context.getSharedPreferences(context.getResources().getString(R.string.ss_name), Context.MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> void putObj(@NonNull Context context, String key, T obj) {
        try {
            getSharedpreFrences(context).edit().putString(key, JsonUtil.toJsonString(obj)).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> void putList(@NonNull Context context, String key, List<T> lst) {
        try {
            getSharedpreFrences(context).edit().putString(key, JsonUtil.toJsonString(lst)).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static <T> T getObj(@NonNull Context context, String key, Class<T> clazz) {
        try {
            return JsonUtil.parse(getSharedpreFrences(context).getString(key, null), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> List<T> getList(@NonNull Context context, String key, Class<T> clazz) {
        try {
            return JsonUtil.parseList(getSharedpreFrences(context).getString(key, null), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean remove(@NonNull Context context, String key) {
        try {
            return getSharedpreFrences(context).edit().remove(key).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean clear(@NonNull Context context) {
        try {
            return getSharedpreFrences(context).edit().clear().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
