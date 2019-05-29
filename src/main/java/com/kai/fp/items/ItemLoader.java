package com.kai.fp.items;

import com.kai.fp.objs.Projectile;
import com.kai.fp.objs.entities.player.Player;
import com.kai.fp.util.ProjectileLoader;
import com.kai.fp.util.ResourceManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

/**
 * @author Kai on May 20, 2019
 */
public class ItemLoader {

    public static Map<String, Item> commonItems = new HashMap<>();
    public static Map<String, Item> rareItems = new HashMap<>();
    public static Map<String, Item> glyphicItems = new HashMap<>();

    static {
        try {
            loadItems();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        for (Item i : commonItems.values()) {
            System.out.println(i);
        }
        for (Item i : rareItems.values()) {
            System.out.println(i);
        }
        for (Item i : glyphicItems.values()) {
            System.out.println(i);
        }

        System.out.println(" ==== ITEM LOADING FINISHED ====\n");
    }

    private static void loadItems() throws ParserConfigurationException, IOException, SAXException {
        InputStream file = ItemLoader.class.getResourceAsStream("/xmls/items.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setIgnoringComments(true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        NodeList itemNodeList = document.getElementsByTagName("Item");
        for (int i = 0; i < itemNodeList.getLength(); i++) {
            Element itemElement = (Element) itemNodeList.item(i);

            String id, description;
            Rarity rarity;
            List<ItemBehavior> behaviors = new ArrayList<>();
            BufferedImage image;

            id = itemElement.getAttribute("id");
            description = itemElement.getElementsByTagName("description").item(0).getTextContent();
            rarity = getRarity(itemElement.getAttribute("rarity"));
            String[] imageCoords = itemElement.getAttribute("imgcoords").split(" ");
            image = ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("items"),Integer.valueOf(imageCoords[0]), Integer.valueOf(imageCoords[1]), 8, 8 ).getScaledInstance(32, 32, Image.SCALE_FAST));

            NodeList statNodeList = itemElement.getElementsByTagName("stat");
            for (int j = 0; j < statNodeList.getLength(); j++) {
                Element statElement = (Element) statNodeList.item(j);
                String statID = statElement.getAttribute("id");
                double amount = Double.valueOf(statElement.getTextContent());
                int integerAmount = (int) amount;

                behaviors.add(new ItemBehavior() {
                    public void onEquip(Player owner) {
                        if (amount>0) { owner.getStats().incStat(statID, integerAmount);
                        } else { owner.getStats().decStat(statID, integerAmount); }
                    }
                    public void onRemove(Player owner) {
                        if (amount>0) { owner.getStats().removeIncStat(statID, integerAmount);
                        } else { owner.getStats().removeDecStat(statID, integerAmount); }
                    }
                    public String getDescription() {
                        return ((amount < 0) ? "" : "+") + integerAmount + " " + statID.substring(0,1).toUpperCase() + statID.substring(1);
                    }
                });
            }

            NodeList keywordList = itemElement.getElementsByTagName("keyword");
            for (int l = 0; l < keywordList.getLength(); l++) {
                Element keywordElement = (Element) keywordList.item(l);
                behaviors.add(getSpecialBehavior(keywordElement.getTextContent()));
            }

            Item item = null;
            if (itemElement.getAttribute("type").equals("armor")) {
                item = new Armor(id, image, behaviors, description, rarity);
            } else if (itemElement.getAttribute("type").equals("weapon")) {
                ItemFire firemode = ItemFire.valueOf(itemElement.getAttribute("firemode"));
                List<Projectile> projList = new ArrayList<>();

                NodeList projectileNodeList = itemElement.getElementsByTagName("projectile");
                for (int k = 0; k < projectileNodeList.getLength(); k++) {
                    projList.add(ProjectileLoader.getProjectile((Element)projectileNodeList.item(k)));
                }

                NodeList lineList = itemElement.getElementsByTagName("line");
                String[] hudLines = new String[lineList.getLength()];
                for (int m = 0; m < lineList.getLength(); m++) {
                    hudLines[m] = lineList.item(m).getTextContent();
                }

                double rof = Double.valueOf(itemElement.getElementsByTagName("rof").item(0).getTextContent());

                item = new Weapon(id, image, behaviors, description, rarity, firemode, hudLines, projList, rof);
            }
            if (rarity == Rarity.COMMON) {
                commonItems.put(id, item);
            } else if (rarity == Rarity.RARE) {
                rareItems.put(id, item);
            } else if (rarity == Rarity.GLYPHIC) {
                glyphicItems.put(id, item);
            }
        }
    }

    private static ItemBehavior getSpecialBehavior(String keyword) {
        switch(keyword) {
            default:
                return null;
        }
    }


    public static Rarity getRarity(String base) {
        switch (base.toLowerCase()) {
            case "common":
                return Rarity.COMMON;
            case "rare":
                return Rarity.RARE;
            case "glyphic":
                return Rarity.GLYPHIC;
        }
        return null;
    }

    public static Item getRandomItem(Rarity r) {
        Map<String, Item> items = getCorrectMap(r);
        Object[] keys = items.keySet().toArray();
        String name =  (String)keys[new Random().nextInt(keys.length)];
        return getItem(name, r);
    }

    public static Item getItem(String name, Rarity r) {
        Map<String, Item> items = getCorrectMap(r);
        if (items.get(name).getType() == Item.ItemType.ARMOR) {
            return new Armor(items.get(name));
        } else {
            return new Weapon((Weapon)items.get(name));
        }
    }

    private static Map<String, Item> getCorrectMap(Rarity r) {
        Map<String, Item> items = null;
        if (r == Rarity.COMMON) items = commonItems;
        if (r == Rarity.RARE) items = rareItems;
        if (r == Rarity.GLYPHIC) items = glyphicItems;
        return items;
    }



}
