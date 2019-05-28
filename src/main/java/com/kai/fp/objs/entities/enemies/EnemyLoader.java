package com.kai.fp.objs.entities.enemies;

import com.kai.fp.objs.Projectile;
import com.kai.fp.util.ProjectileLoader;
import com.kai.fp.util.ResourceManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kai on May 26, 2019
 */
public class EnemyLoader {
    private static Map<String, EnemyResource> enemies = new HashMap<>();


    static {
        try {
            loadEnemies();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        for (EnemyResource e: enemies.values()) {
            System.out.println(e);
        }
        System.out.println("==== ENEMY LOADING FINISHED ====\n");
    }

    private static void loadEnemies() throws ParserConfigurationException, IOException, SAXException {
        InputStream file = EnemyLoader.class.getResourceAsStream("/xmls/enemies.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setIgnoringComments(true);
        documentBuilderFactory.setIgnoringElementContentWhitespace(true);
        documentBuilderFactory.setValidating(false);

        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        NodeList nlist = document.getElementsByTagName("Enemy");
        for (int i = 0; i < nlist.getLength(); i++) {
            Node n = nlist.item(i);

            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                String id = e.getAttribute("id");

                EnemyResource enemy = new EnemyResource();
                enemy.id = id;
                enemy.tier = Enemy.EnemyTier.valueOf(e.getAttribute("tier"));


                enemy.width = Integer.valueOf(e.getAttribute("width"));
                enemy.height = Integer.valueOf(e.getAttribute("height"));
                enemy.rateOfFire = Double.valueOf(e.getElementsByTagName("rof").item(0).getTextContent());

                if (e.hasAttribute("firemode")) {
                    enemy.firemode = EnemyFire.valueOf(e.getAttribute("firemode"));
                }

                NodeList statlist = e.getElementsByTagName("stat");
                for (int st = 0; st < statlist.getLength(); st++) {
                    Element statNode = (Element) statlist.item(st);

                    enemy.stats.setStat(statNode.getAttribute("id"), Integer.valueOf(statNode.getTextContent()));
                    if (statNode.getAttribute("id").equals("max health")) {
                        enemy.stats.setStat("health", Integer.valueOf(statNode.getTextContent()));
                    }
                }

                NodeList projlist = e.getElementsByTagName("projectile");
                for (int prj = 0; prj < projlist.getLength(); prj++) {
                    Element projNode = (Element) projlist.item(prj);

                    Projectile p = ProjectileLoader.getProjectile(projNode);

                    enemy.projectiles.add(p);
                }

/*                NodeList dropList = e.getElementsByTagName("drop");
                for (int di = 0; di < dropList.getLength(); di++) {
                    Element dropElement = (Element) dropList.item(di);

                    ID itemID = new ID(Integer.valueOf(dropElement.getAttribute("id")), dropElement.getAttribute("name"));
                    double frequency = Double.valueOf(dropElement.getAttribute("rate"));
                    enemy.getDrops().put(itemID, frequency);
                }*/


                //TODO: Add drops.

                enemies.put(id, enemy);
            }
        }
    }

    public static EnemyResource getEnemyResource(String id) {
        return enemies.get(id);
    }

}
