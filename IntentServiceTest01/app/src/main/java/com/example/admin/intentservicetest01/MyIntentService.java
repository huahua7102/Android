package com.example.admin.intentservicetest01;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;


public class MyIntentService extends IntentService {
   private  static final String  TAG = "MyIntentService";

    //PS:首先要提供一个无参的构造方法，并且必须在其内部调用父类得有参构造方法
    public MyIntentService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getStringExtra("task_action");

        Log.d(TAG,"收到任务："+action);
        SystemClock.sleep(3000);//模拟耗时的后台任务
        if("task1".equals(action))
            Log.d(TAG,"处理任务："+action);
    }

    @Override
    public void onDestroy() {
       Log.d(TAG,"IntentService销毁");
        super.onDestroy();
    }
}
