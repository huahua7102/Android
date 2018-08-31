package com.example.admin.servicebestpracticebyasynctask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDoneException;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;

public class DownloadService extends Service {

    private DownloadTask downloadTask;

    private  String downloadUrl;
    private  DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1,getNotification("正在下载中.....",progress));
        }

        @Override
        public void onSuccess() {

            downloadTask = null;
            //下载成功时将前台服务通知关闭，并创建一个下载成功的通知
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("下载成功！",-1));
            Toast.makeText(DownloadService.this,"下载成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            //下载失败时将前台服务通知关闭，并创建一个下载失败的通知
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("下载失败",-1));
            Toast.makeText(DownloadService.this,"下载失败",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            Toast.makeText(DownloadService.this,"暂停",Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this,"取消",Toast.LENGTH_SHORT).show();
        }
    };


    private  DownloadBinder mBinder = new DownloadBinder();


    private NotificationManager getNotificationManager() {
        return (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress) {

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder =new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        if(progress>=0){
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }


     class DownloadBinder extends Binder {
          public void startDownload(String url){
              if(downloadTask == null){
                  downloadUrl =url;
                  downloadTask = new DownloadTask(listener);
                  downloadTask.execute(downloadUrl);
                  startForeground(1,getNotification("正在下载中。。。。",0));

                  Toast.makeText(DownloadService.this,"开始下载。。。",Toast.LENGTH_SHORT).show();
              }
          }
         public void pauseDownload(){
             if(downloadTask != null){
                 downloadTask.pausedDownload();
             }
         }
         public void cancelDownload(){
             if(downloadTask != null){
                 downloadTask.cancelDownload();
             }
             if(downloadUrl != null){
                 //1取消下载时需要将文件删除，并将通知关闭
                 String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                 String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                 File file = new File(directory +fileName);
                 if(file.exists())
                     file.delete();
                 getNotificationManager().cancel(1);
                 stopForeground(true);
                 Toast.makeText(DownloadService.this,"取消",Toast.LENGTH_SHORT).show();
             }
         }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
