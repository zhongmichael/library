package com.chinaredstar.core.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by hairui.xiang on 2017/8/30.
 */

public class NumberUtil {
    /**
     * 一位小数点
     */
    public static final String P_ONE_DECI = "#0.0";
    /**
     * 两位小数点
     */
    public static final String P_TWO_DECI = "#0.00";

    public static String format(BigDecimal decimal, String pattern) {
        return new DecimalFormat(pattern).format(decimal);
    }

    /**
     * 保留一位小数
     */
    public static String keepOneDecimalPlaces(BigDecimal decimal) {
        return format(decimal, P_ONE_DECI);
    }

    /**
     * 保留两位小数
     */
    public static String keepTwoDecimalPlaces(BigDecimal decimal) {
        return format(decimal, P_TWO_DECI);
    }

}
