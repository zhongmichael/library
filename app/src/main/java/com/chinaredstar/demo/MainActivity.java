package com.chinaredstar.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chinaredstar.core.okhttp.OkHttpUtils;
import com.chinaredstar.core.okhttp.callback.ListCallback;
import com.chinaredstar.core.okhttp.cookie.CookieJarImpl;
import com.chinaredstar.demo.bean.User;
import com.chinaredstar.push.utils.MetaDataUtil;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;

import java.util.List;

import okhttp3.Call;
import okhttp3.CookieJar;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        X5WebView webView = new X5WebView(this);
//        webView.loadUrl("http://baidu.com");
//        setContentView(webView,new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LeackDemo.class));
            }
        });
        String url = "http://www.jianshu.com/p/3af806782a01?open_source=weibo_search";
        System.out.println(MetaDataUtil.getFlymeAppID(this).toString());
        System.out.println(MetaDataUtil.getFlymeAppKey(this).toString());
        System.out.println(MetaDataUtil.getMiuiAppID(this).toString());
        System.out.println(MetaDataUtil.getMiuiAppKey(this).toString());
        OkHttpUtils.post().url(url).build().execute(new ListCallback<User>(User.class) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(List<User> response, int id) {

            }
        });
//        JPushInterface.stopPush(this);
      /*  OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                System.out.println(response);
                    }
                });

        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(JsonUtil.toJsonString(us))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                    }
                });

        OkHttpUtils
                .postFile()
                .url(url)
                .file(new File(""))
                .build()
                .execute(null);

        OkHttpUtils
                .post()//
                .url(url)//
                .addParams("username", "hyman")//
                .addParams("password", "123")//
                .build()//
                .execute(new GenericsCallback<User>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(User response, int id) {
                    }
                });

        OkHttpUtils
                .get()//
                .url(url)//
                .tag(this)//
                .build()//
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id) {
                        Log.e("TAG", "onResponse：complete");
                    }
                });*/
    }

    public void clearSession(View view) {
        CookieJar cookieJar = OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        if (cookieJar instanceof CookieJarImpl) {
            ((CookieJarImpl) cookieJar).getCookieStore().removeAll();
        } else if (cookieJar instanceof PersistentCookieJar) {
            ((PersistentCookieJar) cookieJar).clearSession();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
