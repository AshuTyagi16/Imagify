package com.sasuke.imagify.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sasuke.imagify.model.pojo.Photo;
import com.sasuke.imagify.ui.fragment.PhotoViewFragment;

import java.util.List;

/**
 * Created by abc on 5/13/2018.
 */

public class ImagePagerAdapter extends FragmentStatePagerAdapter {

    private List<Photo> mPhotoList;

    public ImagePagerAdapter(Fragment fragment, List<Photo> photoList) {
        super(fragment.getChildFragmentManager());
        this.mPhotoList = photoList;
    }

    @Override
    public int getCount() {
        return this.mPhotoList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoViewFragment.newInstance(this.mPhotoList.get(position));
    }
}