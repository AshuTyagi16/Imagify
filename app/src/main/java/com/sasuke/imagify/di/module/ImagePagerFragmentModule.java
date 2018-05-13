package com.sasuke.imagify.di.module;

import android.support.v4.app.Fragment;

import com.sasuke.imagify.ui.adapter.ImagePagerAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abc on 5/14/2018.
 */

@Module
public class ImagePagerFragmentModule {

    private Fragment fragment;

    public ImagePagerFragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    public ImagePagerAdapter getImagePagerAdapter() {
        return new ImagePagerAdapter(fragment);
    }
}
