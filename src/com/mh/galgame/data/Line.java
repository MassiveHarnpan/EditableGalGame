package com.mh.galgame.data;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Line extends Identified {

    private String text;
    private String onStartAction;
    private List<Option> options = new ArrayList<>();

    public Line(String id, String text, String onStartAction, Option... options) {
        setId(id);
        this.text = text;
        this.onStartAction = onStartAction;
        this.options.addAll(Arrays.asList(options));
    }

    public Line(String id) {
        this(id, "", null);
    }

    public Line(String id, String text) {
        this(id,text,null);
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

    public void addOption(Option option) {
        options.add(option);
    }

    public Option getOption(int index) {
        return options.get(index);
    }

    public Option removeOption(int index) {
        return options.remove(index);
    }

    public int optionCount() {
        return options.size();
    }

    public Iterator<Option> optionIterator() {
        return options.iterator();
    }

    public List<Option> getOptions() {
        return options;
    }
}
