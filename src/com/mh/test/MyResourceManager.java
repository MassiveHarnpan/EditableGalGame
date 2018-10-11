package com.mh.test;

import com.mh.galgame.loader.ResourceManager;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MyResourceManager extends ResourceManager<Image, Media> {
    @Override
    protected Image loadImage(String id, File file) {
        try {
            return new Image(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Media loadSound(String id, File file) {
        return new Media(file.toURI().toString());
    }
}
