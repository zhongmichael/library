package com.chinaredstar.demo;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.base.BaseBean;
import com.chinaredstar.core.view.banner.BannerAdapter;
import com.chinaredstar.core.view.banner.BannerViewPager;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hairui.xiang on 2017/9/11.
 */

public class BannerActivity extends BaseActivity {
    BannerViewPager mBannerView;
    LinearLayout ll_dots;

    String[] urls = new String[]{
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505130774390&di=2aacf824537ac0b385f2e2509521dc62&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F8b82b9014a90f60326b707453b12b31bb051eda9.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505130774389&di=878333495f59d0af94eb95293b6bc6d9&imgtype=0&src=http%3A%2F%2Fbizhi.zhuoku.com%2F2009%2F08%2F28%2Fdongmanjingxuan%2Fdongman162.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505130774388&di=63787db9d5e0abefc3847a53bcc4c915&imgtype=0&src=http%3A%2F%2Fimg2.niutuku.com%2Fdesk%2Fanime%2F4446%2F4446-8866.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505130774387&di=e81e0110d9bc515ebb379b902b9ca948&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Ffaf2b2119313b07e6077d3bc0ad7912396dd8cb8.jpg"
    };

    @Override
    protected void initWidget() {
        mBannerView = findViewById(R.id.bvp_banner);
        ll_dots = findViewById(R.id.ll_dots);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_banner;
    }

    @Override
    protected void initData() {
        List<BannerInfo> mBannerInfo = new ArrayList<>();
        for (String url : urls) {
            mBannerInfo.add(new BannerInfo(url));
        }
        mBannerView.setAdapter(new BannerAdapter<BannerInfo>(mBannerInfo) {
            @Override
            protected View getView(BannerInfo data, int position) {
                SimpleDraweeView child = new SimpleDraweeView(mActivity);
                child.setLayoutParams(new ViewPager.LayoutParams());
                child.setImageURI(data.url);
                return child;
            }

            @Override
            protected void setCurrentDot(int position) {
                System.out.println("position: " + position);
                for(int i = 0; i< ll_dots.getChildCount(); i++){
                    ll_dots.getChildAt(i).setSelected(false);
                }
                ll_dots.getChildAt(position).setSelected(true);
            }
        });
    }

    public class BannerInfo extends BaseBean {
        public BannerInfo(String url) {
            this.url = url;
        }

        public String url;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBannerView.startRun();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBannerView.stopRun();
    }
}
