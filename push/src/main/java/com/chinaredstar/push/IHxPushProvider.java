package com.chinaredstar.push;

import android.content.Context;

import com.chinaredstar.push.utils.Platform;

/**
 * Created by hairui.xiang on 2017/8/8.
 */

public interface IHxPushProvider {
    /**
     * 注册
     */
    void registerPush(Context context);

    /**
     * 注销
     */
    void unRegisterPush(Context context);

    /**
     * 设置别名
     */
    void setAlias(Context context, String alias);

    /**
     * 删除别名
     */
    void unsetAlias(Context context, String alias);

    /**
     * 设置tag
     */
    void setTags(Context context, String... tags);

    /**
     * 删除tag
     */
    void unsetTags(Context context, String... tags);

    /**
     * 停止推送
     */
    void pause(Context context);

    /**
     * 开始推送
     **/
    void resume(Context context);

    /**
     * 区分当前所使用的推送平台
     * return {@link com.chinaredstar.push.utils.Platform}
     */
    Platform name();//Platform
}
