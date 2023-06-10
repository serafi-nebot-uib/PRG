package com.sng.view;

import java.awt.*;

public class SubImagen {
    private final Image image;

    public SubImagen(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public int width() {
        return this.image.getWidth(null);
    }

    public int height() {
        return this.image.getHeight(null);
    }
}
