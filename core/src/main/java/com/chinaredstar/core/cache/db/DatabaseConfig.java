package com.chinaredstar.core.cache.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hairui.xiang on 2017/8/17.
 */

public class DatabaseConfig {
    private static List<Class<?>> mTables = new ArrayList<>();
    private static List<Class<?>> mUpgradeTables = new ArrayList<>();
    private static int mDatabaseVersion = 1;
    private static String mDatabaseName = "chinaredstar_hrcore";

    public static List<Class<?>> getTables() {
        return mTables;
    }

    public static List<Class<?>> getUpgradeTables() {
        return mUpgradeTables;
    }

    public static int getDatabaseVersion() {
        return mDatabaseVersion;
    }

    public static String getDatabaseName() {
        return mDatabaseName;
    }

    public static Build newBuild() {
        return new Build();
    }

    public static final class Build {
        public Build addTable(Class<?> clazz) {
            mTables.add(clazz);
            return this;
        }

        public Build addTables(List<Class<?>> clazzs) {
            mTables.addAll(clazzs);
            return this;
        }

        public Build addUpgradeTables(List<Class<?>> clazzs) {
            mUpgradeTables.addAll(clazzs);
            return this;
        }

        public Build setDatabaseVersion(int databaseVersion) {
            mDatabaseVersion = databaseVersion;
            return this;
        }

        public Build setDatabaseName(String databaseName) {
            mDatabaseName = databaseName;
            return this;
        }
    }
}
