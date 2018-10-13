package com.mh.galgame.data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player extends Identified {

    public static final int PLAY = 1;
    public static final int STOP = 2;
    public static final int PAUSE = 3;
    public static final int LOOP = 4;
    public static final int RESTART = 5;

    @Expose
    @SerializedName(value = "res_id")
    private String rscId;
    @Expose
    private int opt;

    public Player(String id, String rscId) {
        setId(id);
        this.rscId = rscId;
        opt = 0;
    }

    public Player(String id) {
        this(id, null);
    }

    public String getRscId() {
        return rscId;
    }

    public void setRscId(String rscId) {
        this.rscId = rscId;
    }

    public void setOpt(int opt) {
        this.opt = opt;
    }

    public int getOpt() {
        return opt;
    }

}
