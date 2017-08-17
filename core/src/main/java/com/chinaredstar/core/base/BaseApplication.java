package com.chinaredstar.core.base;

import android.app.Application;

import com.chinaredstar.core.cache.db.DatabaseConfig;
import com.chinaredstar.core.fresco.ImageConfig;
import com.chinaredstar.core.okhttp.OkHttpUtils;
import com.chinaredstar.core.okhttp.https.HttpsUtils;
import com.chinaredstar.core.okhttp.log.LoggerInterceptor;
import com.chinaredstar.core.utils.CHandler;
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
    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
        initDatabase();
        initOkhttp();
        initFresco();
        initLeakCanary();
        initX5();
    }

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

    private void initX5() {
//        QbSdk.setDownloadWithoutWifi(true);//非wifi条件下允许下载X5内核
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        final QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtil.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };

        CHandler.handler().post(new Runnable() {
            @Override
            public void run() {
                //x5内核初始化接口
                QbSdk.initX5Environment(getApplicationContext(), cb);
            }
        });
    }

    /**
     * 初始化fresco
     */
    private void initFresco() {
        Fresco.initialize(this, ImageConfig.getOkHttpImagePipelineConfig(this));
    }

    private void initLeakCanary() {
        if (isOpenLeakCanary()) {
            LeakCanaryUtil.initLeakCanary(this);
        }
    }

    private void initLogger() {
        LogUtil.init(isPrintLog());
    }

    private void initDatabase() {
        DatabaseConfig
                .newBuild()
                .setDatabaseName(getDatabaseName())
                .setDatabaseVersion(getDatabaseVersion())
                .addTables(getDatabaseTables())
                .addUpgradeTables(getDatabaseUpgradeTables());
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
     * default 'chinaredstar'
     */
    public String getDatabaseName() {
        return "chinaredstar";
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
