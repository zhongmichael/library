package com.chinaredstar.core.utils;

import android.app.Activity;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hairui.xiang on 2017/8/25.
 */

public class ActivityStack {
    private ActivityStack() {
    }

    private static List<SoftReference<Activity>> mActivityStacks = Collections.synchronizedList(new LinkedList<SoftReference<Activity>>());

    public static List<SoftReference<Activity>> getStack() {
        return mActivityStacks;
    }

    /**
     * 移动activity到栈顶
     * <p>
     * 如：回首页 moveActivityToStacktop(xxxx.xxx.xxx.MainActivity)
     *
     * @param acName activity name getClass().getName();
     */
    public static void moveActivityToStacktop(String acName) {
        if (mActivityStacks.size() > 0) {
            Iterator<SoftReference<Activity>> it = mActivityStacks.iterator();
            while (it.hasNext()) {
                Activity ac = it.next().get();
                if (null == ac || !ac.getClass().getName().equals(acName)) {
                    it.remove();
                    finish(ac);
                    continue;
                }
                break;
            }
        }
    }

    /**
     * 移动activity到栈顶
     */
    public static void moveActivityToStacktop(Activity ac) {
        if (mActivityStacks.size() > 0) {
            Iterator<SoftReference<Activity>> it = mActivityStacks.iterator();
            while (it.hasNext()) {
                Activity activity = it.next().get();
                if (ac != activity) {
                    it.remove();
                    finish(ac);
                    continue;
                }
                break;
            }
        }
    }

    /**
     * 移除栈顶activity
     */
    public static void popStacktop() {
        if (mActivityStacks.size() > 0) {
            finish(mActivityStacks.remove(0).get());
        }
    }

    /**
     * 移除靠近栈顶的activity
     */
    public static void pop(Activity ac) {
        if (mActivityStacks.size() > 0) {
            Iterator<SoftReference<Activity>> it = mActivityStacks.iterator();
            while (it.hasNext()) {
                Activity activity = it.next().get();
                if (ac == activity) {
                    LogUtil.i("-----activity stack  pop : -----" + ac);
                    it.remove();
                    finish(ac);
                    break;
                }
            }
        }
    }

    /**
     * 入栈
     * D  C  B   A
     * 0  1  2   3
     */
    public static void push(Activity ac) {
        push(ac, 0);// 栈顶 index == 0
        LogUtil.i("-----activity stack  push : -----" + ac);
    }

    /**
     * 当前页面最多存在max个
     *
     * @param max 栈里当前activity的数量最多max个
     */
    public static void push(Activity ac, int max) {
        mActivityStacks.add(0, element(ac));
        if (max > 0) {
            //-----
            int same = 0;//相同的页面数量
            for (int i = mActivityStacks.size() - 1; i >= 0; i--) {
                if (ac.getClass().getName().equals(mActivityStacks.get(i).get().getClass().getName())) {
                    same++;
                }
            }
            //max = 3  same = 4 remove 1
            int remove = same - max;
            if (remove > 0) {
                for (int i = mActivityStacks.size() - 1; i >= 0; i--) {//从栈底开始删除
                    if (remove == 0) {
                        break;
                    }
                    if (ac.getClass().getName().equals(mActivityStacks.get(i).get().getClass().getName())) {
                        finish(mActivityStacks.remove(i).get());
                        remove--;
                    }
                }
            }
        }
    }

    private static SoftReference<Activity> element(Activity ac) {
        return new SoftReference<>(ac);
    }

    private static void finish(Activity ac) {
        if (null != ac) {
            ac.finish();
        }
    }
}
