package com.aafanasiev.moboxapplication.ui.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aafanasiev.moboxapplication.MoboxApplicationApp;
import com.aafanasiev.moboxapplication.R;
import com.aafanasiev.moboxapplication.helper.Constants;
import com.aafanasiev.moboxapplication.model.Article;
import com.aafanasiev.moboxapplication.model.ServerAPI.ServerAPI;
import com.aafanasiev.moboxapplication.presenters.MainActivityPresenter;
import com.aafanasiev.moboxapplication.presenters.interfaces.MainActivityInterface;
import com.aafanasiev.moboxapplication.ui.NewsDialog;
import com.aafanasiev.moboxapplication.ui.TimeService;
import com.aafanasiev.moboxapplication.ui.adapters.NewsAdapter;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.inject.Inject;

/**
 * Created by a.afanasiev on 16.09.2016.
 */
public class MainActivity extends BaseActivity implements MainActivityInterface {

    @Inject
    Context context;

    @Inject
    ServerAPI serverAPI;

    private List<Article> articleList = new ArrayList<>();

    private MainActivityPresenter presenter;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private RecyclerView.LayoutManager manager;

    private List<String> imageList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MoboxApplicationApp) getApplicationContext()).dataComponent().inject(this);

        presenter = new MainActivityPresenter(context, this);
        presenter.getArticlesList();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        adapter = new NewsAdapter(articleList, this);

        recyclerView.setAdapter(adapter);

        swipeRemoveItem();

        BroadcastTimeReceiver receiver = new BroadcastTimeReceiver();
        IntentFilter filter = new IntentFilter(Constants.ACTION);
        registerReceiver(receiver, filter);


        AlarmManager manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 5);
        long frequency = 120 * 1000;
        Intent i = new Intent(context, TimeService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), frequency, pi);


        displayDialog();
    }

    class BroadcastTimeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long l = intent.getLongExtra("sample_detect", 0L);
//            displayDialog(l);
        }
    }




    private void swipeRemoveItem() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.RIGHT) adapter.removeItem(position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void getArticles(List<Article> list) {
        this.articleList = list;
        adapter.upDate(articleList);

        imageList = Stream.of(list)
                .map(v ->v.getUrlToImage())
                .collect(Collectors.toList());


    }

    private void displayDialog() {



        NewsDialog dialog = new NewsDialog(context, "text", imageList.get(random()));
        dialog.show();

//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
////        builder.setMessage("Текущее время - " + new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(l))
////                .setPositiveButton("Close", (dialogInterface, i) -> {
////                    dialogInterface.cancel();
////                });
//
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogLayout = inflater.inflate(R.layout.dialog, null);
//        builder.setView(dialogLayout);
////        TextView tv = (TextView)findViewById(R.id.dialog_tv);
////        tv.setText(String.valueOf(l));
////        builder.setView(R.layout.dialog);
//
//
//        AlertDialog dialog = builder.create();
//
//        TextView tv1 = (TextView)dialog.findViewById(R.id.dialog_tv);
//        tv1.setText(String.valueOf(l));
//
////        LayoutInflater inflater = getLayoutInflater();
////        View dialogLayout = inflater.inflate(R.layout.dialog, null);
////        dialog.setView(dialogLayout);
////        TextView tv = (TextView)dialog.getWindow().findViewById(R.id.dialog_tv);
//////        ImageView iv = (ImageView)dialog.getWindow().findViewById(R.id.dialog_iv);
////        Picasso.with(context).load(articleList.get(random()).getUrlToImage()).into(iv);
////        tv.setText("Текущее время - " + new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(l));
////        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        dialog.show();

//        dialog.setOnShowListener(dialogInterface -> {
//
//        });
    }

    private int random() {
        Random random = new Random();
        if (articleList.size() > 0) {
            return random.nextInt((articleList.size()-1) - 0 + 1) + 0;
        } else return 0;
    }
}
