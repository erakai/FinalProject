package com.kai.fp.objs.entities.player;

import com.kai.fp.display.RisingText;
import com.kai.fp.display.Screen;
import com.kai.fp.items.*;

import java.awt.*;

public class PlayerInventory {
    private Player owner;

    private Weapon weapon;
    private Armor armor;

    public PlayerInventory(Player owner) {
        this.owner = owner;
        equipItem(ItemLoader.getItem("Useless Jacket", Rarity.STARTER));
        equipItem(ItemLoader.getItem("Apprentice Staff", Rarity.STARTER));
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

    public Weapon getWeapon() {
        return weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void drinkPotion(Potion p) {
        new RisingText(owner, p.getBehaviors().get(0).getDescription(), Color.CYAN);
        p.onEquip(owner);
    }
}
