package com.kai.fp.objs.entities.player;

import com.kai.fp.display.Screen;
import com.kai.fp.items.Armor;
import com.kai.fp.items.Item;
import com.kai.fp.items.Weapon;

public class PlayerInventory {
    private Player owner;

    private Weapon weapon;
    private Armor armor;

    public PlayerInventory(Player owner) {
        this.owner = owner;
    }

    public void equipItem(Item i) {
        i.onEquip(owner);
        if (i.getType() == Item.ItemType.WEAPON) {
            weapon = (Weapon) i;
            Screen.getHud().getWeaponFrame().setItem(i);
            Screen.getHud().getWeaponFrame().setOnScreen(true);
        } else if (i.getType() == Item.ItemType.ARMOR) {
            armor = (Armor) i;
            Screen.getHud().getArmorFrame().setItem(i);
            Screen.getHud().getArmorFrame().setOnScreen(true);
        }

    }

    public Item removeArmor() {
        Armor a = null;
        if (armor != null) {
            a = new Armor(armor);
            armor.onRemove(owner);
        }
        armor = null;
        Screen.getHud().getArmorFrame().removeItem();
        Screen.getHud().getArmorFrame().setOnScreen(false);
        return a;
    }

    public Item removeWeapon() {
        Weapon w = null;
        if (weapon != null) {
            w = new Weapon(weapon);
            weapon.onRemove(owner);
        }
        weapon = null;
        Screen.getHud().getWeaponFrame().removeItem();
        Screen.getHud().getWeaponFrame().setOnScreen(false);
        return w;
    }

}
