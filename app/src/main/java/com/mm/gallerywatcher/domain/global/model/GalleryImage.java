package com.mm.gallerywatcher.domain.global.model;

import java.net.URLConnection;

/**
 * May be a folder with images or an image itself
 * <p>
 * Created by Maksim Makeychik on 04.02.2018.
 */

public class GalleryImage {

    private String strFolder;
    private String imagePath;
    private String name;
    private int size;

    public String getStrFolder() {
        return strFolder;
    }

    public void setStrFolder(String strFolder) {
        this.strFolder = strFolder;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isImage() {
        String mimeType = URLConnection.guessContentTypeFromName(strFolder);
        return mimeType != null && mimeType.startsWith("image");
    }

    @Override
    public String toString() {
        return "GalleryItem{" +
                "strFolder='" + strFolder + '\'' +
                ", imagePath=" + imagePath +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size + "";
    }
}
