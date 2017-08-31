package com.chinaredstar.demo.bean;

import com.chinaredstar.core.base.BaseBean;

/**
 * Created by hairui.xiang on 2017/8/30.
 */

public class Weatherinfo extends BaseBean {
    // {"weatherinfo":{"city":"北京","cityid":"101010100","temp1":"-2℃","temp2":"16℃",
    // "weather":"晴","img1":"n0.gif","img2":"d0.gif","ptime":"18:00"}}
    public Info weatherinfo;

    public static class Info {
        public String city;
        public String cityid;
        public String temp1;
        public String temp2;
        public String weather;
        public String img1;
        public String img2;
        public String ptime;
    }
}
