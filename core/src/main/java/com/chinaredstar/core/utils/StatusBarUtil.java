/*
 * Copyright (C) 2015-2016 Jacksgong(blog.dreamtobe.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chinaredstar.core.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * <p/>
 * In order to avoid the layout of the Status bar.
 */
public class StatusBarUtil {

    private static boolean INIT = false;
    private static int STATUS_BAR_HEIGHT = 50;

    private final static String STATUS_BAR_DEF_PACKAGE = "android";
    private final static String STATUS_BAR_DEF_TYPE = "dimen";
    private final static String STATUS_BAR_NAME = "status_bar_height";

    public static synchronized int getStatusBarHeight(final Context context) {
        if (!INIT) {
            int resourceId = context.getResources().
                    getIdentifier(STATUS_BAR_NAME, STATUS_BAR_DEF_TYPE, STATUS_BAR_DEF_PACKAGE);
            if (resourceId > 0) {
                STATUS_BAR_HEIGHT = context.getResources().getDimensionPixelSize(resourceId);
                INIT = true;
                Log.d("StatusBarUtil",
                        String.format("Get status bar height %d", STATUS_BAR_HEIGHT));
            }
        }

        return STATUS_BAR_HEIGHT;
    }

    /**
     * 设置沉浸式主题
     *
     * @param statusBar
     * @param activity
     */
    public static void setImmersiveStatusBar(View statusBar, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //添加一个状态栏高度的view，替代系统的状态栏
            statusBar.setVisibility(View.VISIBLE);
            int statusBarHeight = getStatusBarHeight(activity);//状态栏高度
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) statusBar.getLayoutParams(); // 取控件当前的布局参数
            linearParams.height = statusBarHeight;// 当控件的高强制设成
            statusBar.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件
        }
    }
}
