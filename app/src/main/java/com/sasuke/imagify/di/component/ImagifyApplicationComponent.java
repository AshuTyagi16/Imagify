package com.sasuke.imagify.di.component;

import com.sasuke.imagify.util.db.ImagifyDatabaseAdapter;
import com.sasuke.imagify.di.module.DatabaseModule;
import com.sasuke.imagify.di.module.FlickrServiceModule;
import com.sasuke.imagify.di.module.NetworkManagerModule;
import com.sasuke.imagify.di.module.PicassoModule;
import com.sasuke.imagify.di.scope.ImagifyApplicationScope;
import com.sasuke.imagify.data.network.FlickrService;
import com.sasuke.imagify.util.NetworkUtil;
import com.squareup.picasso.Picasso;

import dagger.Component;

/**
 * Created by abc on 5/14/2018.
 */

@Component(modules = {FlickrServiceModule.class, DatabaseModule.class,
        PicassoModule.class, NetworkManagerModule.class})
@ImagifyApplicationScope
public interface ImagifyApplicationComponent {

    Picasso getPicasso();

    FlickrService getFlickrService();

    NetworkUtil getNetworkUtil();

    ImagifyDatabaseAdapter getDatabaseAdapter();
}
