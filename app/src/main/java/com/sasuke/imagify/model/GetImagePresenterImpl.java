package com.sasuke.imagify.model;

import com.sasuke.imagify.model.pojo.Result;
import com.sasuke.imagify.network.FlickrService;
import com.sasuke.imagify.presenter.GetImagesPresenter;
import com.sasuke.imagify.ui.view.GetImagesView;
import com.sasuke.imagify.util.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abc on 5/12/2018.
 */

public class GetImagePresenterImpl implements GetImagesPresenter {

    private GetImagesView mGetImagesView;
    private FlickrService flickrService;
    private NetworkUtil networkUtil;

    public GetImagePresenterImpl(FlickrService flickrService, GetImagesView getImagesView, NetworkUtil networkUtil) {
        this.flickrService = flickrService;
        this.mGetImagesView = getImagesView;
        this.networkUtil = networkUtil;
    }

    @Override
    public void checkConnectionAndGetImageForTag(String method, String format, String api_key, int nojsoncallback, String tags, int page) {
        if (networkUtil.isConnected()) {
            getImages(method, format, api_key, nojsoncallback, tags, page);
        } else {
            mGetImagesView.onNetworkConnectionError();
        }
    }

    @Override
    public void getImageForTag(String method, String format, String api_key, int nojsoncallback, String tags, int page) {
        getImages(method, format, api_key, nojsoncallback, tags, page);
    }

    private void getImages(String method, String format, String api_key, int nojsoncallback, String tags, int page) {
        flickrService
                .getImageForTag(method, format, api_key, nojsoncallback, tags, page)
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        mGetImagesView.onGetImageSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        mGetImagesView.onGetImageFailure(t);
                    }
                });
    }
}
