package com.chinaredstar.core.cache.db;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by hairui.xiang on 2017/8/30.
 */

public class DatabaseService<T, ID> implements IBaseService<T, ID> {
    private DatabaseDao<T, ID> dao;

    public DatabaseService(Class<T> clazz) {
        this.dao = new DatabaseDao<T, ID>(clazz);
    }

    public void close() {
        this.dao.close();
    }

    @Override
    public T queryForId(ID id) {
        try {
            return this.dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> queryForAll() {
        try {
            return this.dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> queryForEq(String fieldName, Object value) {
        try {
            return this.dao.queryForEq(fieldName, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> queryForMatching(T matchObj) {
        try {
            return this.dao.queryForMatching(matchObj);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> queryForMatchingArgs(T matchObj) {
        try {
            return this.dao.queryForMatchingArgs(matchObj);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> queryForFieldValues(Map<String, Object> fieldValues) {
        try {
            return this.dao.queryForFieldValues(fieldValues);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> queryForFieldValuesArgs(Map<String, Object> fieldValues) {
        try {
            return this.dao.queryForFieldValuesArgs(fieldValues);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T queryForSameId(T data) {
        try {
            return this.dao.queryForSameId(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int create(T data) {
        try {
            return this.dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int create(Collection<T> datas) {
        try {
            return this.dao.create(datas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public T createIfNotExists(T data) {
        try {
            return this.dao.createIfNotExists(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Dao.CreateOrUpdateStatus createOrUpdate(T data) {
        try {
            return this.dao.createOrUpdate(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int update(T data) {
        try {
            return this.dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int updateId(T data, ID newId) {
        try {
            return this.dao.updateId(data, newId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int refresh(T data) {
        try {
            return this.dao.refresh(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int delete(T data) {
        try {
            return this.dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int deleteById(ID id) {
        try {
            return this.dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int delete(Collection<T> datas) {
        try {
            return this.dao.delete(datas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int deleteIds(Collection<ID> ids) {
        try {
            return this.dao.deleteIds(ids);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean idExists(ID id) {
        try {
            return this.dao.idExists(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
