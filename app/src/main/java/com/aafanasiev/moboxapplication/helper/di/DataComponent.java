package com.aafanasiev.moboxapplication.helper.di;

import com.aafanasiev.moboxapplication.presenters.MainActivityPresenter;
import com.aafanasiev.moboxapplication.ui.TimeService;
import com.aafanasiev.moboxapplication.ui.activities.DetailActivity;
import com.aafanasiev.moboxapplication.ui.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by a.afanasiev on 16.09.2016.
 */

@Singleton
@Component(modules = DataModule.class)
public interface DataComponent {

    void inject(MainActivity activity);

    void inject(DetailActivity activity);

    void inject(MainActivityPresenter presenter);

    void inject(TimeService service);
}
