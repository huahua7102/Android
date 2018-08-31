package com.example.admin.threadpool;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runThreadPool();
    }
    //系统预置的4种线程池的典型使用方法
    private void runThreadPool() {
        Runnable command = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);

            }
        };

        /*
        FixedThreadPool:只有核心线程，线程数量固定；
         */
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        fixedThreadPool.execute(command);


        /*
        cachedThreadPool:只有非核心线程，线程数量不定，超时时长：60S；
        比较适合执行大量的耗时较少的任务
         */
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(command);


        /*
         ScheduledThreadPool:核心线程数量固定，非可信线程没有限制，非核心先出闲置立即被回收；
          主要用于执行定时任务和具有固定周期的重复任务
         */
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);
        //2000ms后执行command（定时）
        scheduledThreadPool.schedule(command,2000, TimeUnit.MILLISECONDS);
        //延迟10ms后，每隔1000ms执行一次command（定时+固定周期）
        scheduledThreadPool.scheduleAtFixedRate(command,10,1000,TimeUnit.MILLISECONDS);


        /* SingleThreadExecutor:内部只有一个核心线程     */
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(command);

    }

    

}
