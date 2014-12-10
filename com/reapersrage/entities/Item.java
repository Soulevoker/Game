/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reapersrage.entities;

import com.reapersrage.entities.Player;
import com.reapersrage.game.ImageResizer;
import com.reapersrage.gfx.GameTile;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author David
 */
public abstract class Item extends Entity{

    protected int dir;
    protected int gold;
    private String name; //name of the item

    public Item(int x, int y, int width, int height, int gold, String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gold = gold;
        this.name = name;
        String fileName = "/com/reapersrage/res/textures/";
        fileName = fileName + name + ".png";
        try {
            OImage = ImageIO
                    .read(GameTile.class
                            .getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            IR = new ImageResizer(OImage, width, height);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RImage = IR.getResizedImage();
    }

    public void draw(Graphics2D g) {
        g.drawImage(RImage, x, y, null);
    }

    public void update(Player person) {
        if (isCollided(person)) {
            giveGold(person);
        }
    }

    public void giveGold(Player person) {
        person.changeGold(gold);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return this.name;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

}
