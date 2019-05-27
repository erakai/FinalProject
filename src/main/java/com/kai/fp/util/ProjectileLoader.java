package com.kai.fp.util;

import com.kai.fp.objs.Projectile;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ProjectileLoader {

    public static Projectile getProjectile(Element projElement) {
        int[] coords = {
                Integer.valueOf(projElement.getAttribute("imgcoords").split(" ")[0]),
                Integer.valueOf(projElement.getAttribute("imgcoords").split(" ")[1]),
                Integer.valueOf(projElement.getAttribute("width")),
                Integer.valueOf(projElement.getAttribute("height"))
        };
        BufferedImage image = ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("projectiles"), coords[0], coords[1], 8, 8).getScaledInstance(coords[2], coords[3], Image.SCALE_FAST));
        int damage = Integer.valueOf(projElement.getAttribute("damage"));
        int range = Integer.valueOf(projElement.getAttribute("range")) * 15;
        int speed = Integer.valueOf(projElement.getAttribute(("speed")));
        int variance = Integer.valueOf(projElement.getAttribute("variance"));

        Projectile p = new Projectile(coords[2], coords[3]);
        p.setImage(image);
        p.setDamage(damage);
        p.setSpeed(speed);
        p.setVariance(variance);
        p.setRange(range);
        return p;
    }

}
