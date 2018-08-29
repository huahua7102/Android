package com.example.admin.ipcbysocket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TCPClientActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED =2;

    private Button mSendButton;
    private TextView mMessageTextView;
    private EditText mMessageEditText;

    private PrintWriter mPrintWriter;
    private Socket mClientSocket;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MESSAGE_RECEIVE_NEW_MSG:{
                    mMessageTextView.setText(mMessageTextView.getText()+(String)msg.obj);
                    break;
                }
                case MESSAGE_SOCKET_CONNECTED:{
                    mSendButton.setEnabled(true);
                    break;
                }
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpclient);
        mMessageTextView = (TextView)findViewById(R.id.msg_container);
        mSendButton = (Button)findViewById(R.id.send);
        mSendButton.setOnClickListener(this);
        mMessageEditText = (EditText)findViewById(R.id.msg);
        Intent service = new Intent(this,TCPServerService.class);
        startService(service);
        new Thread(){
            @Override
            public void run() {
                connectTCPServer();
            }
        }.start();
    }



    @Override
    protected void onDestroy() {
        if(mClientSocket !=null){
            try{
                mClientSocket.shutdownInput();
                mClientSocket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
    if(view==mSendButton){
        final String msg = mMessageEditText.getText().toString();
        if(!TextUtils.isEmpty(msg)&&mPrintWriter != null){
            mPrintWriter.println(msg);
            mMessageEditText.setText("");
            String time = formatDataTime(System.currentTimeMillis());
            final String showMsg = "self"+time +":"+msg+"\n";
            mMessageTextView.setText(mMessageTextView.getText()+showMsg);
        }
    }
    }

    @SuppressLint("SimpleDateFormat")
    private String formatDataTime(long time) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
    }

    private void connectTCPServer() {
        Socket socket =null;
        while(socket == null){
            try{
                socket=new Socket("localhost",8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                System.out.println("连接服务器成功");
            }catch (IOException e){
                SystemClock.sleep(1000);
                System.out.println("连接失败，重试");
            }
        }
        try{
            //接受服务器的消息
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(!TCPClientActivity.this.isFinishing()){
                String msg = br.readLine();
                System.out.println("收到："+msg);
                if(msg != null){
                    String time = formatDataTime(System.currentTimeMillis());
                    final String showMsg = "Server "+time+":"+msg+"\n";
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG,showMsg).sendToTarget();
                }
            }
            System.out.println("quit...");
            mPrintWriter.close();
            br.close();
            socket.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
