package com.mm.gallerywatcher.presentation.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mm.gallerywatcher.R;
import com.mm.gallerywatcher.domain.global.model.GalleryImage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Maksim Makeychik on 05.02.2018.
 */

public class GalleryImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<GalleryImage> items;

    public GalleryImagesAdapter(Context context, List<GalleryImage> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GalleryImageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery_image, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GalleryImageViewHolder viewHolder = (GalleryImageViewHolder) holder;
        viewHolder.bind(getItem(position));
    }

    private GalleryImage getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class GalleryImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_gallery_image_view)
        ImageView imageView;
        @BindView(R.id.item_gallery_name)
        TextView name;
        @BindView(R.id.item_gallery_size)
        TextView size;

        public GalleryImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(GalleryImage galleryImage) {
            Glide.with(context)
                    .load(galleryImage.getUrl())
                    .thumbnail(0.5f)
                    .into(imageView);
            name.setText(galleryImage.getName());
            size.setText(galleryImage.getSize() + " Mb");
        }
    }
}
