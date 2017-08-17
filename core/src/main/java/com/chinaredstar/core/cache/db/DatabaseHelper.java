package com.chinaredstar.core.cache.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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

    private Context mContext;
    private static DatabaseHelper mDataBaseHelper;
    private Map<String, Dao> daos = new HashMap<String, Dao>();

    public DatabaseHelper(Context context) {
//        super(context, context.getResources().getString(R.string.db_name), null, context.getResources().getInteger(R.integer.db_version));
        super(context, DatabaseConfig.getDatabaseName(), null, DatabaseConfig.getDatabaseVersion());
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
         /*   String[] db_tables = mContext.getResources().getStringArray(R.array.db_tables);
            for (String table : db_tables) {
                TableUtils.createTableIfNotExists(connectionSource, Class.forName(table));
            }*/

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
           /* String[] db_tables = mContext.getResources().getStringArray(R.array.db_tables_upgrade);
            for (String table : db_tables) {
                TableUtils.dropTable(connectionSource, Class.forName(table), true);
            }*/
            for (Class<?> clazz : DatabaseConfig.getUpgradeTables()) {
                TableUtils.dropTable(connectionSource, clazz, true);
            }
            onCreate(database, connectionSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DatabaseHelper getInstance(Context context) {
        if (mDataBaseHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (mDataBaseHelper == null) {
                    mDataBaseHelper = new DatabaseHelper(context.getApplicationContext());
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
