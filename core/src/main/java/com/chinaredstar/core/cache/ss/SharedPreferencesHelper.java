package com.chinaredstar.core.cache.ss;

import android.content.Context;
import android.content.SharedPreferences;

import com.chinaredstar.core.R;
import com.chinaredstar.core.base.BaseApplication;
import com.chinaredstar.core.utils.JsonUtil;

import java.util.List;

public class SharedPreferencesHelper {


    public static SharedPreferences getSharedpreFrences() {
        try {
            return BaseApplication.getInstance().getSharedPreferences(BaseApplication.getInstance().getResources().getString(R.string.ss_name), Context.MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> void putObj(String key, T obj) {
        try {
            getSharedpreFrences().edit().putString(key, JsonUtil.toJsonString(obj)).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> void putList(String key, List<T> lst) {
        try {
            getSharedpreFrences().edit().putString(key, JsonUtil.toJsonString(lst)).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static <T> T getObj(String key, Class<T> clazz) {
        try {
            return JsonUtil.parse(getSharedpreFrences().getString(key, null), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> List<T> getList(String key, Class<T> clazz) {
        try {
            return JsonUtil.parseList(getSharedpreFrences().getString(key, null), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean remove(String key) {
        try {
            return getSharedpreFrences().edit().remove(key).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean clear() {
        try {
            return getSharedpreFrences().edit().clear().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
