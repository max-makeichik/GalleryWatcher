package com.mm.gallerywatcher.domain.model_impl;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.mm.gallerywatcher.data.repository.repository_interface.IGalleryImagesRepository;
import com.mm.gallerywatcher.domain.global.model.GalleryImage;
import com.mm.gallerywatcher.domain.model_interface.IGalleryImagesModel;
import com.mm.gallerywatcher.util.exception.NoImagesFoundException;

import io.reactivex.Observable;

/**
 * Created by Max Makeychik on 05-Feb-18.
 */

public class GalleryImagesModelImpl implements IGalleryImagesModel {

    private static final String TAG = GalleryImagesModelImpl.class.getSimpleName();

    private final Context context;
    private final IGalleryImagesRepository galleryImagesRepository;

    private boolean imagesLoaded;

    public GalleryImagesModelImpl(Context context, IGalleryImagesRepository galleryImagesRepository) {
        this.context = context;
        this.galleryImagesRepository = galleryImagesRepository;
    }

    @Override
    public Observable<GalleryImage> getImages() {
        Log.d(TAG, "getImages");
        return Observable.create(emitter -> {
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor == null || cursor.getCount() == 0) {
                emitter.onError(new NoImagesFoundException());
                setImagesLoaded(false);
                return;
            }
            int columnIndexData = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
            while (cursor.moveToNext()) {
                GalleryImage galleryImage = new GalleryImage();
                galleryImage.setPath(cursor.getString(columnIndexData));
                if (galleryImagesRepository.addGalleryImage(galleryImage)) {
                    Log.d(TAG, "getImages galleryItem " + galleryImage);
                    emitter.onNext(galleryImage);
                }
            }
            cursor.close();
            emitter.onComplete();
            setImagesLoaded(true);
        });
    }

    @Override
    public void clearImages() {
        galleryImagesRepository.clearImages();
    }

    @Override
    public boolean isImagesLoaded() {
        return imagesLoaded;
    }

    @Override
    public void setImagesLoaded(boolean imagesLoaded) {
        this.imagesLoaded = imagesLoaded;
    }
}
