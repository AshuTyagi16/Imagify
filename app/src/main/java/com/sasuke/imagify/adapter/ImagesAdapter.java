package com.sasuke.imagify.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sasuke.imagify.R;
import com.sasuke.imagify.model.pojo.Photo;
import com.sasuke.imagify.ui.view.ImageViewHolder;

import java.util.List;

/**
 * Created by abc on 5/12/2018.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private List<Photo> mImageList;

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.setImage(this.mImageList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mImageList == null ? 0 : this.mImageList.size();
    }

    public void setImageList(List<Photo> list) {
        this.mImageList = list;
        notifyDataSetChanged();
    }
}
