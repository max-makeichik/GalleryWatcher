package com.mm.gallerywatcher.domain.global.model;

import android.text.TextUtils;

/**
 * Created by Maksim Makeychik on 04.02.2018.
 */

public class GalleryImage {

    private String imagePath;
    private String name;
    private String size;

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "GalleryImage{" +
                "imagePath='" + imagePath + '\'' +
                '}';
    }

    public String getUrl() {
        return imagePath;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GalleryImage that = (GalleryImage) o;

        return imagePath.equals(that.imagePath);
    }

    @Override
    public int hashCode() {
        return imagePath.hashCode();
    }

    public void setName(String name) {
        if(TextUtils.isEmpty(name)) {
            this.name = name;
            return;
        }
        String[] parts = name.split("/");
        if(parts.length == 0) {
            this.name = name;
            return;
        }
        this.name = parts[parts.length - 1];
    }
}
