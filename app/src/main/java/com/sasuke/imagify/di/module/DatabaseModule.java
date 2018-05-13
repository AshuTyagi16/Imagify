package com.sasuke.imagify.di.module;

import android.content.Context;

import com.sasuke.imagify.db.ImagifyDatabaseAdapter;
import com.sasuke.imagify.di.scope.ImagifyApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abc on 5/4/2018.
 */

@Module
public class DatabaseModule {

    private Context context;

    public DatabaseModule(Context context) {
        this.context = context;
    }

    @Provides
    @ImagifyApplicationScope
    public ImagifyDatabaseAdapter getDatabaseAdapter() {
        return ImagifyDatabaseAdapter.getInstance(this.context);
    }
}
