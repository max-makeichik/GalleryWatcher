package com.mm.gallerywatcher.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mm.gallerywatcher.data.repository.repository_impl.GalleryImagesRepositoryImpl;
import com.mm.gallerywatcher.data.repository.repository_interface.IGalleryImagesRepository;
import com.mm.gallerywatcher.di.annotation.GalleryImagesScope;
import com.mm.gallerywatcher.domain.model_impl.GalleryImagesModelImpl;
import com.mm.gallerywatcher.domain.model_interface.IGalleryImagesModel;

import dagger.Module;
import dagger.Provides;

@Module
public class GalleryImagesModule {
    @Provides
    @GalleryImagesScope
    @NonNull
    public IGalleryImagesModel getGalleryImagesModel(Context context) {
        return new GalleryImagesModelImpl(context, getGalleryImagesRepository());
    }

    @Provides
    @GalleryImagesScope
    @NonNull
    public IGalleryImagesRepository getGalleryImagesRepository() {
        return new GalleryImagesRepositoryImpl();
    }
}
