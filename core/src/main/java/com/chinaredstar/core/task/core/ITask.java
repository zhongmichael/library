package com.chinaredstar.core.task.core;

/**
 * Created by hairui.xiang on 2017/9/13.
 */

public abstract class ITask {
    public int id;

    public ITask(int id) {
        this.id = id;
    }

    public abstract TaskResult doTask();
}
