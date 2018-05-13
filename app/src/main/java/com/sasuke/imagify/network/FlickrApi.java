package com.sasuke.imagify.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sasuke.imagify.Imagify;
import com.sasuke.imagify.model.pojo.Result;
import com.sasuke.imagify.network.interceptor.CacheInterceptor;
import com.sasuke.imagify.network.interceptor.OfflineCacheInterceptor;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abc on 5/12/2018.
 */

public class FlickrApi {

    public static final String BASE_API_URL = "https://api.flickr.com/services/";

    private static FlickrApi instance;
    private FlickrService service;

    public static FlickrApi getInstance() {
        if (instance == null) {
            synchronized (FlickrApi.class) {
                if (instance == null) {
                    instance = new FlickrApi();
                    instance.init();
                }
            }
        }
        return instance;
    }

    private void init() {
        Gson gson = new GsonBuilder().create();
        OkHttpClient httpClient = createHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(FlickrService.class);
    }

    public Call<Result> getImageForTag(String method, String format, String api_key,
                                       int nojsoncallback, String tags, int page) {
        return service.getImageForTag(method, format, api_key, nojsoncallback, tags, page);
    }


    /********Interceptor********/

    private OkHttpClient createHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(logging);
        builder.addInterceptor(new OfflineCacheInterceptor());
        builder.addNetworkInterceptor(new CacheInterceptor());
        builder.cache(provideCache());

        return builder.build();
    }

    private Cache provideCache() {
        try {
            return new Cache(new File(Imagify.getAppContext().getCacheDir(), "http-cache"), 100 * 1000 * 1000);
        } catch (Exception e) {
            return null;
        }
    }
}
