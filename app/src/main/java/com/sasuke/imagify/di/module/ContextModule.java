package com.sasuke.imagify.di.module;

import android.content.Context;

import com.sasuke.imagify.di.scope.ImagifyApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abc on 5/14/2018.
 */

@Module
public class ContextModule {

    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @ImagifyApplicationScope
    public Context getContext() {
        return context;
    }
}
