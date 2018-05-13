package com.sasuke.imagify.di.module;

import android.content.Context;

import com.sasuke.imagify.di.scope.ImagifyApplicationScope;
import com.sasuke.imagify.util.NetworkUtil;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abc on 5/3/2018.
 */

@Module
public class NetworkManagerModule {

    @Provides
    @ImagifyApplicationScope
    public NetworkUtil getNetworkManager(Context context) {
        return new NetworkUtil(context);
    }
}
