package com.iot.diaper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class RequestHandler extends Service {

    public RequestHandler() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}

