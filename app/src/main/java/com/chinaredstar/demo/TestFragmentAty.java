package com.chinaredstar.demo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.demo.fragment.TestFragment;

/**
 * Created by hairui.xiang on 2017/8/23.
 */

public class TestFragmentAty extends BaseActivity {
    FrameLayout id_fragment;
    TabLayout id_tablayout;
    Fragment fragment0;
    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        id_fragment = findViewById(R.id.id_fragment);
        id_tablayout = findViewById(R.id.id_tablayout);

        id_tablayout.addTab(id_tablayout.newTab().setText("A"));
        id_tablayout.addTab(id_tablayout.newTab().setText("B"));
        id_tablayout.addTab(id_tablayout.newTab().setText("C"));
        id_tablayout.addTab(id_tablayout.newTab().setText("D"));

        fragment0 = TestFragment.newInstance(0);
        fragment1 = TestFragment.newInstance(1);
        fragment2 = TestFragment.newInstance(2);
        fragment3 = TestFragment.newInstance(3);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.id_fragment, fragment0)
                .add(R.id.id_fragment, fragment1)
                .add(R.id.id_fragment, fragment2)
                .add(R.id.id_fragment, fragment3)
                .hide(fragment0)
                .hide(fragment1)
                .hide(fragment2)
                .hide(fragment3)
                .commit();

        id_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                show(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        show(0);
    }

    private void show(int index) {
        if (0 == index) {
            getSupportFragmentManager().beginTransaction()
                    .show(fragment0)
                    .hide(fragment1)
                    .hide(fragment2)
                    .hide(fragment3)
                    .commit();
        } else if (1 == index) {
            getSupportFragmentManager().beginTransaction()
                    .hide(fragment0)
                    .show(fragment1)
                    .hide(fragment2)
                    .hide(fragment3)
                    .commit();
        } else if (2 == index) {
            getSupportFragmentManager().beginTransaction()
                    .hide(fragment0)
                    .hide(fragment1)
                    .show(fragment2)
                    .hide(fragment3)
                    .commit();
        } else if (3 == index) {
            getSupportFragmentManager().beginTransaction()
                    .hide(fragment0)
                    .hide(fragment1)
                    .hide(fragment2)
                    .show(fragment3)
                    .commit();
        }
    }

}
