package com.chinaredstar.demo;

import android.content.Intent;
import android.view.View;

import com.chinaredstar.core.base.BaseActivity;
import com.networkbench.agent.impl.NBSAppAgent;

/**
 * Created by hairui.xiang on 2017/9/12.
 */

public class DemoActivity extends BaseActivity {
    @Override
    protected void initValue() {
        NBSAppAgent.setLicenseKey("efa90bd6885c419ab7e8f01692de3390").withLocationServiceEnabled(true).
                start(this.getApplicationContext());
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_demo;
    }

    private void startPager(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    public void apkUpdate(View v) {
        startPager(ApkUpdateDemo.class);
    }

    public void banner(View v) {
        startPager(BannerActivity.class);
    }

    public void eventbus(View v) {
        startPager(EventBusDemo.class);
    }

    public void fresco(View v) {
        startPager(FrescoDemo.class);
    }

    public void loadmore(View v) {
        startPager(LoadMoreDemo.class);
    }

    public void netwrok(View v) {
        startPager(NetRequestJsonParseDemo.class);
    }

    public void photo(View v) {
        startPager(PhotoGetDemo.class);
    }

    public void pulltofresh(View v) {
        startPager(PullRefreshDemo.class);
    }

    public void qrcode(View v) {
        startPager(QRcodeScanDemo.class);
    }

    public void cache(View v) {
        startPager(SqlSSCacheDemo.class);
    }

    public void addFragment(View v) {
        startPager(TestFragmentDemo.class);
    }

    public void ViewPagerFramgent(View v) {
        startPager(ViewPagerDemo.class);
    }

    public void stickheader(View v) {
        startPager(ScrollableLayoutDemo.class);
    }
}
