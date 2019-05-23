package com.kai.fp.items;

import com.kai.fp.objs.entities.player.Player;

/**
 * @author Kai on May 20, 2019
 */
public interface ItemBehavior {
    void onEquip(Player owner);
    void onRemove(Player owner);
    String getDescription();
}
