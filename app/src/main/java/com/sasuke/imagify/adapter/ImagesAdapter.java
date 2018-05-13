package com.sasuke.imagify.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sasuke.imagify.R;
import com.sasuke.imagify.model.pojo.Photo;
import com.sasuke.imagify.ui.view.ImageViewHolder;
import com.sasuke.imagify.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 5/12/2018.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImageViewHolder> implements ImageViewHolder.OnItemClickListsner {

    private List<Photo> mImageList = new ArrayList<>();

    private OnItemClickListener onItemClickListsner;

    private Picasso picasso;

    public ImagesAdapter(Picasso picasso) {
        this.picasso = picasso;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_image, parent, false), picasso);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.setImage(this.mImageList.get(position));
        holder.setOnItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return this.mImageList == null ? 0 : this.mImageList.size();
    }


    @Override
    public void onItemClick(int position) {
        if (onItemClickListsner != null)
            onItemClickListsner.onItemClick(mImageList, position);
    }

    public void setImageList(List<Photo> list, int flag) {
        if (flag == Constants.FLAG_CHANGED)
            this.mImageList.clear();
        this.mImageList.addAll(list);
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListsner = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(List<Photo> photoList, int position);
    }
}
