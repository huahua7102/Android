package com.example.admin.parcelabletest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent= getIntent();
        String data = intent.getStringExtra("data");
        Log.i("SecondActicity",data);
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
}
