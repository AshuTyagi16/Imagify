package com.sasuke.imagify.network;


import com.sasuke.imagify.model.pojo.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by abc on 5/11/2018.
 */

public interface FlickrService {

    @GET("rest")
    Call<Result> getImageForTag(@Query("method") String method,
                                @Query("format") String format,
                                @Query("api_key") String api_key,
                                @Query("nojsoncallback") int nojsoncallback,
                                @Query("tags") String tags,
                                @Query("page") int page);
}
