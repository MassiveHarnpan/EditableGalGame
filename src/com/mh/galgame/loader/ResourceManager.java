package com.mh.galgame.loader;

import com.mh.galgame.data.Line;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ResourceManager<I, S> {

    protected Map<String, I> images = new HashMap<>();
    protected Map<String, S> sounds = new HashMap<>();
    protected Map<String, Line> lines = new HashMap<>();
    protected List<Line> indexedLines = new ArrayList<>();

    protected File root;

    public void setRoot(File root) {
        this.root = root;
    }

    public File getRoot() {
        return root;
    }

    public I getImage(String id) {
        return images.get(id);
    }
    public S getSound(String id) {
        return sounds.get(id);
    }

    public Line getLine(String id) {
        return lines.get(id);
    }

    public Line getLineByIndex(int index) {
        return indexedLines.get(index);
    }

    protected void addImage(String id, I image) {
        images.put(id, image);
    }

    protected void addSound(String id, S sound) {
        sounds.put(id, sound);
    }

    protected void addLine(String id, Line line) {
        lines.put(id, line);
        indexedLines.add(line);
    }

    protected abstract I loadImage(String id, File file);
    protected abstract S loadSound(String id, File file);


    public int getLineCount() {
        return indexedLines.size();
    }
}
