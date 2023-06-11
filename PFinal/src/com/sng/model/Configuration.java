package com.sng.model;

import java.io.Serializable;

public class Configuration implements Serializable {
    private String imageDirectory;

    public Configuration() {
    }

    public Configuration(String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }

    public String getImageDirectory() {
        return imageDirectory;
    }

    public void setImageDirectory(String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }
}
