package com.aafanasiev.moboxapplication.ui;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aafanasiev.moboxapplication.MoboxApplicationApp;
import com.aafanasiev.moboxapplication.helper.Constants;

import javax.inject.Inject;

/**
 * Created by a.afanasiev on 16.09.2016.
 */
public class TimeService extends Service {

    public static String TAG = TimeService.class.getSimpleName();

    @Inject
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        ((MoboxApplicationApp)getApplicationContext()).dataComponent().inject(this);
        Log.i(TAG, "onCreate: SERVICE CREATE");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        Log.i(TAG, "onStartCommand: SERVICE START");

        Intent i = new Intent();
        i.putExtra("sample_detect", System.currentTimeMillis());
        i.setAction(Constants.ACTION);
        sendBroadcast(i);
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















