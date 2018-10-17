package com.mh.galgame.preform.updater;

import com.mh.galgame.data.Player;

public interface PlayerUpdater {

    void onPlayerAdded(Player player);
    void onPlayerRemoved(Player player);
    void onResIdChanged(Player player, String resId);
    void onPlayerOptChanged(Player player, int opt);


}
