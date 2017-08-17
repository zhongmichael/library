package com.chinaredstar.core.cache.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.chinaredstar.core.R;
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

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private Context mContext;
    private static DataBaseHelper mDataBaseHelper;
    private Map<String, Dao> daos = new HashMap<String, Dao>();

    public DataBaseHelper(Context context) {
        super(context, context.getResources().getString(R.string.db_name), null, context.getResources().getInteger(R.integer.db_version));
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            String[] db_tables = mContext.getResources().getStringArray(R.array.db_tables);
            for (String table : db_tables) {
                TableUtils.createTableIfNotExists(connectionSource, Class.forName(table));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            String[] db_tables = mContext.getResources().getStringArray(R.array.db_tables_upgrade);
            for (String table : db_tables) {
                TableUtils.dropTable(connectionSource, Class.forName(table), true);
            }
            onCreate(database, connectionSource);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DataBaseHelper getInstance(Context context) {
        if (mDataBaseHelper == null) {
            synchronized (DataBaseHelper.class) {
                if (mDataBaseHelper == null) {
                    mDataBaseHelper = new DataBaseHelper(context.getApplicationContext());
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
