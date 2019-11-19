package com.example.workmanagerdemo.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * creation date: 2019-11-13 17:50
 * description ：
 */
public class MyWorker extends Worker {
    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public MyWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {   // 任务执行逻辑   成功 失败  或者重新执行
        Data data = getInputData();
        String name = data.getString("name");
        int age = data.getInt("age", 0);
        Data out = new Data.Builder()
                .putString("result", "可以返回啦")
                .putInt("status", 200)
                .build();
        return Result.success(out);
    }


}
