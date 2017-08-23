package com.chinaredstar.demo;

import com.chinaredstar.core.base.BaseApplication;
import com.chinaredstar.push.HxPush;
import com.chinaredstar.push.emui.EmuiPushProvider;
import com.chinaredstar.push.flyme.FlymePushProvider;
import com.chinaredstar.push.jpush.JPushProvider;
import com.chinaredstar.push.miui.MiuiPushProvider;

import java.util.List;

/**
 * Created by hairui.xiang on 2017/8/1.
 */

public class MyApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //1,默认只会创建jpush推送
//        HxPush.register(this);

        //2，分别添加极光，小米，魅族，华为的推送，根据当前rom是哪种类型的手机则采用哪种那个平台的推送
        HxPush.addPushProvider(new JPushProvider());
        HxPush.addPushProvider(new MiuiPushProvider(this));//"2882303761517604935","5301760491935"
        HxPush.addPushProvider(new FlymePushProvider(this));
        HxPush.addPushProvider(new EmuiPushProvider());
        HxPush.register(this);
    }

    @Override
    public boolean isPrintLog() {
        return BuildConfig.LOG_DEBUG;
    }

    @Override
    public boolean isOpenLeakCanary() {
        return BuildConfig.MEMORY_LEAK_CHECK;
    }

    @Override
    public List<Class<?>> getDatabaseTables() {
        return super.getDatabaseTables();
    }

    @Override
    public List<Class<?>> getDatabaseUpgradeTables() {
        return super.getDatabaseUpgradeTables();
    }

    @Override
    public int getDatabaseVersion() {
        return super.getDatabaseVersion();
    }

    @Override
    public String getDatabaseName() {
        return super.getDatabaseName();
    }
}
