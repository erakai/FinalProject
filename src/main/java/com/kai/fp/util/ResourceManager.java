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

    static {
        loadSprites("src/main/resources/sprites/");

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

    private static void loadSprites(String path) {
        File dir1 = new File(path);
        File[] listOfFiles1 = dir1.listFiles();
        for (File f : listOfFiles1) {
            if (f.isFile()) {
/*
                System.out.println(f.getName().replaceAll("\\.png", "") + " : " + f.getPath());
*/
                load(f.getName().replaceAll("\\.png", ""), f.getPath());
            }
            if (f.isDirectory()) {
                loadSprites(f.getPath());
            }
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


}
