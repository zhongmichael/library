package com.chinaredstar.demo.bean;

import com.chinaredstar.core.base.BaseBean;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by hairui.xiang on 2017/7/31.
 */

@DatabaseTable(tableName = "tb_user")
public class User extends BaseBean{
    @DatabaseField(generatedId = true, unique = true)//自增，唯一
    private long id;
    @DatabaseField(columnName = "name")//数据库列名，如果不设置默认字段名
    private String name;
    @DatabaseField(columnName = "age",dataType = DataType.INTEGER)
    private int age;

    public User(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public User(){}

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
