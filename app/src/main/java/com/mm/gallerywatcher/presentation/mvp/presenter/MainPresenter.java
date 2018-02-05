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

    public MainPresenter() {
        App.getGalleryImagesComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadImages();//todo!
    }

    public void loadImages() {
        getViewState().showLoading();
        disposables.add(galleryImagesModel.getImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(image -> {
                            getViewState().hideLoading();
                            getViewState().addImage(image);
                        },
                        throwable -> {
                            onError(throwable);
                            getViewState().showError(R.string.error_getting_images);
                        }));
    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

}
