package com.mm.gallerywatcher.data.repository.repository_impl;

import com.mm.gallerywatcher.data.repository.repository_interface.IGalleryImagesRepository;
import com.mm.gallerywatcher.domain.global.model.GalleryImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max Makeychik on 05-Feb-18.
 */

public class GalleryImagesRepositoryImpl implements IGalleryImagesRepository {

    private List<GalleryImage> galleryImages = new ArrayList<>();

    @Override
    public boolean addGalleryImage(GalleryImage galleryImage) {
        if (!galleryImages.contains(galleryImage)) {
            galleryImages.add(galleryImage);
            return true;
        }
        return false;
    }

    @Override
    public void clearImages() {
        galleryImages.clear();
    }
}
