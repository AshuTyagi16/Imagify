package com.sasuke.imagify.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.sasuke.imagify.R;
import com.sasuke.imagify.adapter.ImagesAdapter;
import com.sasuke.imagify.model.GetImagePresenterImpl;
import com.sasuke.imagify.model.pojo.Result;
import com.sasuke.imagify.presenter.GetImagesPresenter;
import com.sasuke.imagify.ui.view.GetImagesView;
import com.sasuke.imagify.util.Constants;
import com.sasuke.imagify.util.ItemDecorator;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Created by abc on 5/12/2018.
 */

public class HomeActivity extends AppCompatActivity implements GetImagesView {

    @BindView(R.id.rv_photos)
    RecyclerView mRvPhotos;

    @BindView(R.id.pb_images)
    CircularProgressBar mPbImages;

    @BindView(R.id.iv_placeholder)
    ImageView mIvPlaceholder;

    private int SPAN_COUNT = 2;

    private ImagesAdapter mAdapter;
    private GetImagesPresenter mGetImagesPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRvPhotos.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        mRvPhotos.addItemDecoration(new ItemDecorator(0));
        mAdapter = new ImagesAdapter();
        mRvPhotos.setAdapter(mAdapter);
        mGetImagesPresenter = new GetImagePresenterImpl(this);
        getImages();
    }

    @Override
    public void onGetImageSuccess(Result result) {
        if (result != null) {
            mAdapter.setImageList(result.getPhotos().getPhoto());
            showData();
        } else {
        }
    }

    @Override
    public void onGetImageFailure(Throwable throwable) {
        showErrorPlaceholder();
        Toasty.error(this, throwable.getMessage());
    }

    @Override
    public void onNetworkConnectionError() {
        showConnectionPlaceholder();
        Toasty.error(this, getString(R.string.no_internet_connection));
    }

    private void getImages() {
        mGetImagesPresenter.getImageForTag(Constants.METHOD, Constants.FORMAT, Constants.API_KEY, Constants.NO_JSON_CALLBACK, "delhi", 1);
    }

    private void showConnectionPlaceholder() {
        mRvPhotos.setVisibility(View.GONE);
        mPbImages.setVisibility(View.GONE);
        mIvPlaceholder.setImageResource(R.drawable.placeholder_image_error);
        mIvPlaceholder.setVisibility(View.VISIBLE);
    }

    private void showErrorPlaceholder() {
        mRvPhotos.setVisibility(View.GONE);
        mPbImages.setVisibility(View.GONE);
        mIvPlaceholder.setImageResource(R.drawable.placeholder_error_occured);
        mIvPlaceholder.setVisibility(View.VISIBLE);
    }

    private void showData() {
        mPbImages.setVisibility(View.GONE);
        mIvPlaceholder.setVisibility(View.GONE);
        mRvPhotos.setVisibility(View.VISIBLE);
    }
}