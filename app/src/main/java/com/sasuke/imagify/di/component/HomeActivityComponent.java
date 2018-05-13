package com.sasuke.imagify.di.component;

import com.sasuke.imagify.di.module.HomeActivityModule;
import com.sasuke.imagify.di.scope.PerActivityScope;
import com.sasuke.imagify.ui.activity.HomeActivity;

import dagger.Component;

/**
 * Created by abc on 5/14/2018.
 */

@PerActivityScope
@Component(modules = HomeActivityModule.class, dependencies = ImagifyApplicationComponent.class)
public interface HomeActivityComponent {
    void injectHomeActivity(HomeActivity homeActivity);
}
