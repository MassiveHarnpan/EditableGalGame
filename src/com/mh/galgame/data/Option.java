package com.mh.galgame.data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option {

    @Expose
    private String hint;
    @Expose
    @SerializedName(value = "onselect")
    private String onSelectAction;

    public Option() {
        this("", null);
    }

    public Option(String hint) {
        this(hint, null);
    }


    public Option(String hint, String onSelectAction) {
        this.hint = hint;
        this.onSelectAction = onSelectAction;
    }


    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getOnSelectAction() {
        return onSelectAction;
    }

    public void setOnSelectAction(String onSelectAction) {
        this.onSelectAction = onSelectAction;
    }

    @Override
    public String toString() {
        return hint;
    }
}
