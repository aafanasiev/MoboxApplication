package com.aafanasiev.moboxapplication.helper.di;

import android.content.Context;

import com.aafanasiev.moboxapplication.helper.Constants;
import com.aafanasiev.moboxapplication.model.ServerAPI.ServerAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {

    final protected Context context;

    public DataModule(Context context) {
        this.context = context;
    }

    protected ServerAPI getServerAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ServerAPI.class);
    }

    @Provides
    @Singleton
    public Context provideApplicationContext(){
        return context;
    }

    @Provides
    @Singleton
    public ServerAPI provideServerAPI(){
        return getServerAPI();
    }




}
