package com.example.admin.ipcbycontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.Nullable;
import android.util.Log;

public class BookProvider extends ContentProvider {
    public BookProvider() {
    }
    private static  final String TAG= "BookProvider";

    public static final String AUTHORITY = "com.example.admin.ipcbycontentprovider.provider";
    public static  final  Uri BOOK_CONTENT_URI =Uri.parse("content://"+AUTHORITY+"/book");
    public static  final Uri USER_CONTENT_URI=Uri.parse("content://"+AUTHORITY+"/user");

    public static final int BOOK_URI_CODE = 0;
    public static  final  int USER_URI_CODE = 1;

    private static  final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY,"book",BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY,"user",USER_URI_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        Log.d(TAG,"on create,当前线程："+Thread.currentThread().getName());
        mContext=getContext();
        //ContentProvider创建时，初始化数据库（PS:实际使用中不推荐在主线程中进行耗时的数据库操作）
        initProviderData();
        return  true;
    }
        private  void initProviderData(){
            mDb = new DbOpenHelper(mContext).getWritableDatabase();
            mDb.execSQL("delete from "+DbOpenHelper.BOOK_TABLE_NAME);
            mDb.execSQL("delete from "+DbOpenHelper.USER_TABLE_NAME);
            mDb.execSQL("insert into book values(1,'Android');");
            mDb.execSQL("insert into book values(2,'IOS');");
            mDb.execSQL("insert into book values(3,'JAVA');");
            mDb.execSQL("insert into user values(4,'jake',1);");
            mDb.execSQL("insert into user  values(5,'jasmine',0);");

        }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG,"query,当前线程："+Thread.currentThread().getName());
        String table =  getTableName(uri);
        if(table == null)
            throw new IllegalArgumentException("非法URI"+uri);
        return  mDb.query(table,projection,selection,selectionArgs,null,null,sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.d(TAG,"getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Log.d(TAG,"insert");
        String table =getTableName(uri);
        if(table == null)
            throw new IllegalArgumentException("非法URI"+uri);
        mDb.insert(table,null,contentValues);
        mContext.getContentResolver().notifyChange(uri,null);
        return uri;
    }



    @Override
    public int delete(Uri uri, String s, String[] strings) {
        Log.d(TAG,"delete");
        String table = getTableName(uri);
        if(table==null)
            throw new IllegalArgumentException("非法URI"+uri);
        int count = mDb.delete(table,s,strings);
        if(count>=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        Log.d(TAG,"update");
        String table = getTableName(uri);
        if(table == null){
            throw new IllegalArgumentException("非法URI"+uri);
        }
        int row = mDb.update(table,contentValues,s,strings);
        if(row>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return row;
    }
    private String getTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)){
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return  tableName;
    }
}
