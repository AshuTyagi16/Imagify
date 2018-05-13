package com.sasuke.imagify.di.module;

import android.content.Context;

import com.sasuke.imagify.di.scope.ImagifyApplicationScope;
import com.sasuke.imagify.di.qualifier.NetworkCacheQualifier;
import com.sasuke.imagify.di.qualifier.OfflineCacheQualifier;
import com.sasuke.imagify.data.network.interceptor.CacheInterceptor;
import com.sasuke.imagify.data.network.interceptor.OfflineCacheInterceptor;
import com.sasuke.imagify.util.Constants;
import com.sasuke.imagify.util.NetworkUtil;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by abc on 5/3/2018.
 */

@Module(includes = ContextModule.class)
public class NetworkModule {

    @Provides
    @ImagifyApplicationScope
    public OkHttpClient getOkHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache,
                                        @OfflineCacheQualifier Interceptor offlineCacheInterceptor,
                                        @NetworkCacheQualifier Interceptor networkCacheInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(offlineCacheInterceptor)
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(networkCacheInterceptor)
                .cache(cache);

        return builder.build();
    }

    @Provides
    @ImagifyApplicationScope
    public HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.i(message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    @Provides
    @ImagifyApplicationScope
    public Cache getCache(File cacheFile) {
        return new Cache(cacheFile, Constants.CACHE_SIZE);
    }

    @Provides
    @ImagifyApplicationScope
    public File getCacheFile(Context context) {
        return new File(context.getCacheDir(), Constants.CACHE_DIR);
    }

    @Provides
    @ImagifyApplicationScope
    @OfflineCacheQualifier
    public Interceptor getOfflineCacheInterceptor(NetworkUtil networkUtil) {
        return new OfflineCacheInterceptor(networkUtil);
    }

    @Provides
    @ImagifyApplicationScope
    @NetworkCacheQualifier
    public Interceptor getCacheInterceptor() {
        return new CacheInterceptor();
    }
}
