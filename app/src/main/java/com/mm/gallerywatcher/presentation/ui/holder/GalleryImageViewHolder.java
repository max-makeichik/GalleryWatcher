package com.mm.gallerywatcher.presentation.ui.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mm.gallerywatcher.R;
import com.mm.gallerywatcher.domain.global.model.GalleryImage;
import com.mm.gallerywatcher.util.Util;
import com.mm.gallerywatcher.util.images.LoadImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Max Makeychik on 05-Feb-18.
 */

public class GalleryImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_gallery_image_view)
    ImageView imageView;
    @BindView(R.id.item_gallery_image_name)
    TextView imageName;
    @BindView(R.id.item_gallery_image_size)
    TextView imageSize;
    @BindView(R.id.item_gallery_image_hash)
    TextView imageHash;

    public GalleryImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Context context, GalleryImage image) {
        imageName.setText(image.getName());
        imageSize.setText(context.getString(R.string.item_gallery_size, image.getSize()));
        imageHash.setText(Util.md5(image.getImagePath()));
        LoadImageUtil.loadGalleryImage(context, image.getImagePath(), imageView);
    }
}
