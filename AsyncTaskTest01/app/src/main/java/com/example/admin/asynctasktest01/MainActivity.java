package com.example.admin.asynctasktest01;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

     private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button)findViewById(R.id.bt);

        bt.setOnClickListener(this);
    }

    //串行执行
    @Override
    public void onClick(View view) {
       if (view == bt){
            new MyAsyncTask("AsyncTask#1").execute("");
           new MyAsyncTask("AsyncTask#2").execute("");
           new MyAsyncTask("AsyncTask#3").execute("");
           new MyAsyncTask("AsyncTask#4").execute("");
           new MyAsyncTask("AsyncTask#5").execute("");
           Log.d("MainActivity","onClick");

        }
    }

    //并行执行：为了让AsyncTask可以在Android3.0及以上的版本上并行，可以采用AsyncTask的executeOnExecutor方法；
   /* @Override
    public void onClick(View view) {
        if(view == bt){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
                new MyAsyncTask("AsyncTask#1").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
                new MyAsyncTask("AsyncTask#2").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
                new MyAsyncTask("AsyncTask#3").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
                new MyAsyncTask("AsyncTask#4").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
                new MyAsyncTask("AsyncTask#5").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
                Log.d("MainActivity","onClick");

            }
        }
    }*/

    private static class MyAsyncTask extends AsyncTask<String,Integer,String>{

        private  String mName= "AsyncTask";

        public MyAsyncTask(String name){
            super();
            mName = name;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return  mName;
        }




        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d("MainActivity",result+"结束时间："+System.currentTimeMillis());
        }
    }
}
