package com.mh.test;

import com.mh.galgame.data.Layer;
import com.mh.galgame.loader.GalGame;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MyGalGame extends GalGame<Image, Media, ImageView, MediaPlayer> {


    @Override
    public ImageView createGraphic(Layer layer) {
        ImageView imageView = new ImageView(getRes().getImage(layer.getPicId()));
        return imageView;
    }

}
