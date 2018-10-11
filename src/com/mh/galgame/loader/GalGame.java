package com.mh.galgame.loader;

import com.google.gson.*;
import com.mh.galgame.data.Layer;
import com.mh.galgame.data.Line;
import com.mh.galgame.data.Option;
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
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        // Load index.json
        JsonObject o = parser.parse(new FileReader(new File(root, "index.json"))).getAsJsonObject();
        this.width = o.get("width").getAsInt();
        this.height = o.get("height").getAsInt();
        // Load res.json
        o = parser.parse(new FileReader(new File(root, "res.json"))).getAsJsonObject();
        JsonArray images = o.get("images").getAsJsonArray();
        for (JsonElement je : images) {
            JsonObject jo = je.getAsJsonObject();
            String id = jo.get("id").getAsString();
            String rltPath = jo.get("path").getAsString();
            res.addImage(id, res.loadImage(id, new File(root, rltPath)));
        }
        JsonArray sounds = o.get("sounds").getAsJsonArray();
        for (JsonElement je : sounds) {
            JsonObject jo = je.getAsJsonObject();
            String id = jo.get("id").getAsString();
            String rltPath = jo.get("path").getAsString();
            res.addSound(id, res.loadSound(id, new File(root, rltPath)));
        }
        // Load script.json
        o = parser.parse(new FileReader(new File(root, "script.json"))).getAsJsonObject();
        JsonArray lines = o.get("lines").getAsJsonArray();
        for (JsonElement je : lines) {
            JsonObject jo = je.getAsJsonObject();
            String id = jo.get("id").getAsString();
            String text = jo.get("text").getAsString();
            String onEnter = jo.get("onenter").getAsString();
            Line line = new Line(id, text, onEnter);

            JsonArray options = jo.get("options").getAsJsonArray();
            for (JsonElement opt : options) {
                JsonObject joOpt = opt.getAsJsonObject();
                String hint = joOpt.get("hint").getAsString();
                String onSelect = joOpt.get("onselect").getAsString();
                Option option = new Option(hint, onSelect);
                line.addOption(option);
            }
            res.addLine(id, line);
        }
    }

    public void prepare(File root) throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        // Load prepare.json
        JsonObject o = parser.parse(new FileReader(new File(root, "prepare.json"))).getAsJsonObject();
        setPresentLine(res.getLine(o.get("lineid").getAsString()));
        // Load layers
        JsonArray layers = o.get("layers").getAsJsonArray();
        for (JsonElement je : layers) {
            JsonObject jo = je.getAsJsonObject();
            String id = jo.get("id").getAsString();
            String resId = jo.get("resid").getAsString();
            int matchMode = jo.get("matchmode").getAsInt();
            double x = jo.get("x").getAsDouble();
            double y = jo.get("y").getAsDouble();
            double scale = jo.get("scale").getAsDouble();
            double opc = jo.get("opc").getAsDouble();

            Layer layer = new Layer(id, resId, matchMode, scale, x, y, opc);
            addLayer(id, layer);
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

}
