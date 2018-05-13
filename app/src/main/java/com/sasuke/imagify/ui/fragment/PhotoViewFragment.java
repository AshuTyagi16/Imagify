package com.sasuke.imagify.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.sasuke.imagify.Imagify;
import com.sasuke.imagify.R;
import com.sasuke.imagify.di.component.DaggerPhotoViewFragmentComponent;
import com.sasuke.imagify.di.component.PhotoViewFragmentComponent;
import com.sasuke.imagify.model.pojo.Photo;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by abc on 5/13/2018.
 */

public class PhotoViewFragment extends BaseFragment {

    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;

    @Inject
    Picasso picasso;
    @Inject
    Gson gson;

    private static final String EXTRA_PHOTO = "photo";
    private Photo mPhoto;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_photo_view;
    }

    public static PhotoViewFragment newInstance(Photo photo) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_PHOTO, new Gson().toJson(photo));
        PhotoViewFragment fragment = new PhotoViewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PhotoViewFragmentComponent component = DaggerPhotoViewFragmentComponent.builder()
                .imagifyApplicationComponent(Imagify.get(getActivity()).getApplicationComponent())
                .build();

        component.injectPhotoViewFragment(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mPhoto = gson.fromJson(getArguments().getString(EXTRA_PHOTO), Photo.class);
            picasso.load("https://farm"
                    + mPhoto.getFarm()
                    + ".staticflickr.com/"
                    + mPhoto.getServer()
                    + "/"
                    + mPhoto.getId()
                    + "_"
                    + mPhoto.getSecret()
                    + ".jpg")
                    .error(R.drawable.placeholder_image_error)
                    .placeholder(R.drawable.placeholder_image_loading)
                    .into(mIvPhoto);
        }
    }
}
