package com.mh.galgame.data;


public class Player extends Identified {

    public static final int PLAY = 1;
    public static final int STOP = 2;
    public static final int PAUSE = 3;
    public static final int LOOP = 4;
    public static final int RESTART = 5;

    private String lastRscId;
    private String rscId;
    private int opt;
    private int lastOpt;

    public Player(String id, String rscId) {
        setId(id);
        this.rscId = rscId;

        lastRscId = null;
        lastOpt = 0;
        opt = 0;
    }

    public Player(String id) {
        this(id, null);
    }

    public String getRscId() {
        return rscId;
    }

    public String getLastRscId() {
        return lastRscId;
    }

    public void setRscId(String rscId) {
        lastRscId = this.rscId;
        this.rscId = rscId;
    }

    public void setLastOpt(int lastOpt) {
        this.lastOpt = lastOpt;
    }

    public void setOpt(int opt) {
        lastOpt = this.opt;
        this.opt = opt;
    }

    public int getOpt() {
        return opt;
    }

    public int getLastOpt() {
        return lastOpt;
    }
}
