/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reapersrage.entities.items;

import com.reapersrage.entities.mobs.Player;
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
public abstract class Item {

    protected int x;
    protected int y;
    ImageResizer IR;
    //O = original R = resized
    protected BufferedImage OImage;
    protected BufferedImage RImage;
    protected int dir;
    protected int width;
    protected int height;
    protected boolean destroyed;
    protected boolean imortalObject;

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

    public boolean isCollided(Player person) {
        int[] itemXrange = {x, x + width};
        int[] personXrange = {person.getX(), person.getX() + person.getWidth()};
        int[] itemYrange = {y, y + height};
        int[] personYrange = {person.getY(), person.getY() + person.getHeight()};

        //checks if any pixel in mob overlaps with any pixel in player
        for (int i = itemXrange[0]; i < itemXrange[1]; i++) {
            for (int j = itemYrange[0]; j < itemYrange[1]; j++) {
                for (int k = personXrange[0]; k < personXrange[1]; k++) {
                    for (int l = personYrange[0]; l < personYrange[1]; l++) {
                        //The pixels of the mob and player must overlap  AND  the mob must not be transparent at that point
                        if (((i == k) && (j == l)) && !isTransparent(i - itemXrange[0], j - itemYrange[0]) && !(person.isTransparent(k - personXrange[0], l - personYrange[0]))) {
                            return true;
                        }

                    }
                }
            }
        }
        return false;
    }

    //checks if the mob sprite is transparent at give x,y. Transparent pixels do not count as part of the hitbox
    public boolean isTransparent(int x, int y) {
        int pixel = RImage.getRGB(x, y);
        if ((pixel >> 24) == 0x00) {
            return true;
        } else {
            return false;
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
