package com.mm.gallerywatcher.util.images;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.NONE;

/**
 * Created by Andrey V. Murzin on 30.06.17.
 */

public class LoadImageUtil {

    public static void loadGalleryImage(Context context, @Nullable String url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(NONE)
                .into(imageView);
    }

}
