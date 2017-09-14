package com.chinaredstar.core.task.core;

import android.os.Process;

import com.chinaredstar.core.eventbus.EventCenter;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hairui.xiang on 2017/9/13.
 */

public class TaskRunnable implements Runnable {
    private ITask task;

    public TaskRunnable(ITask task) {
        this.task = task;
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        EventBus.getDefault().post(new EventCenter<>(task.id, task.doTask()));
    }
}
