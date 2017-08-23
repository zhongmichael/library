package com.chinaredstar.demo.h5form.utils;

import com.chinaredstar.core.cache.db.DatabaseConfig;

/**
 * Created by hairui.xiang on 2017/8/17.
 */

public class DbTest {
    void getDb() {
        DatabaseConfig
                .newBuild()
                .setDatabaseVersion(1)
                .setDatabaseName("")
                .addTable(String.class);
    }

}
