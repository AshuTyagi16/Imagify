package com.sasuke.imagify.di.component;

import com.sasuke.imagify.di.module.PhotoViewFragmentModule;
import com.sasuke.imagify.di.scope.PerActivityScope;
import com.sasuke.imagify.ui.fragment.PhotoViewFragment;

import dagger.Component;

/**
 * Created by abc on 5/14/2018.
 */

@PerActivityScope
@Component(modules = PhotoViewFragmentModule.class, dependencies = ImagifyApplicationComponent.class)
public interface PhotoViewFragmentComponent {

    void injectPhotoViewFragment(PhotoViewFragment photoViewFragment);
}
