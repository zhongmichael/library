package com.chinaredstar.demo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.chinaredstar.core.base.BaseFragment;
import com.chinaredstar.demo.R;

/**
 * Created by hairui.xiang on 2017/8/23.
 */

public class TestFragment extends BaseFragment {
    TextView tv_text;

    public static Fragment newInstance(int index) {
        TestFragment fragment = new TestFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("index", index);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void initValue() {
        super.initValue();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tv_text = findViewById(R.id.tv_text);
        tv_text.setText(String.valueOf(getArguments().getInt("index")));
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void initData() {
        super.initData();
        System.out.println("----------------------------initData------------------------------" + getArguments().getInt("index"));
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment;
    }

    @Override
    protected boolean openLazyinit() {
        return true;
    }
}
