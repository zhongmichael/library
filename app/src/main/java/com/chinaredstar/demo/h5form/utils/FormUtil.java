package com.chinaredstar.demo.h5form.utils;

import android.content.Context;

import com.chinaredstar.core.cache.ss.SharedPreferencesHelper;

import java.util.List;

/**
 * Created by hairui.xiang on 2017/8/14.
 */

public class FormUtil {

    /**
     * 存储表单数据
     */
    public static void writeUncommittedForm(Context context, String json) {
        //生成表单编号
        String no = gnerateFormNo();
        //保存当前表单数据
        SharedPreferencesHelper.putObj(no, json);
        //将新表单和以往表单合并
        refreshCache(context, no);
    }

    private static void refreshCache(Context context, String no) {
        String userName = "";
        List<String> nos = readUncommittedFormNos(context, userName);
        nos.add(no);
        SharedPreferencesHelper.putList(userName, nos);
    }

    /**
     * 读取当前登录用户的所有未提交表单的编号
     **/
    public static List<String> readUncommittedFormNos(Context context, String userName) {
        return SharedPreferencesHelper.getList(userName, String.class);
    }


    /**
     * 根据表单编号，查找表单
     */
    public static FromData readUncommittedFormByNo(Context context, String no) {
        return SharedPreferencesHelper.getObj(no, FromData.class);
    }

    /**
     * 生成表单编号
     */
    public static String gnerateFormNo() {
        return String.valueOf(System.currentTimeMillis());
    }
}
