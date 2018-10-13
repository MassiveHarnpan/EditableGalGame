package com.mh.galgame.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mh.galgame.preform.updater.LayerUpdater;

import java.util.Objects;

/**
 * 绘图类，用于记录绘制一张图所需要的参数，包括位置和可见性以及图片资源
 */
public class Layer extends Identified {

    // 决定以何种方式适应画布的大小，使得：宽度=画布宽度，高度=画布宽度，填充画布，把图片完全塞进画布
    // 老夫的游戏引擎禁止任何不等比例的拉伸
    public static final int MATCH_BY_X = 1;
    public static final int MATCH_BY_Y = 2;
    public static final int MATCH_FILL = 3;
    public static final int MATCH_FIT = 4;

    public static final double TRANSPARENT = 0.0D;
    public static final double TRANSLUCENT = 0.5D;
    public static final double OPAQUE = 1.0D;

    @Expose
    @SerializedName(value = "pic_id")
    private String picId;
    @Expose
    @SerializedName(value = "match_mode")
    private int matchMode;
    @Expose
    private double scale;
    @Expose
    private double x;
    @Expose
    private double y;
    @Expose
    private double opc; //0完全透明，1完全不透明


    public Layer(String id) {
        this(id, null, MATCH_FIT, 1, 0, 0, OPAQUE);
    }

    public Layer(String id, int matchMode, int scale) {
        this(id, null, matchMode, scale, 0, 0, OPAQUE);
    }

    public Layer(String id, String picId, int matchMode, double scale, double x, double y, double opc) {
        setId(id);
        this.picId = picId;
        this.matchMode = matchMode;
        this.scale = scale;
        this.x = x;
        this.y = y;
        this.opc = opc;
    }



    private LayerUpdater updater = null;

    public LayerUpdater getUpdater() {
        return updater;
    }

    public void setUpdater(LayerUpdater updater) {
        this.updater = updater;
    }




    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        String oldId = this.picId;
        this.picId = picId;
        if (!Objects.equals(oldId, picId) && updater != null) {
            updater.onResourceChanged(this, picId);
        }
    }

    public int getMatchMode() {
        return matchMode;
    }

    public void setMatchMode(int matchMode) {
        this.matchMode = matchMode;
        if (updater != null) {
            updater.onMatchModeChanged(this, matchMode);
        }
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
        if (updater != null) {
            updater.onScaleChanged(this, scale);
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        if (updater != null) {
            updater.onPositionChanged(this, x, y);
        }
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        if (updater != null) {
            updater.onPositionChanged(this, x, y);
        }
    }

    public double getOpc() {
        return opc;
    }

    public void setOpc(double opc) {
        this.opc = opc;
        if (updater != null) {
            updater.onOpacityChanged(this, opc);
        }
    }


    public boolean isShow() {
        return !(opc==TRANSPARENT);
    }
}
