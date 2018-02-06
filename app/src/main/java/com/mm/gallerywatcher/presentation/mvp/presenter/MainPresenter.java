package com.mm.gallerywatcher.presentation.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.mm.gallerywatcher.App;
import com.mm.gallerywatcher.R;
import com.mm.gallerywatcher.domain.model_interface.IGalleryImagesModel;
import com.mm.gallerywatcher.presentation.mvp.view.GalleryMvpView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Maksim Makeychik on 04.02.2018.
 */

@InjectViewState
public class MainPresenter extends BasePresenter<GalleryMvpView> {

    @Inject
    IGalleryImagesModel galleryImagesModel;

    private boolean isImagesLoading;

    public MainPresenter() {
        App.getGalleryImagesComponent().inject(this);
    }

    public void getImages() {
        getViewState().showLoading();
        getViewState().clearImages();
        loadImages();
    }

    public void loadImages() {
        if (isImagesLoading()) {
            return;
        }
        setImagesLoading(true);
        disposables.add(galleryImagesModel.getImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(image -> {
                            setImagesLoading(true);
                            getViewState().hideLoading();
                            getViewState().addImage(image);
                        },
                        throwable -> {
                            setImagesLoading(false);
                            onError(throwable);
                            getViewState().hideLoading();
                            getViewState().showError(R.string.error_getting_images);
                        }, () -> setImagesLoading(false)));
    }

    /**
     * Update images only if user loaded them earlier
     */
    public void updateImages() {
        if (!galleryImagesModel.isImagesLoaded()) {
            return;
        }
        loadImages();
    }

    public boolean isImagesLoading() {
        return isImagesLoading;
    }

    public void setImagesLoading(boolean imagesLoading) {
        isImagesLoading = imagesLoading;
    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        galleryImagesModel.clearImages();
    }
}
