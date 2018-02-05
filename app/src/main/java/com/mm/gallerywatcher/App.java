package com.mm.gallerywatcher;

import android.app.Application;

import com.mm.gallerywatcher.di.component.AppComponent;
import com.mm.gallerywatcher.di.component.DaggerAppComponent;
import com.mm.gallerywatcher.di.component.DaggerGalleryImagesComponent;
import com.mm.gallerywatcher.di.component.GalleryImagesComponent;
import com.mm.gallerywatcher.di.module.ContextModule;

/**
 * Created by Andrey V. Murzin on 27.06.17.
 */

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();
    private static AppComponent appComponent;
    private static GalleryImagesComponent galleryImagesComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildAppComponent();
    }

    private AppComponent buildAppComponent() {
        return DaggerAppComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .build();
    }

    public static synchronized GalleryImagesComponent getGalleryImagesComponent() {
        if (galleryImagesComponent == null) {
            galleryImagesComponent = buildGalleryImagesComponent();
        }
        return galleryImagesComponent;
    }

    private static GalleryImagesComponent buildGalleryImagesComponent() {
        return DaggerGalleryImagesComponent.builder()
                .appComponent(appComponent)
                .build();
    }
}
