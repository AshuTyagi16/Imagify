package com.sasuke.imagify.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sasuke.imagify.R;
import com.sasuke.imagify.adapter.ImagePagerAdapter;
import com.sasuke.imagify.model.pojo.Photo;
import com.sasuke.imagify.ui.activity.HomeActivity;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;

/**
 * Created by abc on 5/13/2018.
 */

public class ImagePagerFragment extends BaseFragment {

    @BindView(R.id.vp_images)
    ViewPager mVpImages;

    private List<Photo> mPhotoList;
    private ImagePagerAdapter mAdapter;

    private static final String EXTRA_PHOTO_LIST = "photo_list";

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_image_pager;
    }

    public static ImagePagerFragment newInstance(List<Photo> photoList) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_PHOTO_LIST, new Gson().toJson(photoList));
        ImagePagerFragment fragment = new ImagePagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Type type = new TypeToken<List<Photo>>() {
            }.getType();
            mPhotoList = new Gson().fromJson(getArguments().getString(EXTRA_PHOTO_LIST), type);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new ImagePagerAdapter(this, mPhotoList);
        mVpImages.setAdapter(mAdapter);
        mVpImages.setCurrentItem(HomeActivity.currentPosition);
        mVpImages.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                HomeActivity.currentPosition = position;
            }
        });
    }
}
