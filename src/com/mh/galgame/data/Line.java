package com.mh.galgame.data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Line extends Identified {

    @Expose
    private String text;
    @Expose
    @SerializedName(value = "onstart")
    private String onStartAction;
    @Expose
    @SerializedName(value = "onempty")
    private String onEmptyAction;
    @Expose
    private Option[] options = null;

    public Line(String id, String text, String onStartAction, Option... options) {
        setId(id);
        this.text = text;
        this.onStartAction = onStartAction;
        this.options = options;
    }



    public Line(String id) {
        this(id, "", null);
    }

    public Line(String id, String text) {
        this(id,text,null);
    }

    @Expose(serialize = false, deserialize = false)
    private int index = -1;

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }




    public String getOnStartAction() {
        return onStartAction;
    }

    public void setOnStartAction(String onStartAction) {
        this.onStartAction = onStartAction;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /*
    public void addOption(Option option) {
        options.add(option);
    }

    public Option removeOption(int index) {
        return options.remove(index);
    }
    */

    public void setOptions(Option[] options) {
        this.options = options;
    }

    public Option getOption(int index) {
        return options[index];
    }

    public int optionCount() {
        return options == null ? 0 : options.length;
    }

    public Option[] getOptions() {
        return Arrays.copyOf(options, options.length);
    }

    public String getOnEmptyAction() {
        return onEmptyAction;
    }

    public void setOnEmptyAction(String onEmptyAction) {
        this.onEmptyAction = onEmptyAction;
    }
}
