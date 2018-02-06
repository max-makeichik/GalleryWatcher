package com.mm.gallerywatcher.presentation.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mm.gallerywatcher.R;
import com.mm.gallerywatcher.domain.global.model.GalleryImage;
import com.mm.gallerywatcher.presentation.ui.holder.GalleryImageViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max Makeychik on 05-Feb-18.
 */

public class GalleryImagesAdapter extends AnimatedRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<GalleryImage> items = new ArrayList<>();

    public GalleryImagesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GalleryImageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery_image, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        GalleryImageViewHolder galleryImageViewHolder = (GalleryImageViewHolder) holder;
        galleryImageViewHolder.bind(context, getItem(position));
    }

    private GalleryImage getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void addItem(GalleryImage image) {
        items.add(image);
        notifyItemInserted(items.size() - 1);
    }
}
