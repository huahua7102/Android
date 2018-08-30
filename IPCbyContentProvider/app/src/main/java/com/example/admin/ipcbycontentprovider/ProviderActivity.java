package com.example.admin.ipcbycontentprovider;

import android.content.ContentValues;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ProviderActivity extends AppCompatActivity {

    private  static  final String TAG = "providerActiviry";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri bookUri = Uri.parse("content://com.example.admin.ipcbycontentprovider.provider/book");
        ContentValues values = new ContentValues();
        values.put("_id",6);
        values.put("name","c++");
        getContentResolver().insert(bookUri,values);

        Cursor bookCursor = getContentResolver().query(bookUri,new String[]{"_id","name"},null,null,null);
        while (bookCursor.moveToNext()){
            Book book = new Book();
            book.bookId=bookCursor.getInt(0);
            book.bookName=bookCursor.getString(1);
            Log.d(TAG,"查询书："+book.toString());
        }
        bookCursor.close();

        Uri userUri = Uri.parse("content://com.example.admin.ipcbycontentprovider.provider/user");
        Cursor userCursor = getContentResolver().query(userUri,new String[]{"_id","name","sex"},null,null,null);
        while (userCursor.moveToNext()){
         User user = new User();
            user.userId=userCursor.getInt(0);
            user.userName=userCursor.getString(1);
            user.isMale= userCursor.getInt(2)==1;
            Log.d(TAG,"查询用户："+user.toString());
        }
        userCursor.close();

       /* Uri uri = Uri.parse("content://com.example.admin.ipcbycontentprovider.provider");
        getContentResolver().query(uri,null,null,null,null);
        getContentResolver().query(uri,null,null,null,null);
        getContentResolver().query(uri,null,null,null,null);*/
    }
}
