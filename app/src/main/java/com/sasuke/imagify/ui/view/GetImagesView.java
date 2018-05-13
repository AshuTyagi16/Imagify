package com.sasuke.imagify.ui.view;

import com.sasuke.imagify.data.model.pojo.Result;

/**
 * Created by abc on 5/12/2018.
 */

public interface GetImagesView {
    void onGetImageSuccess(Result result);

    void onGetImageFailure(Throwable throwable);

    void onNetworkConnectionError();
}
