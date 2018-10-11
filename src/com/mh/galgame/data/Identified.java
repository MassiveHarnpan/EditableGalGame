package com.mh.galgame.data;

public class Identified implements Comparable<Identified> {

    private String id = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (this.id != null) {
            throw new UnsupportedOperationException("The ID has been set: " + this.id);
        }
        this.id = id;
    }

    @Override
    public int compareTo(Identified i){
        if (this.id != null) {
            return this.id.compareTo(i.id);
        } else {
            return "".compareTo(i.id);
        }
    }
}
