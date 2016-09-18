package com.aafanasiev.moboxapplication.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.aafanasiev.moboxapplication.MoboxApplicationApp;
import com.aafanasiev.moboxapplication.R;
import com.aafanasiev.moboxapplication.model.Article;
import com.aafanasiev.moboxapplication.model.ServerAPI.ServerAPI;
import com.aafanasiev.moboxapplication.presenters.MainActivityPresenter;
import com.aafanasiev.moboxapplication.presenters.interfaces.MainActivityInterface;
import com.aafanasiev.moboxapplication.ui.adapters.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MoboxApplicationApp) getApplicationContext()).dataComponent().inject(this);

        presenter = new MainActivityPresenter(context, this);
        presenter.getArticlesList();

        initRecycleView();

        swipeRemoveItem();
    }

    private void initRecycleView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        adapter = new NewsAdapter(articleList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getArticles(List<Article> list) {
        this.articleList = list;
        adapter.upDate(articleList);
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
}