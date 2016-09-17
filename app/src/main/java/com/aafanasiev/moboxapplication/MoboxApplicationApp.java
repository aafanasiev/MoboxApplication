package com.aafanasiev.moboxapplication;

import android.app.Application;

import com.aafanasiev.moboxapplication.helper.di.DaggerDataComponent;
import com.aafanasiev.moboxapplication.helper.di.DataComponent;
import com.aafanasiev.moboxapplication.helper.di.DataModule;

/**
 * Created by a.afanasiev on 16.09.2016.
 */
public class MoboxApplicationApp extends Application {

    private DataComponent dataComponent;
    public DataComponent dataComponent(){
        return dataComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDaggerGraph();
    }

    private void initDaggerGraph() {
        dataComponent = DaggerDataComponent.builder()
                .dataModule(new DataModule(this))
                .build();
    }


}
