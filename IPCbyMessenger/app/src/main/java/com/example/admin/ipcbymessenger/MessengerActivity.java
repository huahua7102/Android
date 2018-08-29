package com.example.admin.ipcbymessenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MessengerActivity extends AppCompatActivity {

    private static  final String TAG="MessengerActivity";
    private Messenger mService;

    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());
    private static  class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyConstants.MSG_FROM_SERVICE:
                    Log.i(TAG,"收到服务端的回复："+msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }
    private ServiceConnection mConncetion = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = new Messenger(iBinder);
            Message msg = Message.obtain(null,MyConstants.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg","你好，我是客户端");
            msg.setData(data);
            msg.replyTo=mGetReplyMessenger;
            try {
                mService.send(msg);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent= new Intent(this,MessengerService.class);
        bindService(intent,mConncetion, Context.BIND_AUTO_CREATE);
        /*Log.i("MessengerActivity","客户端");*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConncetion);
        super.onDestroy();
    }
}
