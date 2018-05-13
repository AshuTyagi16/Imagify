package com.sasuke.imagify.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sasuke.imagify.di.scope.PerActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abc on 5/14/2018.
 */

@Module
public class PhotoViewFragmentModule {

    @Provides
    @PerActivityScope
    public Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }
}
