package com.aafanasiev.moboxapplication.presenters;

import android.content.Context;
import android.util.Log;

import com.aafanasiev.moboxapplication.MoboxApplicationApp;
import com.aafanasiev.moboxapplication.model.Article;
import com.aafanasiev.moboxapplication.model.BaseModel;
import com.aafanasiev.moboxapplication.model.ServerAPI.ServerAPI;
import com.aafanasiev.moboxapplication.presenters.interfaces.MainActivityInterface;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter {

    public static String TAG = MainActivityPresenter.class.getSimpleName();

    @Inject
    Context context;

    @Inject
    ServerAPI serverAPI;

    private List<Article> articleList;
    private List<Article> list;

    private MainActivityInterface mainActivityInterface;

    public MainActivityPresenter(Context context, MainActivityInterface mainActivityInterface) {
        ((MoboxApplicationApp) context).dataComponent().inject(this);
        this.context = context;
        this.mainActivityInterface = mainActivityInterface;
    }

    public void getArticlesList() {

        articleList = new ArrayList<>();

        Call<BaseModel> call = serverAPI.getModel();
        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {

                list = response.body().getArticles();

                articleList = Stream.of(list)
                        .map(v -> new Article(v.getDescription(), v.getUrlToImage()))
                        .collect(Collectors.toList());

                mainActivityInterface.getArticles(articleList);


            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i(TAG, "onFailure: Retrofit error");
            }
        });

    }
}
