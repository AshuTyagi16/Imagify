package com.sasuke.imagify.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sasuke.imagify.R;
import com.sasuke.imagify.adapter.ImagePagerAdapter;
import com.sasuke.imagify.model.pojo.Photo;
import com.sasuke.imagify.ui.activity.HomeActivity;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

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

        prepareSharedElementTransition();

        // Avoid a postponeEnterTransition on orientation change, and postpone only of first creation.
        if (savedInstanceState == null) {
            postponeEnterTransition();
        }
    }

    private void prepareSharedElementTransition() {
        Transition transition = TransitionInflater.from(getContext())
                .inflateTransition(R.transition.image_shared_element_transition);

        setSharedElementEnterTransition(transition);

        // A similar mapping is set at the GridFragment with a setExitSharedElementCallback.
        setEnterSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        // Locate the image view at the primary fragment (the ImageFragment that is currently
                        // visible). To locate the fragment, call instantiateItem with the selection position.
                        // At this stage, the method will simply return the fragment at the position and will
                        // not create a new one.
                        Fragment currentFragment = (Fragment) mVpImages.getAdapter()
                                .instantiateItem(mVpImages, HomeActivity.currentPosition);
                        View view = currentFragment.getView();
                        if (view == null) {
                            return;
                        }

                        // Map the first shared element name to the child ImageView.
                        sharedElements.put(names.get(0), view.findViewById(R.id.iv_photo));
                    }
                });
    }
}
