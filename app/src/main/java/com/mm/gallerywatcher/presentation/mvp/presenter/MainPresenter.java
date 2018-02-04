package com.mm.gallerywatcher.presentation.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.mm.gallerywatcher.presentation.mvp.view.GalleryMvpView;

/**
 * Created by Maksim Makeychik on 04.02.2018.
 */

@InjectViewState
public class MainPresenter extends BasePresenter<GalleryMvpView> {


    public void loadImages() {
        getViewState().showLoading();
    }
}
