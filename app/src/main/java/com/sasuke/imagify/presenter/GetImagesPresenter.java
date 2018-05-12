package com.sasuke.imagify.presenter;

/**
 * Created by abc on 5/12/2018.
 */

public interface GetImagesPresenter {
    void getImageForTag(String method, String format, String api_key,
                        int nojsoncallback, String tags, int page);
}
