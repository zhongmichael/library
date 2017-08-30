package com.chinaredstar.core.cache.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.chinaredstar.core.base.BaseApplication;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hairui.xiang on 2017/7/31.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static DatabaseHelper mDataBaseHelper;
    private Map<String, Dao> daos = new HashMap<String, Dao>();

    public DatabaseHelper(Context context) {
        super(context, DatabaseConfig.getDatabaseName(), null, DatabaseConfig.getDatabaseVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            for (Class<?> clazz : DatabaseConfig.getTables()) {
                TableUtils.createTableIfNotExists(connectionSource, clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            for (Class<?> clazz : DatabaseConfig.getUpgradeTables()) {
                TableUtils.dropTable(connectionSource, clazz, true);
            }
            onCreate(database, connectionSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DatabaseHelper getInstance() {
        if (mDataBaseHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (mDataBaseHelper == null) {
                    mDataBaseHelper = new DatabaseHelper(BaseApplication.getInstance());
                }
            }
        }
        return mDataBaseHelper;
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    public void close(Class clazz) {
        String className = clazz.getSimpleName();
        Dao dao = daos.remove(className);
        dao = null;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.remove(key);
            dao = null;
        }
    }
}
