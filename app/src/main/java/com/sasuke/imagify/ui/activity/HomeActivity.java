package com.sasuke.imagify.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.paginate.Paginate;
import com.sasuke.imagify.Imagify;
import com.sasuke.imagify.R;
import com.sasuke.imagify.adapter.ImagesAdapter;
import com.sasuke.imagify.db.ImagifyDatabaseAdapter;
import com.sasuke.imagify.db.ImagifyDatabaseManager;
import com.sasuke.imagify.event.PositionChangedEvent;
import com.sasuke.imagify.model.GetImagePresenterImpl;
import com.sasuke.imagify.model.pojo.Photo;
import com.sasuke.imagify.model.pojo.Result;
import com.sasuke.imagify.presenter.GetImagesPresenter;
import com.sasuke.imagify.ui.view.GetImagesView;
import com.sasuke.imagify.ui.view.LoadingListItemCreator;
import com.sasuke.imagify.util.Constants;
import com.sasuke.imagify.util.ItemDecorator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Created by abc on 5/12/2018.
 */

public class HomeActivity extends AppCompatActivity implements GetImagesView, Paginate.Callbacks, ImagesAdapter.OnItemClickListener {

    @BindView(R.id.rv_photos)
    RecyclerView mRvPhotos;
    @BindView(R.id.pb_images)
    CircularProgressBar mPbImages;
    @BindView(R.id.iv_placeholder)
    ImageView mIvPlaceholder;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ImagesAdapter mAdapter;
    private GetImagesPresenter mGetImagesPresenter;
    private GridLayoutManager mGridLayoutManager;

    private int SPAN_COUNT = 2;

    private LoadingListItemCreator mLoadingListItemCreator;
    private Paginate paginate;
    private boolean loading = false;
    private int totalPages;
    private int page = Constants.INITIAL_PAGE;

    private String currentQuery;
    private int flag = Constants.FLAG_UNCHANGED;

    public static int currentPosition;

    private ImagifyDatabaseAdapter databaseAdapter;
    private List<String> queries;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);
        mGridLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mRvPhotos.setLayoutManager(mGridLayoutManager);
        mRvPhotos.addItemDecoration(new ItemDecorator(0));
        mAdapter = new ImagesAdapter();
        mRvPhotos.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mLoadingListItemCreator = new LoadingListItemCreator();
        mGetImagesPresenter = new GetImagePresenterImpl(this);
        databaseAdapter = ImagifyDatabaseAdapter.getInstance(Imagify.getAppContext());
        setPagination();
        setSearchViewListeners();
        showSuggestions();
        showSearchPlaceholder();
        scrollToPosition();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_span_size_two:
                SPAN_COUNT = 2;
                break;
            case R.id.menu_span_size_three:
                SPAN_COUNT = 3;
                break;
            case R.id.menu_span_size_four:
                SPAN_COUNT = 4;
                break;
        }
        mGridLayoutManager.setSpanCount(SPAN_COUNT);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetImageSuccess(Result result) {
        loading = false;
        if (result != null && result.getPhotos() != null && result.getPhotos().getPhoto().size() > 0) {
            totalPages = result.getPhotos().getPages();
            page++;
            mAdapter.setImageList(result.getPhotos().getPhoto(), flag);
            showData();
        } else if (result != null && result.getCode() != 3) {
            showNoResultFoundPlaceholder();
            Toasty.error(this, getString(R.string.no_result_placeholder)).show();
        }
    }

    @Override
    public void onGetImageFailure(Throwable throwable) {
        loading = false;
        showErrorPlaceholder();
        Toasty.error(this, throwable.getMessage()).show();
    }

    @Override
    public void onNetworkConnectionError() {
        loading = false;
        showConnectionPlaceholder();
        Toasty.error(this, getString(R.string.no_internet_connection)).show();
    }

    @Override
    public void onLoadMore() {
        flag = Constants.FLAG_UNCHANGED;
        loading = true;
        getImages(currentQuery);
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return page == totalPages;
    }

    @Override
    public void onItemClick(List<Photo> photoList, int position) {
        currentPosition = position;
        Pair<View, String> pair = Pair.create(findViewById(R.id.iv_movie_image), "myimage");
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair);
        startActivity(ImagePagerActivity.newIntent(this, photoList), optionsCompat.toBundle());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onPositionChangedEvent(PositionChangedEvent event) {
        scrollToPosition();
    }

    private void setSearchViewListeners() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!queries.contains(query)) {
                    ImagifyDatabaseManager.addQuery(databaseAdapter, query);
                    showSuggestions();
                }
                showLoadingPlaceholder();
                flag = Constants.FLAG_CHANGED;
                currentQuery = query;
                currentPosition = 0;
                getImages(currentQuery);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void scrollToPosition() {
        mRvPhotos.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left,
                                       int top,
                                       int right,
                                       int bottom,
                                       int oldLeft,
                                       int oldTop,
                                       int oldRight,
                                       int oldBottom) {
                mRvPhotos.removeOnLayoutChangeListener(this);
                final RecyclerView.LayoutManager layoutManager = mRvPhotos.getLayoutManager();
                View viewAtPosition = layoutManager.findViewByPosition(HomeActivity.currentPosition);
                if (viewAtPosition == null || layoutManager
                        .isViewPartiallyVisible(viewAtPosition, false, true)) {
                    mRvPhotos.post(new Runnable() {
                        @Override
                        public void run() {
                            layoutManager.scrollToPosition(HomeActivity.currentPosition);
                        }
                    });
                }
            }
        });
    }

    private void getImages(String query) {
        if (mGetImagesPresenter != null) {
            mGetImagesPresenter.getImageForTag(Constants.METHOD,
                    Constants.FORMAT,
                    Constants.API_KEY,
                    Constants.NO_JSON_CALLBACK,
                    query,
                    page);
        }
    }

    private void showConnectionPlaceholder() {
        mRvPhotos.setVisibility(View.GONE);
        mPbImages.setVisibility(View.GONE);
        mIvPlaceholder.setImageResource(R.drawable.placeholder_image_error);
        mIvPlaceholder.setVisibility(View.VISIBLE);
    }

    private void showErrorPlaceholder() {
        mRvPhotos.setVisibility(View.GONE);
        mPbImages.setVisibility(View.GONE);
        mIvPlaceholder.setImageResource(R.drawable.placeholder_error_occured);
        mIvPlaceholder.setVisibility(View.VISIBLE);
    }

    private void showSearchPlaceholder() {
        mRvPhotos.setVisibility(View.GONE);
        mPbImages.setVisibility(View.GONE);
        mIvPlaceholder.setImageResource(R.drawable.placeholder_search);
        mIvPlaceholder.setVisibility(View.VISIBLE);
    }

    private void showNoResultFoundPlaceholder() {
        mRvPhotos.setVisibility(View.GONE);
        mPbImages.setVisibility(View.GONE);
        mIvPlaceholder.setImageResource(R.drawable.placeholder_no_result_found);
        mIvPlaceholder.setVisibility(View.VISIBLE);
    }

    private void showLoadingPlaceholder() {
        mRvPhotos.setVisibility(View.GONE);
        mIvPlaceholder.setVisibility(View.GONE);
        mPbImages.setVisibility(View.VISIBLE);
    }

    private void showData() {
        mPbImages.setVisibility(View.GONE);
        mIvPlaceholder.setVisibility(View.GONE);
        mRvPhotos.setVisibility(View.VISIBLE);
    }

    private void setPagination() {
        if (paginate != null)
            paginate.unbind();

        paginate = Paginate.with(mRvPhotos, this)
                .addLoadingListItem(true)
                .setLoadingListItemCreator(mLoadingListItemCreator)
                .build();
    }

    private void showSuggestions() {
        queries = ImagifyDatabaseManager.getAllQueries(databaseAdapter);
        if (queries.size() > 0) {
            String[] suggestions = new String[queries.size()];
            for (int i = 0; i < queries.size(); i++) {
                suggestions[i] = queries.get(i);
            }
            searchView.setSuggestions(suggestions);
        }
    }
}
