package com.chinaredstar.demo;

import android.os.Bundle;
import android.widget.TextView;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.qrcode.QRCodeScanActivity;
import com.chinaredstar.demo.utils.ActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jiquan.zhong on 2017/9/11.
 */

public class TestActivity extends BaseActivity {

    @BindView(R.id.activityStack)
    TextView mActivityStack;
    @BindView(R.id.activityUpdate)
    TextView mActivityUpdate;
    @BindView(R.id.eventBusDemo2)
    TextView mEventBusDemo2;
    @BindView(R.id.eventBusDemo)
    TextView mEventBusDemo;
    @BindView(R.id.frescoDemo)
    TextView mFrescoDemo;
    @BindView(R.id.leackDemo)
    TextView mLeackDemo;
    @BindView(R.id.loadmoreDemo)
    TextView mLoadmoreDemo;
    @BindView(R.id.mainActivity)
    TextView mMainActivity;
    @BindView(R.id.jsonParseDemo)
    TextView mJsonParseDemo;
    @BindView(R.id.photoGetDemo)
    TextView mPhotoGetDemo;
    @BindView(R.id.pullRefresh)
    TextView mPullRefresh;
    @BindView(R.id.qrcodeScan)
    TextView mQrcodeScan;
    @BindView(R.id.sqlsslCacheDemo)
    TextView mSqlsslCacheDemo;
    @BindView(R.id.testFramentDemo)
    TextView mTestFramentDemo;
    @BindView(R.id.viewPagerDemo)
    TextView mViewPagerDemo;

    @Override
    protected int getHeaderLayoutId() {
        return super.getHeaderLayoutId();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initValue() {
        super.initValue();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @OnClick(R.id.frescoDemo)
    public void fresco() {
        ActivityUtil.navigateTo(FrescoDemo.class);
    }

    @OnClick(R.id.leackDemo)
    public void leack() {
        ActivityUtil.navigateTo(LeackDemo.class);
    }

    @OnClick(R.id.loadmoreDemo)
    public void loadore() {
        ActivityUtil.navigateTo(LoadMoreDemo.class);
    }

    @OnClick(R.id.mainActivity)
    public void main(){
        ActivityUtil.navigateTo(MainActivity.class);
    }

    @OnClick(R.id.jsonParseDemo)
    public void parse()
    {
        ActivityUtil.navigateTo(NetRequestJsonParseDemo.class);
    }

    @OnClick(R.id.photoGetDemo)
    public void photeget(){
        ActivityUtil.navigateTo(PhotoGetDemo.class);
    }
    @OnClick(R.id.pullRefresh)
    public void pullrefresh(){
        ActivityUtil.navigateTo(PullRefreshDemo.class);
    }

    @OnClick(R.id.qrcodeScan)
    public void qrscan(){
        ActivityUtil.navigateTo(QRCodeScanActivity.class);
    }

    @OnClick(R.id.sqlsslCacheDemo)
    public void cache(){
        ActivityUtil.navigateTo(SqlSSCacheDemo.class);
    }

    @OnClick(R.id.activityStack)
    public void jumpStack() {
        ActivityUtil.navigateTo(ActivityStackTestDemo.class);
    }

    @OnClick(R.id.testFramentDemo)
    public void testFragment(){
        ActivityUtil.navigateTo(TestFragmentDemo.class);
    }

    @OnClick(R.id.viewpager)
    public void pager(){
        ActivityUtil.navigateTo(ViewPagerDemo.class);
    }

    @OnClick(R.id.activityUpdate)
    public void update() {
        ActivityUtil.navigateTo(ApkUpdateDemo.class);
    }

    @OnClick(R.id.eventBusDemo2)
    public void event2() {
        ActivityUtil.navigateTo(EventBus2Demo.class);
    }

    @OnClick(R.id.eventBusDemo)
    public void event() {
        ActivityUtil.navigateTo(EventBusDemo.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
