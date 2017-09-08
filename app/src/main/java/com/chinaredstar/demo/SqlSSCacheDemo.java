package com.chinaredstar.demo;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.cache.db.DatabaseService;
import com.chinaredstar.core.cache.ss.SharedPreferencesHelper;
import com.chinaredstar.demo.bean.User;

/**
 * Created by hairui.xiang on 2017/8/30.
 */

public class SqlSSCacheDemo extends BaseActivity {
    DatabaseService<User, Long> dao;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_empty;
    }

    @Override
    protected void initData() {
        super.initData();
        dao = new DatabaseService<>(User.class);
        User u = new User();
        u.setAge(30);
        u.setName("ma");
        u.setId(1L);
        dao.create(u);
        System.out.println(dao.queryForAll());
        dao.updateId(u, 10L);
        System.out.println(dao.queryForAll());

        SharedPreferencesHelper.clear();
        SharedPreferencesHelper.putObj("user", u);
        System.out.println(SharedPreferencesHelper.getObj("user",User.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //-------------------------------------------------------
        dao.close();
    }
}
