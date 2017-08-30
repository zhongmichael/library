package com.chinaredstar.core.cache.db;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by hairui.xiang on 2017/7/31.
 */

public class DatabaseDao<T, ID> {
    private DatabaseHelper helper;
    private Dao<T, ID> dao;
    private Class<T> clazz;

    public DatabaseDao(Class clazz) {
        try {
            this.helper = DatabaseHelper.getInstance();
            this.clazz = clazz;
            this.dao = helper.getDao(clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        this.helper.close(this.clazz);
    }

    public T queryForId(ID id) throws SQLException {
        return this.dao.queryForId(id);
    }

    public List<T> queryForAll() throws SQLException {
        return this.dao.queryForAll();
    }

    public List<T> queryForEq(String fieldName, Object value) throws SQLException {
        return this.dao.queryForEq(fieldName, value);
    }

    public List<T> queryForMatching(T matchObj) throws SQLException {
        return this.dao.queryForMatching(matchObj);
    }

    public List<T> queryForMatchingArgs(T matchObj) throws SQLException {
        return this.dao.queryForMatchingArgs(matchObj);
    }

    public List<T> queryForFieldValues(Map<String, Object> fieldValues) throws SQLException {
        return this.dao.queryForFieldValues(fieldValues);
    }

    public List<T> queryForFieldValuesArgs(Map<String, Object> fieldValues) throws SQLException {
        return this.dao.queryForFieldValuesArgs(fieldValues);
    }

    public T queryForSameId(T data) throws SQLException {
        return this.dao.queryForSameId(data);
    }

    public int create(T data) throws SQLException {
        return this.dao.create(data);
    }

    public int create(Collection<T> datas) throws SQLException {
        return this.dao.create(datas);
    }

    public T createIfNotExists(T data) throws SQLException {
        return this.dao.createIfNotExists(data);
    }

    public Dao.CreateOrUpdateStatus createOrUpdate(T data) throws SQLException {
        return this.dao.createOrUpdate(data);
    }

    public int update(T data) throws SQLException {
        return this.dao.update(data);
    }

    public int updateId(T data, ID newId) throws SQLException {
        return this.dao.updateId(data, newId);
    }

    public int refresh(T data) throws SQLException {
        return this.dao.refresh(data);
    }

    public int delete(T data) throws SQLException {
        return this.dao.delete(data);
    }

    public int deleteById(ID id) throws SQLException {
        return this.dao.deleteById(id);
    }

    public int delete(Collection<T> datas) throws SQLException {
        return this.dao.delete(datas);
    }

    public int deleteIds(Collection<ID> ids) throws SQLException {
        return this.dao.deleteIds(ids);
    }

    public boolean idExists(ID id) throws SQLException {
        return this.dao.idExists(id);
    }
}
