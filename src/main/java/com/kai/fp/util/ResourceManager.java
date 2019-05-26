package com.kai.fp.util;

import com.kai.fp.world.TileResource;
import com.kai.fp.world.WorldTile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that retrieves all images on program start and can then be accessed in the future.
 *
 * @author Kai on May 13, 2019
 */
public class ResourceManager {
    private static Map<String, BufferedImage> sprites = new HashMap<>();
    private static Map<String, TileResource> tiles = new HashMap<>();

    private static final String[] imagesToLoad = {
            "button", "chestcontents", "chests", "hud", "itemframe", "items", "observer", "player",
            "portals", "rock", "tentacle", "tiles"
    };

    static {
        try {
            loadSprites();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            loadTiles();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

    public static BufferedImage getSprite(String key) {
        return sprites.get(key);
    }

    public static TileResource getTileResource(String key) {
        return tiles.get(key);
    }

    private static void loadSprites() throws IOException {
        for (String s :imagesToLoad) {
            BufferedImage image = (ImageIO.read(ResourceManager.class.getResourceAsStream("/sprites/"+s+".png")));
            sprites.put(s, image);
        }
    }
    private static BufferedImage load(String name, String path) {
/*
        System.out.println("LOADING " + name + " : " + path );
*/
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new FileInputStream(path));
        } catch (IOException ex) { ex.printStackTrace(); }

        sprites.put(name, bi);
        return bi;
    }

    private static void loadTiles() throws ParserConfigurationException, IOException, SAXException {
        InputStream file = ResourceManager.class.getResourceAsStream("/xmls/tiles.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setIgnoringComments(true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        NodeList tileList = document.getElementsByTagName("Tile");
        for (int i = 0; i < tileList.getLength(); i++) {
            Element tileElement = (Element) tileList.item(i);

            String id = tileElement.getAttribute("id");
            BufferedImage image;

            if (tileElement.hasAttribute("img")) {
                image = load(id, tileElement.getAttribute("img"));
            } else {
                image = sprites.get("tiles");
                image = splice(image, Integer.valueOf(tileElement.getAttribute("x")), Integer.valueOf(tileElement.getAttribute("y")), WorldTile.WIDTH, WorldTile.HEIGHT);
            }

            TileResource resource = new TileResource(image, id);

            if (tileElement.hasAttribute("walkable")) {
                resource.setWalkable(Boolean.getBoolean(tileElement.getAttribute("walkable")));
            }

            tiles.put(id, resource);
        }

    }

    public static BufferedImage splice(BufferedImage start, int x, int y, int width, int height) {
        return start.getSubimage(x, y, width, height);
    }
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;

    }

        /*
    THE BELOW METHODS ARE TAKEN FROM https://code.google.com/archive/p/game-engine-for-java/source.
    I take no credit for any of the below code.
     */

    public static BufferedImage mirrorImage(BufferedImage simg) {
        int width = simg.getWidth();
        int height = simg.getHeight();
        //BufferedImage for mirror image
        BufferedImage mimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //create mirror image pixel by pixel
        for(int y = 0; y < height; y++){
            for(int lx = 0, rx = width-1; lx < width; lx++, rx--){
                //lx starts from the left side of the image
                //rx starts from the right side of the image
                //get source pixel value
                int p = simg.getRGB(lx, y);
                //set mirror image pixel value - both left and right
                mimg.setRGB(rx, y, p);
            }
        }
        return mimg;
    }
    public static BufferedImage[] mirrorList(BufferedImage[] arr) {
        BufferedImage[] mirrored = new BufferedImage[arr.length];
        for (int i = 0; i < mirrored.length; i++) {
            mirrored[i] = mirrorImage(arr[i]);
        }
        return mirrored;
    }

    public static BufferedImage rotate(Image img, double angle) {
        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
                cos = Math.abs(Math.cos(Math.toRadians(angle)));

        int w = img.getWidth(null),
                h = img.getHeight(null);

        int neww = (int) Math.floor(w*cos + h*sin),
                newh = (int) Math.floor(h*cos + w*sin);

        BufferedImage bimg = toBufferedImage(getEmptyImage(neww, newh));
        Graphics2D g = bimg.createGraphics();

        g.translate((neww-w)/2, (newh-h)/2);
        g.rotate(Math.toRadians(angle), w/2, h/2);
        g.drawRenderedImage(toBufferedImage(img), null);
        g.dispose();

        return toBufferedImage(toImage(bimg));
    }

    public static Image getEmptyImage(int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return toImage(img);
    }

    public static Image toImage(BufferedImage bimage) {
        // Casting is enough to convert from BufferedImage to Image
        Image img = (Image) bimage;
        return img;
    }
}
