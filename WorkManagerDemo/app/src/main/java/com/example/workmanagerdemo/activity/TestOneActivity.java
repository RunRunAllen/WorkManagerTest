package com.example.workmanagerdemo.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.workmanagerdemo.R;
import com.example.workmanagerdemo.utils.MyWorker;

import java.util.concurrent.TimeUnit;

/**
 * creation date: 2019-11-13 17:07
 * description ：
 */
public class TestOneActivity extends AppCompatActivity {

    //添加Work 的 环境约束
    Constraints constraints = new Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)//联网状态
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .setRequiresStorageNotLow(true)//存储空间不能太小
            .build();

    //塞数据
    private String name = "hahaha";
    private int age = 12;
    Data data = new Data.Builder()
            .putString("name", name)
            .putInt("age", age)
            .build();

    //代表一个单独的任务，是对worker任务对包装， 任务只会执行一次
//    private WorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
//            .setConstraints(constraints)
//            .setInputData(data)
//            .build();

    //周期性执行任务
    PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 15, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .setInputData(data)
            .build();


    // worker的角色定位用于特殊的任务操作，可以脱离于本App的进程，所以这里的定期任务，做了最小限制，间隔至少15分钟，最小弹性伸缩时间为5分钟
//    WorkRequest request = new PeriodicWorkRequest.Builder(MyWorker.class, 15, TimeUnit.MINUTES)
//            .setBackoffCriteria(BackoffPolicy.LINEAR, 20, TimeUnit.MINUTES)
//            .build();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_one_activity);
        //3 加入任务管理，但不是执行，执行的代码稍后
        WorkManager.getInstance(this).enqueue(workRequest);
        //4、通过workRequest的唯一标记id，来操作request，并获取返回数据
        // 这里因为在oncreate中执行，会先与work执行，toast会弹出未执行work的空结果，work变化后，还会显示出成功后的结果。
        // 这是因为observe监测worker的status变化 enqueued、RUNNING、successed、retry、failure等
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            Data outputData = workInfo.getOutputData();
                            String result = outputData.getString("result");
                            int status = outputData.getInt("status", 0);
                            Toast.makeText(TestOneActivity.this, "result: " + result + "status: " + status, Toast.LENGTH_LONG).show();
                            Log.i("haha", "===" + workInfo.getState().name());
                        }

                    }
                });


//        WorkManager.getInstance(this)
//                .beginWith()
//                .then()
//                .then()
//                .enqueue();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WorkManager.getInstance(this).cancelWorkById(workRequest.getId());
    }
}
