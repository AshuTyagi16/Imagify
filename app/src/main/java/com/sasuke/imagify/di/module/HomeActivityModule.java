package com.sasuke.imagify.di.module;

import android.support.v7.widget.GridLayoutManager;

import com.sasuke.imagify.ui.adapter.ImagesAdapter;
import com.sasuke.imagify.di.scope.PerActivityScope;
import com.sasuke.imagify.data.model.GetImagePresenterImpl;
import com.sasuke.imagify.data.network.FlickrService;
import com.sasuke.imagify.ui.presenter.GetImagesPresenter;
import com.sasuke.imagify.ui.activity.HomeActivity;
import com.sasuke.imagify.ui.view.GetImagesView;
import com.sasuke.imagify.ui.view.LoadingListItemCreator;
import com.sasuke.imagify.util.Constants;
import com.sasuke.imagify.util.ItemDecorator;
import com.sasuke.imagify.util.NetworkUtil;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abc on 5/14/2018.
 */

@Module
public class HomeActivityModule {

    private HomeActivity homeActivity;
    private GetImagesView getImagesView;

    public HomeActivityModule(HomeActivity homeActivity, GetImagesView getImagesView) {
        this.homeActivity = homeActivity;
        this.getImagesView = getImagesView;
    }

    @Provides
    @PerActivityScope
    public GetImagesPresenter getMoviesPresenter(FlickrService flickrService, NetworkUtil networkUtil) {
        return new GetImagePresenterImpl(flickrService, getImagesView, networkUtil);
    }

    @Provides
    @PerActivityScope
    public ImagesAdapter getMoviesAdapter(Picasso picasso) {
        return new ImagesAdapter(picasso);
    }

    @Provides
    @PerActivityScope
    public ItemDecorator getItemDecorator() {
        return new ItemDecorator(Constants.SPACING);
    }

    @Provides
    @PerActivityScope
    public GridLayoutManager getGridLayoutManager() {
        return new GridLayoutManager(homeActivity, Constants.INITIAL_SPAN_COUNT);
    }

    @Provides
    @PerActivityScope
    public LoadingListItemCreator getLoadingListItemCreator() {
        return new LoadingListItemCreator();
    }
}
