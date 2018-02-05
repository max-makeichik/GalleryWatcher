package com.mm.gallerywatcher.di.component;

import com.mm.gallerywatcher.di.annotation.GalleryImagesScope;
import com.mm.gallerywatcher.di.module.GalleryImagesModule;
import com.mm.gallerywatcher.presentation.mvp.presenter.MainPresenter;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = {GalleryImagesModule.class})
@GalleryImagesScope
public interface GalleryImagesComponent {

    void inject(MainPresenter mainPresenter);
}
