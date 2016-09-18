package com.aafanasiev.moboxapplication.ui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aafanasiev.moboxapplication.helper.Constants;

public class TimeService extends Service {

    public static String TAG = TimeService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: SERVICE CREATE");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i(TAG, "onStartCommand: SERVICE START");

        Intent serviceIntent = new Intent();
        serviceIntent.putExtra(Constants.INTENT_NAME, System.currentTimeMillis());
        serviceIntent.setAction(Constants.ACTION);
        sendBroadcast(serviceIntent);
        stopSelf();
        Log.i(TAG, "onStartCommand: STOP");

        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}