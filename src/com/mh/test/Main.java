package com.mh.test;

import com.mh.galgame.data.Layer;
import com.mh.galgame.data.Line;
import com.mh.galgame.loader.GalGame;
import com.mh.galgame.loader.LoadingException;
import com.mh.galgame.loader.ResourceManager;
import com.mh.galgame.preform.updater.LayerUpdater;
import com.mh.galgame.preform.updater.LineUpdater;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

public class Main extends Application {

    static GalGame<Image, Media, ImageView, MediaPlayer> game;

    static File ROOT = new File("hop");

    public static void main(String[] args) throws FileNotFoundException, LoadingException {
        game = new MyGalGame();
        ResourceManager<Image, Media> res = new MyResourceManager();
        game.loadRes(ROOT, res);
        launch();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane stkRoot = new StackPane();
        StackPane stkGraphics = new StackPane();
        AnchorPane acrLine = new AnchorPane();
        Text txtLine = new Text();


        stkRoot.getChildren().addAll(stkGraphics, acrLine);
        acrLine.getChildren().add(txtLine);
        AnchorPane.setBottomAnchor(txtLine, 20.0);
        AnchorPane.setLeftAnchor(txtLine, 20.0);
        AnchorPane.setRightAnchor(txtLine, 20.0);


        LayerUpdater layerUpdater = new LayerUpdater() {
            @Override
            public void onLayerAdded(Layer layer) {
                ImageView imv = game.getGraphic(layer);
                stkGraphics.getChildren().add(imv);
                onResourceChanged(layer, layer.getPicId());
                onMatchModeChanged(layer, layer.getMatchMode());
                onOpacityChanged(layer, layer.getOpc());
                onPositionChanged(layer, layer.getX(), layer.getY());
                onScaleChanged(layer, layer.getScale());
            }

            @Override
            public void onLayerRemoved(Layer layer) {
                ImageView imv = game.getGraphic(layer);
                stkGraphics.getChildren().remove(imv);
            }

            @Override
            public void onPositionChanged(Layer layer, double x, double y) {
                ImageView imv = game.getGraphic(layer);
                imv.setTranslateX(x);
                imv.setTranslateY(y);
            }

            @Override
            public void onScaleChanged(Layer layer, double scale) {
                ImageView imv = game.getGraphic(layer);
                imv.setScaleX(scale);
                imv.setScaleY(scale);
            }

            @Override
            public void onMatchModeChanged(Layer layer, int matchMode) {
                ImageView imv = game.getGraphic(layer);
                Image image = imv.getImage();
                if (image == null) {
                    return;
                }
                double pp = game.getHeight() / game.getWidth();
                double pi = image.getHeight() / image.getWidth();
                switch (matchMode) {
                    case Layer.MATCH_BY_X:
                        imv.setFitWidth(game.getWidth());
                        break;
                    case Layer.MATCH_BY_Y:
                        imv.setFitHeight(game.getWidth());
                        break;
                    case Layer.MATCH_FILL:
                        if (pp > pi) {
                            imv.setFitHeight(game.getHeight());
                        } else {
                            imv.setFitWidth(game.getWidth());
                        }
                        break;
                    case Layer.MATCH_FIT:
                        if (pp < pi) {
                            imv.setFitHeight(game.getHeight());
                        } else {
                            imv.setFitWidth(game.getWidth());
                        }
                        break;
                }
            }

            @Override
            public void onResourceChanged(Layer layer, String resId) {
                ImageView imv = game.getGraphic(layer);
                imv.setImage(game.getRes().getImage(resId));
            }

            @Override
            public void onOpacityChanged(Layer layer, double opc) {
                ImageView imv = game.getGraphic(layer);
                imv.setOpacity(opc);
            }
        };
        LineUpdater lineUpdater = new LineUpdater() {
            @Override
            public void onLineChanged(Line line) {
                txtLine.setText(line.getText());
            }
        };

        game.setLayerUpdater(layerUpdater);
        game.setLineUpdater(lineUpdater);

        Scene scene = new Scene(stkRoot, game.getWidth(), game.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();

        game.prepare(ROOT);


    }
}
