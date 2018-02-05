package com.mm.gallerywatcher.domain.model_interface;

import com.mm.gallerywatcher.domain.global.model.GalleryImage;

import io.reactivex.Observable;

/**
 * Created by Max Makeychik on 05-Feb-18.
 */

public interface IGalleryImagesModel {

    Observable<GalleryImage> getImages();

}
