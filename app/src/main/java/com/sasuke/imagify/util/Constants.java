package com.sasuke.imagify.util;

/**
 * Created by abc on 5/12/2018.
 */

public class Constants {

    private Constants() {
    }

    public static final String METHOD = "flickr.photos.search";
    public static final String FORMAT = "json";
    public static final String API_KEY = "d6531fa8510d0e43fd291856b6691465";
    public static final int NO_JSON_CALLBACK = 1;

    public static final int INITIAL_PAGE = 1;

    public static final int FLAG_UNCHANGED = 0;
    public static final int FLAG_CHANGED = 1;

    public static final String CACHE_DIR = "http-cache";
    public static final int CACHE_SIZE = 100 * 1000 * 1000;  //100 MB

    public static final int SPACING = 0;
    public static final int INITIAL_SPAN_COUNT = 2;

    public static final String BASE_URL = "https://api.flickr.com/services/";
}
