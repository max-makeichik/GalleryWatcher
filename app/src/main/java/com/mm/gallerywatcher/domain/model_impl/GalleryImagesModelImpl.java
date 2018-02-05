package com.mm.gallerywatcher.domain.model_impl;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.mm.gallerywatcher.data.repository.repository_interface.IGalleryImagesRepository;
import com.mm.gallerywatcher.domain.global.model.GalleryImage;
import com.mm.gallerywatcher.domain.model_interface.IGalleryImagesModel;

import io.reactivex.Observable;

/**
 * Created by Max Makeychik on 05-Feb-18.
 */

public class GalleryImagesModelImpl implements IGalleryImagesModel {

    private static final String TAG = GalleryImagesModelImpl.class.getSimpleName();

    private final IGalleryImagesRepository galleryImagesRepository;
    private final Context context;

    public GalleryImagesModelImpl(Context context, IGalleryImagesRepository galleryImagesRepository) {
        this.context = context;
        this.galleryImagesRepository = galleryImagesRepository;
    }

    @Override
    public Observable<GalleryImage> getImages() {
        Log.d(TAG, "getImages");
        return Observable.create(emitter -> {
            int logCounts = 0;
            Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
            String[] projection = {MediaStore.Images.Thumbnails.DATA};

            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor == null) {
                emitter.onError(new NullPointerException());
                return;
            }
            int columnIndexData = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
            //int columnIndexFolderName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            while (cursor.moveToNext()) {
                GalleryImage galleryItem = new GalleryImage();
                //galleryItem.setStrFolder(cursor.getString(columnIndexFolderName));
                galleryItem.setImagePath(cursor.getString(columnIndexData));

                Log.d(TAG, "getImages galleryItem " + galleryItem);
                emitter.onNext(galleryItem);
                ++logCounts;
            }
            Log.d(TAG, "getImages logCounts " + logCounts);
            cursor.close();
            emitter.onComplete();
        });
    }
}
