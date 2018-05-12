package com.sasuke.imagify;

import android.app.Application;
import android.content.Context;

/**
 * Created by abc on 5/12/2018.
 */

public class Imagify extends Application {

    private static Imagify instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getAppContext() {
        return instance;
    }
}
