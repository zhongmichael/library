package com.chinaredstar.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hairui.xiang on 2017/8/24.
 */

public class TimeUtil {

    /**
     * 将时间转换为时间戳 精确到毫秒
     *
     * @param timeStr
     * @param pattern
     */
    public static long timestamp(String timeStr, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(timeStr).getTime();
    }

    /**
     * 时间戳 精确到毫秒
     */
    public static long timestamp() throws ParseException {
        return new Date().getTime();
    }

    /**
     * 将时间戳转换为时间
     */
    public static Date parse(long timestamp) {
        return new Date(timestamp);
    }

    /**
     * 格式化时间
     *
     * @param timeStr  时间字符串
     * @param sPattern 源格式
     * @param dPattern 目标格式
     * @return 目标格式的时间字符串
     */
    public static String format(String timeStr, String sPattern, String dPattern) throws ParseException {
        return new SimpleDateFormat(dPattern).format(parse(timeStr, sPattern));
    }

    /**
     * 解析时间
     *
     * @param timeStr 时间字符串
     * @param pattern 时间的格式
     * @return Date
     **/
    public static Date parse(String timeStr, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(timeStr);
    }
}
