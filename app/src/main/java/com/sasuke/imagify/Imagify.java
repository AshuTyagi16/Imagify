package com.sasuke.imagify;

import android.app.Activity;
import android.app.Application;

import com.sasuke.imagify.di.component.DaggerImagifyApplicationComponent;
import com.sasuke.imagify.di.component.ImagifyApplicationComponent;
import com.sasuke.imagify.di.module.ContextModule;
import com.sasuke.imagify.di.module.DatabaseModule;

import timber.log.Timber;

/**
 * Created by abc on 5/12/2018.
 */

public class Imagify extends Application {

    private ImagifyApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        component = DaggerImagifyApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .databaseModule(new DatabaseModule(this))
                .build();
    }

    public static Imagify get(Activity activity) {
        return (Imagify) activity.getApplication();
    }

    public ImagifyApplicationComponent getApplicationComponent() {
        return component;
    }

}
