package com.mh.galgame.loader;

import com.google.gson.*;
import com.mh.galgame.data.Layer;
import com.mh.galgame.data.Line;
import com.mh.galgame.loader.model.ModelRes;
import com.mh.galgame.loader.model.ModelResource;
import com.mh.galgame.preform.updater.LayerUpdater;
import com.mh.galgame.preform.updater.LineUpdater;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

// Image, Sound, graphic, Player
public abstract class GalGame<I, S, G, P> {

    private ResourceManager<I, S> res;

    public ResourceManager<I, S> getRes() {
        return res;
    }

    protected void setRes(ResourceManager<I, S> res) {
        this.res = res;
    }

    private File root;

    protected void setRoot(File root) {
        this.root = root;
    }

    public File getRoot() {
        return root;
    }

    private int width = 800;
    private int height = 600;


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }


    public void loadRes(File root, ResourceManager res) throws LoadingException, FileNotFoundException {
        if (root == null || !root.exists() || res == null) {
            throw new LoadingException();
        }
        this.root = root;
        this.res = res;
        Gson gson = new GsonBuilder().serializeNulls().create();
        JsonParser parser = new JsonParser();
        // Load index.json
        JsonObject o = parser.parse(new FileReader(new File(root, "index.json"))).getAsJsonObject();
        this.width = o.get("width").getAsInt();
        this.height = o.get("height").getAsInt();
        // Load res.json
        ModelRes mr = gson.fromJson(new FileReader(new File(root, "res.json")), ModelRes.class);
        for (ModelResource image : mr.images) {
            res.addImage(image.id, res.loadImage(image.id, new File(root, image.path)));
        }
        for (ModelResource sound : mr.sounds) {
            res.addSound(sound.id, res.loadSound(sound.id, new File(root, sound.path)));
        }
        // Load script.json
        JsonObject jo = parser.parse(new FileReader(new File(root, "script.json"))).getAsJsonObject();
        Line[] lines = gson.fromJson(jo.get("lines"), Line[].class);
        for (int i = 0; i <lines.length; i++) {
            Line line = lines[i];
            line.setIndex(res.getLineCount());
            res.addLine(line.getId(), line);
        }
    }

    public void prepare(File root) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        // Load prepare.json
        JsonObject o = parser.parse(new FileReader(new File(root, "prepare.json"))).getAsJsonObject();
        setPresentLine(res.getLine(o.get("line_id").getAsString()));

        // Load layers
        Layer[] layers = gson.fromJson(o.get("layers"), Layer[].class);
        for (int i = 0; i < layers.length; i++) {
            Layer layer = layers[i];
            addLayer(layer.getId(), layer);
        }
        // Load players
    }

    //region updaters and getters and setters
    private LayerUpdater layerUpdater;
    private LineUpdater lineUpdater;

    public LayerUpdater getLayerUpdater() {
        return layerUpdater;
    }

    public LineUpdater getLineUpdater() {
        return lineUpdater;
    }

    public void setLayerUpdater(LayerUpdater layerUpdater) {
        this.layerUpdater = layerUpdater;
    }

    public void setLineUpdater(LineUpdater lineUpdater) {
        this.lineUpdater = lineUpdater;
    }
    //endregion

    //region graphics and players
    private Map<String, Layer> layers = new HashMap<>();
    private Map<Layer, G> graphics = new HashMap<>();

    public abstract G createGraphic(Layer layer);

    public G getGraphic(Layer layer) {
        return graphics.get(layer);
    }

    public void addLayer(String id, Layer layer) {
        layer.setUpdater(layerUpdater);
        layers.put(id, layer);
        graphics.put(layer, createGraphic(layer));
        layerUpdater.onLayerAdded(layer);
    }

    public void removeLayer(String id) {
        Layer layer = layers.get(id);
        layer.setUpdater(null);
        layers.remove(id);
        graphics.remove(layer);
        layerUpdater.onLayerRemoved(layer);
    }
    //endregion


    private Line presentLine = null;

    public Line getPresentLine() {
        return presentLine;
    }

    public void setPresentLine(Line line) {
        this.presentLine = line;
        lineUpdater.onLineChanged(line);
    }

    public void setPresentLine(String id) {
        Line line = res.getLine(id);
        setPresentLine(line);
    }

    public void init(File root, ResourceManager<I, S> res) throws FileNotFoundException, LoadingException {
        loadRes(root, res);
        prepare(root);
    }

    public void go(String lineId) {
        Line line;
        if ("+".equals(lineId)) {
            int index = presentLine.getIndex() + 1;
            line = res.getLineByIndex(index);
        } else if ("-".equals(lineId)) {
            int index = presentLine.getIndex() - 1;
            line = res.getLineByIndex(index);
        } else {
            line = res.getLine(lineId);
        }
        setPresentLine(line);
    }

}
