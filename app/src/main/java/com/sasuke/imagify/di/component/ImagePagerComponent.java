package com.sasuke.imagify.di.component;

import com.sasuke.imagify.di.module.ImagePagerFragmentModule;
import com.sasuke.imagify.ui.fragment.ImagePagerFragment;

import dagger.Component;

/**
 * Created by abc on 5/14/2018.
 */

@Component(modules = ImagePagerFragmentModule.class)
public interface ImagePagerComponent {

    void injectPagerFragment(ImagePagerFragment pagerFragment);
}
