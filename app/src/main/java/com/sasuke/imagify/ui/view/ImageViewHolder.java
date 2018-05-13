package com.sasuke.imagify.ui.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.sasuke.imagify.R;
import com.sasuke.imagify.model.pojo.Photo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 5/12/2018.
 */

public class ImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_movie_image)
    ImageView mIvPhoto;

    private OnItemClickListsner onItemClickListsner;

    public ImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListsner != null)
                    onItemClickListsner.onItemClick(getAdapterPosition());
            }
        });
    }

    public void setImage(Photo photo) {
        Picasso.get().load("https://farm"
                + photo.getFarm()
                + ".staticflickr.com/"
                + photo.getServer()
                + "/"
                + photo.getId()
                + "_"
                + photo.getSecret()
                + ".jpg")
                .error(R.drawable.placeholder_image_error)
                .placeholder(R.drawable.placeholder_image_loading)
                .into(mIvPhoto);
    }

    public void setOnItemClickListener(OnItemClickListsner onItemClickListener) {
        this.onItemClickListsner = onItemClickListener;
    }

    public interface OnItemClickListsner {
        void onItemClick(int position);
    }
}
