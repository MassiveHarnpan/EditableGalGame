package com.mh.galgame.actionresponder;

public interface ActionResponder {
    Object eval(String str);

    Object get(String key);
    Object set(String key, Object value);
}
