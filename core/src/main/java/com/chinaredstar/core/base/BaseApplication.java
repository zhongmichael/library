package com.chinaredstar.core.base;

import android.app.Application;

import com.chinaredstar.core.cache.db.DatabaseConfig;
import com.chinaredstar.core.fresco.ImageConfig;
import com.chinaredstar.core.okhttp.OkHttpUtils;
import com.chinaredstar.core.okhttp.https.HttpsUtils;
import com.chinaredstar.core.okhttp.log.LoggerInterceptor;
import com.chinaredstar.core.utils.LeakCanaryUtil;
import com.chinaredstar.core.utils.LogUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * Created by hairui.xiang on 2017/8/1.
 */

public class BaseApplication extends Application {
    private static BaseApplication mInstance;

    public static Application getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initLogger();
        initDatabase();
        initOkhttp();
        initFresco();
        initLeakCanary();
        intCrashCollector();
        initX5();
    }

    /**
     * 初始化okhttp
     */
    private void initOkhttp() {
        ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
//        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor())
                .cookieJar(cookieJar1)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 初始化腾讯x5内核
     */
    private void initX5() {
//        QbSdk.setDownloadWithoutWifi(true);//非wifi条件下允许下载X5内核
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        final QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtil.d("app", " web x5 onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };

//        HandlerUtil.handler().post(new Runnable() {
//            @Override
//            public void run() {
//                //x5内核初始化接口
//                QbSdk.initX5Environment(getApplicationContext(), cb);
//            }
//        });
    }

    /**
     * 初始化fresco
     */
    private void initFresco() {
        Fresco.initialize(this, ImageConfig.getOkHttpImagePipelineConfig(this));
    }

    /**
     * 内存泄漏以及anr监控
     */
    private void initLeakCanary() {
        LeakCanaryUtil.initLeakCanary(isOpenLeakCanary());
    }

    /**
     * 打印日志
     */
    private void initLogger() {
        LogUtil.init(isPrintLog());
    }

    /**
     * 数据库
     * */
    private void initDatabase() {
        DatabaseConfig
                .newBuild()
                .setDatabaseName(getDatabaseName())//数据库名
                .setDatabaseVersion(getDatabaseVersion())//数据库版本(修改版本号实现数据库升级)
                .addTables(getDatabaseTables())//表
                .addUpgradeTables(getDatabaseUpgradeTables());//修改更新的表
    }

    private void intCrashCollector() {
    }

    /**
     * default empty
     */
    public List<Class<?>> getDatabaseTables() {
        return new ArrayList<>();
    }

    /**
     * default empty
     */
    public List<Class<?>> getDatabaseUpgradeTables() {
        return new ArrayList<>();
    }

    /**
     * default 1
     */
    public int getDatabaseVersion() {
        return 1;
    }

    /**
     * default ''
     */
    public String getDatabaseName() {
        return "core_lib_db_name";
    }

    public boolean isCollectCrashLog() {
        return isPrintLog();
    }


    /**
     * default true
     */
    public boolean isPrintLog() {
        return true;
    }

    /**
     * default true
     */
    public boolean isOpenLeakCanary() {
        return true;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ImageConfig.trimMemory();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImageConfig.clearMemoryCaches();
    }
}
