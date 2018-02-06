package com.mm.gallerywatcher.data.repository.repository_interface;

import com.mm.gallerywatcher.domain.global.model.GalleryImage;

/**
 * Created by Max Makeychik on 05-Feb-18.
 */

public interface IGalleryImagesRepository {
    boolean addGalleryImage(GalleryImage galleryItem);

    void clearImages();
}
