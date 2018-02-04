package com.mm.gallerywatcher.presentation.mvp.presenter;

import com.arellomobile.mvp.MvpPresenter;
import com.mm.gallerywatcher.presentation.mvp.view.BaseMvpView;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Andrey V. Murzin on 29.06.17.
 */
public class BasePresenter<View extends BaseMvpView> extends MvpPresenter<View> {

    protected CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposables != null && !disposables.isDisposed()) {
            disposables.clear();
        }
    }
}