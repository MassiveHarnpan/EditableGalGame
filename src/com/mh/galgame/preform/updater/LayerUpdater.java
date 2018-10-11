package com.mh.galgame.preform.updater;

import com.mh.galgame.data.Layer;

public interface LayerUpdater {

    void onLayerAdded(Layer layer);
    void onLayerRemoved(Layer layer);
    void onPositionChanged(Layer layer, double x, double y);
    void onScaleChanged(Layer layer, double scale);
    void onMatchModeChanged(Layer layer, int matchMode);
    void onResourceChanged(Layer layer, String resId);
    void onOpacityChanged(Layer layer, double opc);
}
