package com.example.admin.ipcbysocket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPServerService extends Service {
    public TCPServerService() {
    }

    private boolean mIsServiceDstroyed = false;
    private String[] mDefinedMessages = new String[]{"你好啊","在干嘛","天气好吗？","很开心啊"};

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mIsServiceDstroyed =true;
        super.onDestroy();
    }

    private class TcpServer implements Runnable {
        @SuppressWarnings("resource")

        @Override
        public void run() {
            ServerSocket serverSocket =null;
            try{
                serverSocket = new ServerSocket(8688);//监听本地8688端口

            }catch (IOException e){
               System.err.println("连接失败，端口号：8688");
                e.printStackTrace();
                return;
            }
            while(!mIsServiceDstroyed){
                try{
                    final Socket client = serverSocket.accept();
                    System.out.println("accept");
                    new Thread(){
                        @Override
                        public void run() {
                            try{
                                responseClient(client);
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        };
                    }.start();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void responseClient(Socket client)  throws  IOException{
        //用于接受客户端的消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //用于向客户端发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
        out.println("欢迎来到聊天室");
        while(!mIsServiceDstroyed){
            String str=in.readLine();
            System.out.println("来自客户端的消息："+str);
            if(str==null){
                break;
            }
            int i=new Random().nextInt(mDefinedMessages.length);
            String msg=mDefinedMessages[i];
            out.println(msg);
            System.out.println("send:"+msg);
        }
        System.out.println("client quit.");

        out.close();
        in.close();
        client.close();
    }

}
