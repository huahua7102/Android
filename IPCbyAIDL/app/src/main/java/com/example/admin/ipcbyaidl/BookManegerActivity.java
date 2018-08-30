package com.example.admin.ipcbyaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class BookManegerActivity extends AppCompatActivity {

    private static  final  String TAG ="BookManagerActivity";
    private  static  final  int MESSAGE_NEW_BOOK_ARRIVED = 1;

    private IBookManager mRemoteBookManager;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_NEW_BOOK_ARRIVED:
                Log.d(TAG,"收到新书消息:"+msg.obj);
                break;
                default:
                    super.handleMessage(msg);

            }
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //将服务端返回的Binder对象转化为AIDL接口所属的类型
            IBookManager bookManager = IBookManager.Stub.asInterface(iBinder);

            try{
                mRemoteBookManager = bookManager;
                List<Book> list = bookManager.getBookList();//调用AIDL中的方法getBookList（）
                Log.i(TAG,"list的类型："+list.getClass().getCanonicalName());
                Log.i(TAG,"书单："+list.toString());
                Book newBook = new Book(3,"Android开发艺术探索");
                bookManager.addBook(newBook);//调用AIDL中的方法addBook(book)
                Log.i(TAG,"新书："+newBook);
                List<Book> newList = bookManager.getBookList();//调用AIDL中的方法getBookList（）
                Log.i(TAG,"新书单："+newList.toString());

                bookManager.registerListener(mOnNewBookArrivedListener);//调用AIDL中的方法registerListener（）
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mRemoteBookManager = null;
            Log.e(TAG,"binder死亡");

        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED,newBook).sendToTarget();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this,BookManagerService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        if(mRemoteBookManager != null&&mRemoteBookManager.asBinder().isBinderAlive()){
            try{
                Log.i(TAG,"解除监听者："+mOnNewBookArrivedListener);
                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener);
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }
       unbindService(mConnection);
        super.onDestroy();
    }
}
