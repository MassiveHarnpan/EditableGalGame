package com.mh.galgame.data;



public class Option {

    private String text;
    private String onSelectAction;

    public Option() {
        this("", null);
    }

    public Option(String text) {
        this(text, null);
    }


    public Option( String text, String onSelectAction) {
        this.text = text;
        this.onSelectAction = onSelectAction;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOnSelectAction() {
        return onSelectAction;
    }

    public void setOnSelectAction(String onSelectAction) {
        this.onSelectAction = onSelectAction;
    }

    @Override
    public String toString() {
        return text;
    }
}
