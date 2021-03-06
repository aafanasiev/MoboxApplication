package com.aafanasiev.moboxapplication.helper.di;

import com.aafanasiev.moboxapplication.presenters.MainActivityPresenter;
import com.aafanasiev.moboxapplication.ui.TimeService;
import com.aafanasiev.moboxapplication.ui.activities.BaseActivity;
import com.aafanasiev.moboxapplication.ui.activities.DetailActivity;
import com.aafanasiev.moboxapplication.ui.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = DataModule.class)
public interface DataComponent {

    void inject(MainActivity activity);

    void inject(DetailActivity activity);

    void inject(BaseActivity activity);

    void inject(MainActivityPresenter presenter);
}
