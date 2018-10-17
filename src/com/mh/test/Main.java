package com.mh.test;

import com.mh.galgame.data.Layer;
import com.mh.galgame.data.Line;
import com.mh.galgame.data.Option;
import com.mh.galgame.data.Player;
import com.mh.galgame.loader.GalGame;
import com.mh.galgame.loader.LoadingException;
import com.mh.galgame.loader.ResourceManager;
import com.mh.galgame.preform.updater.LayerUpdater;
import com.mh.galgame.preform.updater.LineUpdater;
import com.mh.galgame.preform.updater.PlayerUpdater;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
        VBox vbxLine = new VBox();
        Text txtLine = new Text();
        ScrollPane sclOptions = new ScrollPane();
        VBox vbxOptions = new VBox();

        sclOptions.setOnMouseClicked(event -> {
            //System.out.println("# Clicked on "+game.getPresentLine().getId());
            Line line = game.getPresentLine();
            String lineId;
            if (line != null && line.optionCount() == 0 && (lineId = line.getOnEmptyAction()) != null) {
                game.go(lineId);
            }
        });

        vbxLine.setPadding(new Insets(10));
        vbxOptions.setPadding(new Insets(10));
        vbxOptions.setSpacing(10);
        vbxLine.setBackground(new Background(new BackgroundFill(Color.color(1, 1, 1, 0.5), new CornerRadii(16), new Insets(0,0,0,0))));
        sclOptions.setBackground(null);
        vbxOptions.setBackground(null);


        sclOptions.setContent(vbxOptions);
        vbxLine.getChildren().addAll(txtLine, sclOptions);
        VBox.setVgrow(sclOptions, Priority.ALWAYS);


        stkRoot.getChildren().addAll(stkGraphics, acrLine);
        acrLine.getChildren().add(vbxLine);
        AnchorPane.setTopAnchor(vbxLine, 500.0);
        AnchorPane.setBottomAnchor(vbxLine, 20.0);
        AnchorPane.setLeftAnchor(vbxLine, 20.0);
        AnchorPane.setRightAnchor(vbxLine, 20.0);

        Font font = new Font("书体坊赵九江钢笔楷书", 36);
        txtLine.setFont(font);

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
                            imv.setFitWidth(game.getHeight() / pi);
                        } else {
                            imv.setFitWidth(game.getWidth());
                            imv.setFitHeight(game.getWidth() * pi);
                        }
                        break;
                    case Layer.MATCH_FIT:
                        if (pp < pi) {
                            imv.setFitHeight(game.getHeight());
                            imv.setFitWidth(game.getHeight() / pi);
                        } else {
                            imv.setFitWidth(game.getWidth());
                            imv.setFitHeight(game.getWidth() * pi);
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
                System.out.println("line: "+ line.getId()+" optionCount="+line.optionCount());
                if (line != null) {
                    txtLine.setText(line.getText());
                    vbxOptions.getChildren().clear();
                    for (int i = 0; i < line.optionCount(); i++) {
                        Option option = line.getOption(i);
                        System.out.println("   option: "+option);
                        Text txtOption = new Text(option.getHint());
                        txtOption.setFont(font);
                        txtOption.setOnMouseClicked(event -> {
                            event.consume();
                            //System.out.println("$ Clicked on "+option.getHint());
                            game.go(option.getOnSelectAction());
                        });
                        vbxOptions.getChildren().add(txtOption);
                    }
                } else {
                    txtLine.setText("");
                    vbxOptions.getChildren().clear();
                }
            }
        };
        PlayerUpdater playerUpdater = new PlayerUpdater() {
            @Override
            public void onPlayerAdded(Player player) {
                onPlayerOptChanged(player, player.getOpt());
            }

            @Override
            public void onPlayerRemoved(Player player) {
            }

            @Override
            public void onResIdChanged(Player player, String resId) {
                player.setOpt(Player.STOP);
                game.removePlayer(player.getId());
                game.addPlayer(player.getId(), player);
            }

            @Override
            public void onPlayerOptChanged(Player player, int opt) {
                MediaPlayer mediaPlayer = game.getPerformer(player);
                switch (opt) {
                    case Player.PLAY: mediaPlayer.play(); break;
                    case Player.PAUSE: mediaPlayer.pause(); break;
                    case Player.STOP: mediaPlayer.stop(); break;
                    case Player.LOOP: mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); mediaPlayer.play(); break;
                    case Player.RESTART: mediaPlayer.stop(); mediaPlayer.play(); break;
                }
            }
        };

        game.setLayerUpdater(layerUpdater);
        game.setLineUpdater(lineUpdater);
        game.setPlayerUpdater(playerUpdater);

        Scene scene = new Scene(stkRoot, game.getWidth(), game.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();

        game.prepare(ROOT);


    }
}
