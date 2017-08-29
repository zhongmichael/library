package com.chinaredstar.demo;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.demo.fragment.TestFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hairui.xiang on 2017/8/23.
 */

public class ViewPagerDemo extends BaseActivity {
    TabLayout tablayout;
    ViewPager viewpager;
    List<Fragment> fragments = new ArrayList<>();
    List<String> mLabel = new ArrayList<>();

    @Override
    protected int getContentLayoutId() {
        return R.layout.viewpager;
    }

    @Override
    protected boolean retainStatusBarHeight() {
        return false;
    }

    @Override
    protected void initWidget() {
        setStatusBarBackgroundColor(Color.BLUE);
        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.viewpager);

        tablayout.addTab(tablayout.newTab().setText("tab1"));
        tablayout.addTab(tablayout.newTab().setText("tab2"));
        tablayout.addTab(tablayout.newTab().setText("tab3"));
        tablayout.addTab(tablayout.newTab().setText("tab4"));
        tablayout.setupWithViewPager(viewpager);

        fragments.add(TestFragment.newInstance(0));
        fragments.add(TestFragment.newInstance(1));
        fragments.add(TestFragment.newInstance(2));
        fragments.add(TestFragment.newInstance(3));

        mLabel.add("A");
        mLabel.add("B");
        mLabel.add("C");
        mLabel.add("D");

        viewpager.setAdapter(new VpPagerAdapter(getSupportFragmentManager()));
    }

    class VpPagerAdapter extends FragmentPagerAdapter {

        public VpPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mLabel.get(position);
        }
    }
}
