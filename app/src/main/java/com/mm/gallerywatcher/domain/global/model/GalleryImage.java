package com.mm.gallerywatcher.domain.global.model;

import com.mm.gallerywatcher.util.Util;

import java.io.File;

/**
 * May be a folder with images or an image itself
 * <p>
 * Created by Maksim Makeychik on 04.02.2018.
 */

public class GalleryImage {

    private String imagePath;
    private String name;
    private long size;

    public String getImagePath() {
        return imagePath;
    }

    public void setPath(String path) {
        this.imagePath = path;
        this.name = Util.getName(path);
        try {
            File f = new File(path);
            this.size = f.length();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return Util.getName(name);
    }

    public String getSize() {
        return Util.getHumanReadableByteCount(size, true);
    }

    @Override
    public String toString() {
        return "GalleryItem{" +
                ", imagePath=" + imagePath +
                '}';
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
}
