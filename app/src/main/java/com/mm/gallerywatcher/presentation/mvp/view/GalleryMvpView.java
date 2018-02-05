package com.mm.gallerywatcher.presentation.mvp.view;

import com.mm.gallerywatcher.domain.global.model.GalleryImage;

/**
 * Created by Maksim Makeychik on 04.02.2018.
 */

public interface GalleryMvpView extends BaseMvpView {
    void addImage(GalleryImage images);
}
