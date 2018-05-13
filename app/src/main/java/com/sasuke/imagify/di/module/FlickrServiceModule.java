package com.sasuke.imagify.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sasuke.imagify.di.scope.ImagifyApplicationScope;
import com.sasuke.imagify.data.network.FlickrService;
import com.sasuke.imagify.util.Constants;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abc on 5/14/2018.
 */

@Module(includes = NetworkModule.class)
public class FlickrServiceModule {

    @Provides
    @ImagifyApplicationScope
    public FlickrService getFlickrService(Retrofit retrofit) {
        return retrofit.create(FlickrService.class);
    }

    @Provides
    @ImagifyApplicationScope
    public Retrofit getRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @ImagifyApplicationScope
    public Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }
}
