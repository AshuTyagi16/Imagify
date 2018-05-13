package com.sasuke.imagify.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sasuke.imagify.event.PositionChangedEvent;
import com.sasuke.imagify.model.pojo.Photo;
import com.sasuke.imagify.ui.fragment.ImagePagerFragment;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by abc on 5/13/2018.
 */

public class ImagePagerActivity extends SingleFragmentActivity {

    private List<Photo> mPhoto;

    private static final String EXTRA_PHOTO_LIST = "photo_list";

    @Override
    protected Fragment createFragment() {
        return ImagePagerFragment.newInstance(mPhoto);
    }

    public static Intent newIntent(Context context, List<Photo> photoList) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putExtra(EXTRA_PHOTO_LIST, new Gson().toJson(photoList));
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            Type type = new TypeToken<List<Photo>>() {
            }.getType();
            mPhoto = new Gson().fromJson(getIntent().getStringExtra(EXTRA_PHOTO_LIST), type);
        }
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().postSticky(new PositionChangedEvent());
    }
}
