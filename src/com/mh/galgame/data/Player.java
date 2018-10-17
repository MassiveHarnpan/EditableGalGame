package com.mh.galgame.data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mh.galgame.preform.updater.PlayerUpdater;

public class Player extends Identified {

    public static final int PLAY = 1;
    public static final int STOP = 2;
    public static final int PAUSE = 3;
    public static final int LOOP = 4;
    public static final int RESTART = 5;

    @Expose
    @SerializedName(value = "res_id")
    private String resId;
    @Expose
    private int opt;

    public Player(String id, String resId) {
        setId(id);
        this.resId = resId;
        opt = 0;
    }

    public Player(String id) {
        this(id, null);
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
        if (updater!=null) {
            updater.onResIdChanged(this, resId);
        }
    }

    public void setOpt(int opt) {
        this.opt = opt;
        if (updater!=null) {
            updater.onPlayerOptChanged(this, opt);
        }
    }

    public int getOpt() {
        return opt;
    }

    @Expose(serialize = false, deserialize = false)
    PlayerUpdater updater;

    public PlayerUpdater getUpdater() {
        return updater;
    }

    public void setUpdater(PlayerUpdater updater) {
        this.updater = updater;
    }
}
