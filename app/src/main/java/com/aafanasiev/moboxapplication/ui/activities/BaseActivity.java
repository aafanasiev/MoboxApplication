package com.aafanasiev.moboxapplication.ui.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aafanasiev.moboxapplication.MoboxApplicationApp;
import com.aafanasiev.moboxapplication.R;
import com.aafanasiev.moboxapplication.helper.Constants;
import com.aafanasiev.moboxapplication.ui.TimeService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity {

    @Inject
    Context context;

    private BroadcastTimeReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MoboxApplicationApp)getApplicationContext()).dataComponent().inject(this);

        receiver = new BroadcastTimeReceiver();
        IntentFilter filter = new IntentFilter(Constants.ACTION);
        registerReceiver(receiver, filter);

        setReceiver();
    }

    private void setReceiver() {

        AlarmManager manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, Constants.FIRST_TIME);
        long frequency = Constants.TIME_LIMIT;
        Intent broadcastIntent = new Intent(context, TimeService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, broadcastIntent, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), frequency, pi);
    }

    class BroadcastTimeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long l = intent.getLongExtra(Constants.INTENT_NAME, 0L);
            displayDialog(l);
        }
    }

    private void displayDialog(Long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dialog_text) + " - " + new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(l))
                .setTitle(getResources().getString(R.string.dialog_title))
                .setPositiveButton(getResources().getString(R.string.dialog_button), (dialogInterface, i) -> {
                    dialogInterface.cancel();
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
