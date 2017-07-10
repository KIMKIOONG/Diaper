package com.iot.diaper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class RequestHandler extends Service {
    NotificationManager Notifi_M;
    Notification Notifi;
    CallbackForResultFromStatus _callbackForResultFromStatus;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {//graph_activity에서 받은 intent입니다.
        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        _callbackForResultFromStatus = new CallbackForResultFromStatus();//여기다가 intent로 받은 id값 넣어주세요, handler 넣어주세요.
        _callbackForResultFromStatus.getResult();
        return START_STICKY;
    }

    class MyServiceHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(RequestHandler.this, MainActivity.class);//상태창에서 갈 클래 적어야됨
            PendingIntent pendingIntent = PendingIntent.getActivity(RequestHandler.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notifi = new Notification.Builder(getApplicationContext())
                    .setContentTitle("기저귀를 갈아주세요!!")
                    .setContentText("")//받은 json에서 시간을 넣어주세요...
                    .setTicker("알림!")
                    .setContentIntent(pendingIntent)
                    .build();

            Notifi.defaults = Notification.DEFAULT_SOUND;
            Notifi.flags = Notification.FLAG_ONLY_ALERT_ONCE;
            Notifi.flags = Notification.FLAG_AUTO_CANCEL;
            Notifi_M.notify(777, Notifi);
        }
    }
}

