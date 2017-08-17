package com.chinaredstar.core.utils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by hairui.xiang on 2017/8/2.
 */

public class LogUtil {

    private LogUtil() {

    }

    public static void v(String message, Object... args) {
        Logger.v(message, args);
    }

    public static void d(String message, Object... args) {
        Logger.d(message, args);
    }

    public static void i(String message, Object... args) {
        Logger.i(message, args);
    }

    public static void w(String message, Object... args) {
        Logger.w(message, args);
    }

    public static void e(String message, Object... args) {
        Logger.e(message, args);
    }

    public static void json(String json) {
        Logger.json(json);
    }

    public static void xml(String xml) {
        Logger.xml(xml);
    }

    public static void v(String tag, String message, Object... args) {
        Logger.t(tag).v(message, args);
    }

    public static void d(String tag, String message, Object... args) {
        Logger.t(tag).d(message, args);
    }

    public static void i(String tag, String message, Object... args) {
        Logger.t(tag).i(message, args);
    }

    public static void w(String tag, String message, Object... args) {
        Logger.t(tag).w(message, args);
    }

    public static void e(String tag, String message, Object... args) {
        Logger.t(tag).e(message, args);
    }

    public static void json(String tag, String json) {
        Logger.t(tag).json(json);
    }

    public static void xml(String tag, String xml) {
        Logger.t(tag).xml(xml);

    }

    public static void init(final Boolean isPrintLog) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
//                .methodCount(0)         // (Optional) How many method line to show. Default 2
//                .methodOffset(3)        // (Optional) Skips some method invokes in stack trace. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("chinaredstar")   // (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
//                BuildConfig.DEBUG
                return isPrintLog;
            }
        });
//        Logger.addLogAdapter(new DiskLogAdapter());
    }
}
