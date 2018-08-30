package com.example.admin.ipcbyaidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {

    private static final String TAG = "BMS";

    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);

    private CopyOnWriteArrayList mBookList = new CopyOnWriteArrayList<Book>();
   /* private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<IOnNewBookArrivedListener>();*/
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<IOnNewBookArrivedListener>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);

            final int N = mListenerList.beginBroadcast();
            mListenerList.finishBroadcast();
            Log.d(TAG, "注册监听者，当前数量:" + N);
           /* if (!mListenerList.contains(listener)) {
                mListenerList.add(listener);
            } else {
                Log.d(TAG, "已经存在该监听者");
            }

            Log.i(TAG, "监听者数量：" + mListenerList.size());*/
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {

            boolean success = mListenerList.unregister(listener);

            if (success) {
                Log.d(TAG, "解绑成功");
            } else {
                Log.d(TAG, "该监听者不存在，无法解绑");
            }
            final int N = mListenerList.beginBroadcast();
            mListenerList.finishBroadcast();
            Log.d(TAG, "解绑监听者后，当前数量：" + N);
           /* if (mListenerList.contains(listener)) {
                mListenerList.remove(listener);
                Log.i(TAG, "成功解除绑定");
            } else {
                Log.i(TAG, "该监听者不存在");
            }
            Log.i(TAG, "解除后，当前监听者数量：" + mListenerList.size());*/
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "IOS"));
        new Thread(new ServiceWorker()).start();//开启线程，每隔5S向书库增加一本新书并通知所有感兴趣的用户；
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private class ServiceWorker implements Runnable {
        @Override
        public void run() {
            while (!mIsServiceDestroyed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId, "new book#" + bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onNewBookArrived(Book newBook) throws  RemoteException {
        mBookList.add(newBook);

        final int N = mListenerList.beginBroadcast();{
            for(int i=0;i<N;i++){
                IOnNewBookArrivedListener l = mListenerList.getBroadcastItem(i);
                if(l!=null){
                    try{
                        l.onNewBookArrived(newBook);
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }
                }
            }
            mListenerList.finishBroadcast();
        }
       /* Log.i(TAG,"新书到达：通知监听者们："+mListenerList.size());
        for(int i=0;i<mListenerList.size();i++){
            IOnNewBookArrivedListener listener = mListenerList.get(i);
            Log.i(TAG,"新书到达：通知监听者:"+listener);
            listener.onNewBookArrived(newBook);

        }*/
    }


}