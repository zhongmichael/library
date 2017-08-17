package com.chinaredstar.cache.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by hairui.xiang on 2017/7/31.
 */

public class DataBaseManager<T> {
    private DataBaseHelper helper;
    private Dao dao;

    private DataBaseManager(Context context, Class clazz) {
        this.helper = DataBaseHelper.getInstance(context);
        try {
            this.dao = helper.getDao(clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果插入的数据存在，会更新数据
     */
    public int insert(T t) throws SQLException {
        return this.dao.create(t);
    }

    public int insertAll(List<T> list) throws SQLException {
        return this.dao.create(list);
    }

    public int update(T t) throws SQLException {
        return this.dao.update(t);
    }

    public int updateById(T t, long id) throws SQLException {
        return this.dao.updateId(t, id);
    }

    public UpdateBuilder updateBuild(T t) {
        return this.dao.updateBuilder();
    }

    public List<T> query(T t) throws SQLException {
        return this.dao.queryForMatchingArgs(t);
    }

    public T queryT(int id) throws SQLException {
        return (T) this.dao.queryForId(id);
    }

    public T queryT(T t) throws SQLException {
        return (T) this.dao.queryForSameId(t);
    }

    public List<T> queryAll() throws SQLException {
        return this.dao.queryForAll();
    }

    public QueryBuilder queryBuild() throws SQLException {
        return this.dao.queryBuilder();
    }

    public int delete(T t) throws SQLException {
        return this.dao.delete(t);
    }

    public int deleteAll(List<T> list) throws SQLException {
        return this.dao.delete(list);
    }

    public boolean isExist(long id) throws SQLException {
        return this.dao.idExists(id);
    }
}
