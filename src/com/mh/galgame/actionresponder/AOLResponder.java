package com.mh.galgame.actionresponder;

import aol.*;
import com.mh.galgame.data.Layer;
import com.mh.galgame.data.Player;
import com.mh.galgame.loader.GalGame;

public class AOLResponder implements ActionResponder {

    private AOL aol = new AOL();

    private GalGame game;

    public AOLResponder(GalGame game) {
        this.game = game;
        OperationalClass layerClass = aol.createClass("Layer");
        OperationalClass playerClass = aol.createClass("Player");
        OperationalClass gameClass = aol.createClass("Game");

        Operator layerSetter = new Operator() {
            @Override
            public boolean operate(OperationalObject obj, String operation, KeyValuePair pair) {
                Layer layer = (Layer) obj.getObject();
                String key = pair.getKey();
                if (key == null) {
                    return false;
                }
                PrimitiveValue value = pair.getValue();
                switch (key) {
                    case "x": layer.setX(value.getReal()); break;
                    case "y": layer.setY(value.getReal()); break;
                    case "scale": layer.setScale(value.getReal()); break;
                    case "match_mode": layer.setMatchMode(value.getInt()); break;
                    case "pic_id": layer.setPicId(value.getString()); break;
                    default: return false;
                }
                return true;
            }
        };
        Operator playerSetter = new Operator() {
            @Override
            public boolean operate(OperationalObject obj, String operation, KeyValuePair pair) {
                Player player = (Player) obj.getObject();
                String key = pair.getKey();
                if (key == null) {
                    return false;
                }
                PrimitiveValue value = pair.getValue();
                switch (key) {
                    case "res_id": player.setResId(value.getString()); break;
                    case "opt": player.setOpt(value.getInt()); break;
                    default: return false;
                }
                return true;
            }
        };
        Operator playerOperator = new Operator() {
            @Override
            public boolean operate(OperationalObject obj, String operation, KeyValuePair pair) {
                Player player = (Player) obj.getObject();
                String key = pair.getKey();
                if (key == null) {
                    return false;
                }
                PrimitiveValue value = pair.getValue();
                switch (key) {
                    case "res_id": player.setResId(value.getString()); break;
                }
                switch (operation) {
                    case "play": player.setOpt(Player.PLAY);
                    case "stop": player.setOpt(Player.STOP);
                    case "pause": player.setOpt(Player.PAUSE);
                    case "restart": player.setOpt(Player.RESTART);
                    case "loop": player.setOpt(Player.LOOP);
                }
                return true;
            }
        };
        Operator gameOperator = new Operator() {
            @Override
            public boolean operate(OperationalObject obj, String operation, KeyValuePair pair) {
                GalGame g = (GalGame) obj.getObject();
                String key = pair.getKey();
                PrimitiveValue value = pair.getValue();
                if (key == null) {
                    if (value.getType() == PrimitiveValue.NUMBER) {
                        g.go(value.getInt());
                    } else {
                        g.go(value.getString());
                    }
                } else if ("id".equals(key)) {
                    g.go(value.getString());
                    return true;
                } else if ("off".equals(key) && value.getType() == PrimitiveValue.NUMBER) {
                    g.go(value.getInt());
                } else {
                    return false;
                }
                return true;
            }
        };

        layerClass.addOperator("set", layerSetter);
        playerClass.addOperator("set", playerSetter);
        playerClass.addOperator("play", playerOperator);
        playerClass.addOperator("pause", playerOperator);
        playerClass.addOperator("stop", playerOperator);
        playerClass.addOperator("loop", playerOperator);
        playerClass.addOperator("restart", playerOperator);
        gameClass.addOperator("go", gameOperator);

        aol.addObject("_", game, "Game");
        aol.addObject("script", game, "Game");

    }

    @Override
    public Object eval(String str) {
        return aol.evalString(str);
    }

    @Override
    public Object get(String key) {
        return aol.getVar(key);
    }

    @Override
    public Object set(String key, Object value) {
        if (value != null && aol.getClass(value.getClass().getSimpleName()) != null) {
            return aol.setVar(key, value, value.getClass().getSimpleName());
        } else {
            return aol.setVar(key, value);
        }
    }

    @Override
    public Object del(String key) {
        return aol.delVar(key);
    }
}
