package com.sasuke.imagify.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sasuke.imagify.data.model.pojo.Photo;
import com.sasuke.imagify.ui.fragment.PhotoViewFragment;

import java.util.List;

/**
 * Created by abc on 5/13/2018.
 */

public class ImagePagerAdapter extends FragmentStatePagerAdapter {

    private List<Photo> mPhotoList;

    public ImagePagerAdapter(Fragment fragment) {
        super(fragment.getChildFragmentManager());
    }

    @Override
    public int getCount() {
        return this.mPhotoList.size();
    }

    @Override
    public Fragment getItem(int position) {
        if (this.mPhotoList != null)
            return PhotoViewFragment.newInstance(this.mPhotoList.get(position));
        return null;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.mPhotoList = photoList;
        notifyDataSetChanged();
    }
}