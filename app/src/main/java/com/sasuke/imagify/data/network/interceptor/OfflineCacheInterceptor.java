package com.sasuke.imagify.data.network.interceptor;


import com.sasuke.imagify.util.NetworkUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;

public class OfflineCacheInterceptor implements Interceptor {

    private NetworkUtil networkUtil;

    public OfflineCacheInterceptor(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!networkUtil.isConnected()) {
            request = request.newBuilder().cacheControl(new CacheControl.Builder().maxStale(7, TimeUnit.DAYS).build()).build();
        }
        return chain.proceed(request);
    }
}