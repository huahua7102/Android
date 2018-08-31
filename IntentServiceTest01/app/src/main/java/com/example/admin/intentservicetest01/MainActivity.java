package com.example.admin.intentservicetest01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this,MyIntentService.class);

        intent.putExtra("task_action","task1");
        startService(intent);
        intent.putExtra("task_action","task2");
        startService(intent);
        intent.putExtra("task_action","task3");
        startService(intent);
        intent.putExtra("task_action","task4");
        startService(intent);
    }
}
