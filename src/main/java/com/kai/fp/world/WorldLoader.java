package com.kai.fp.world;

import com.kai.fp.core.Game;
import com.kai.fp.items.ItemLoader;
import com.kai.fp.items.Rarity;
import com.kai.fp.objs.entities.enemies.bosses.Boulderfist;
import com.kai.fp.objs.entities.enemies.bosses.GraveTaker;
import com.kai.fp.objs.entities.enemies.bosses.Shrieker;
import com.kai.fp.objs.entities.enemies.w1.Goblin;
import com.kai.fp.objs.entities.enemies.w1.RangedGoblin;
import com.kai.fp.objs.entities.enemies.w1.Sludge;
import com.kai.fp.objs.entities.enemies.w2.DemonTank;
import com.kai.fp.objs.entities.enemies.w2.Eyeball;
import com.kai.fp.objs.entities.enemies.w2.SkeleTony;
import com.kai.fp.objs.entities.enemies.w3.CandleMan;
import com.kai.fp.objs.entities.enemies.w3.Waxy;
import com.kai.fp.objs.inanimate.*;
import com.kai.fp.objs.npcs.EricCharacter;
import com.kai.fp.objs.npcs.JohnCharacter;
import com.kai.fp.objs.npcs.SallyCharacter;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.util.TextFileLoader;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kai on May 13, 2019
 */
class WorldLoader {

    static WorldTile[][] load(String worldName) {
        WorldTile[][] worldTiles = null;
        try {
            List<String> worldString = TextFileLoader.readLines("/worlds/" + worldName + ".txt");
            int rows = worldString.size();
            int cols = -1;
            List<String[]> lineList = new ArrayList<>();
            for (String s: worldString) {
                String[] arr = s.split("\\s+");
                if (arr.length > cols) cols = arr.length;
                lineList.add(arr);
            }
            worldTiles = new WorldTile[rows][cols];
            for (int i = 0; i<lineList.size(); i++) {
                for (int j = 0; j < lineList.get(i).length; j++) {
                    worldTiles[i][j] = getTile(lineList.get(i)[j], i, j);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

/*        for (WorldTile[] wt: worldTiles) {
            for (WorldTile t: wt) {
                System.out.print(t + "\t");
            }
            System.out.println();
        }*/
        System.out.println(" ==== FINISHED LOADING " + worldName + " ====\n");
        return worldTiles;
    }

    private static WorldTile getTile(String base, int row, int col) {
        String tileString = String.valueOf(base.charAt(0));
        String occupyingString = base.substring(1);
        WorldLocation location = new WorldLocation((WorldTile.WIDTH * col), (WorldTile.HEIGHT * row));

        WorldTile tile = null;
        switch (tileString) {
            case "g":
                tile = new WorldTile(location, ResourceManager.getTileResource("grass"));
                break;
            case "s":
                tile = new WorldTile(location, ResourceManager.getTileResource("stone"));
                break;
            case "_":
                tile = new WorldTile(location, ResourceManager.getTileResource("void"));
                break;
            case "v":
                tile = new WorldTile(location, ResourceManager.getTileResource("fake void"));
                break;
            case "f":
                tile = new WorldTile(location, ResourceManager.getTileResource("fleshy"));
                break;
            case "e":
                tile = new WorldTile(location, ResourceManager.getTileResource("extra red fleshy"));
                break;
            case "l":
                tile = new WorldTile(location, ResourceManager.getTileResource("red light"));
                break;
            case "y":
                double random = Math.random();
                if (random > 0.666) {
                    tile = new WorldTile(location, ResourceManager.getTileResource("spacey thingy 1"));
                } else if (random > 0.333) {
                    tile = new WorldTile(location, ResourceManager.getTileResource("spacey thingy 2"));
                } else {
                    tile = new WorldTile(location, ResourceManager.getTileResource("spacey thingy 3"));
                }
                break;
            case "m":
                tile = new WorldTile(location, ResourceManager.getTileResource("horrible light thingy"));
                break;
            case "i":
                tile = new WorldTile(location, ResourceManager.getTileResource("spawn indicator"));
                break;
            default:
                System.out.println(tileString + " is not recognized as a tile.");

        }

        if (!occupyingString.equals("_")) {
            switch (occupyingString) {
                case "r":
                    tile.setOccupying(new Rock(new WorldLocation(row, col)));
                    break;
                case "C":
                    LootChest chest = new LootChest(new WorldLocation(row, col));
                    if (Math.random() > 0.55) {
                        chest.addItem(ItemLoader.getRandomItem(Rarity.COMMON));
                    } else {
                        chest.addItem(ItemLoader.getRandomPotion(Rarity.COMMON));
                    }
                    tile.setOccupying(chest);
                    break;
                case "R":
                    LootChest rareChest = new LootChest(new WorldLocation(row, col));
                    if (Math.random() > 0.3) {
                        rareChest.addItem(ItemLoader.getRandomItem(Rarity.RARE));
                    } else {
                        rareChest.addItem(ItemLoader.getRandomPotion(Rarity.RARE));
                    }
                    tile.setOccupying(rareChest);
                    break;
                case "G":
                    LootChest glyphicChest = new LootChest(new WorldLocation(row, col));
                    glyphicChest.addItem(ItemLoader.getRandomItem(Rarity.GLYPHIC));
                    tile.setOccupying(glyphicChest);
                    break;
                case "p":
                    tile.setOccupying(new Portal(new WorldLocation(row, col)));
                    break;
                case "t":
                    tile.setOccupying(new Tentacle(new WorldLocation(row, col)));
                    break;
                case "w":
                    tile.setOccupying(new RedWall(new WorldLocation(row, col)));
                    break;
                case "!":
                    Game.addToWorldQueue(new Goblin(new WorldLocation(location)));
                    break;
                case "?":
                    Game.addToWorldQueue(new RangedGoblin(new WorldLocation(location)));
                    break;
                case "@":
                    Game.addToWorldQueue(new Eyeball(new WorldLocation(location)));
                    break;
                case "1":
                    tile.setOccupying(new JohnCharacter(new WorldLocation(row, col), false));
                    break;
                case "2":
                    tile.setOccupying(new SallyCharacter(new WorldLocation(row, col)));
                    break;
                case "3":
                    tile.setOccupying(new EricCharacter(new WorldLocation(row, col), 0));
                    break;
                case "4":
                    tile.setOccupying(new JohnCharacter(new WorldLocation(row, col), true));
                    break;
                case "5":
                    tile.setOccupying(new EricCharacter(new WorldLocation(row, col), 1));
                    break;
                case "6":
                    tile.setOccupying(new EricCharacter(new WorldLocation(row, col), 2));
                    break;
                case ";":
                    Game.addToWorldQueue(new Sludge(new WorldLocation(location)));
                    break;
                case "/":
                    Game.addToWorldQueue(new SkeleTony(new WorldLocation(location)));
                    break;
                case "$":
                    Game.addToWorldQueue(new DemonTank(new WorldLocation(location)));
                    break;
                case "%":
                    Game.addToWorldQueue(new Boulderfist(new WorldLocation(location)));
                    break;
                case ":":
                    Game.addToWorldQueue(new Shrieker(new WorldLocation(location)));
                    break;
                case "|":
                    Game.addToWorldQueue(new Waxy(new WorldLocation(location)));
                    break;
                case "*":
                    Game.addToWorldQueue(new CandleMan(new WorldLocation(location)));
                    break;
                case "#":
                    Game.addToWorldQueue(new GraveTaker(new WorldLocation(location)));
                    break;
                default:
                    System.out.println(occupyingString + " produced an error.");
            }

            if (tile.getOccupying() != null) {
                tile.setWalkable(!tile.getOccupying().isPhysical());
            }
        }
        return tile;
    }


}
