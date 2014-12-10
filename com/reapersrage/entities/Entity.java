/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reapersrage.entities;

import com.reapersrage.game.Game;
import com.reapersrage.game.ImageResizer;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author David
 */
public abstract class Entity {

    //X and Y are are based on the WCS
    int x;
    int y;
    int locX;
    int locY;
    ImageResizer IR;
    BufferedImage OImage;
    BufferedImage RImage;
    int width;
    int height;
    boolean destroyed;
    boolean imortalObject;
    boolean[] wall; //Will it hit the wall?

    public void draw(Graphics2D g) {
                this.locX = x - Game.getScreen().getX();
                this.locY = y - Game.getScreen().getY();
		g.drawImage(RImage, locX, locY, null);
	}
    
    /**
     * Update
     */
    public abstract void update(Player person);

    /**
     * Checks whether or not there is a collision, pixel by pixel.
     * 
     * Uses LCS
     */
    public boolean isCollided(Entity person) {
        int[] mobXrange = {locX, locX + width};
        int[] personXrange = {person.getX(), person.getX() + person.getWidth()};
        int[] mobYrange = {locY, locY + height};
        int[] personYrange = {person.getY(), person.getY() + person.getHeight()};

        //checks if any pixel in mob overlaps with any pixel in player 
        if (Math.abs(person.getX() - this.locX) < person.getWidth() + this.width || Math.abs(person.getY() - this.locY) < person.getHeight() + this.height) {
            for (int i = mobXrange[0]; i < mobXrange[1]; i++) {
                for (int j = mobYrange[0]; j < mobYrange[1]; j++) {
                    for (int k = personXrange[0]; k < personXrange[1]; k++) {
                        for (int l = personYrange[0]; l < personYrange[1]; l++) {
                            //The pixels of the mob and player must overlap  AND  the mob must not be transparent at that point
                            if (((i == k) && (j == l)) && !isTransparent(i - mobXrange[0], j - mobYrange[0]) && !(person.isTransparent(k - personXrange[0], l - personYrange[0]))) {
                                return true;
                            }

                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns true if current pixel is transparent
     *
     * @param x - loc of pixel
     * @param y - loc of pixel
     * @return
     */
    public boolean isTransparent(int x, int y) {
        int pixel;
        try {
            pixel = RImage.getRGB(x, y);
        } catch (java.lang.NullPointerException e) {
            pixel = OImage.getRGB(x, y);
        }
        if ((pixel >> 24) == 0x00) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Move the entity by [dx,dy] if it can
     *
     * @param dx - change in x
     * @param dy - change in 
     * @return if there was a collision
     */
    public boolean move(int dx, int dy) {
        boolean[] collision = checkCollision(x, y, new double[]{dx, dy});
        
        //if we aren't colliding on the X axis
        int xNew = x;
        int yNew = y;
        if (!collision[0]) {
            xNew += dx;
        }
        //if we aren't colliding on the Y axis
        if (!collision[1]) {
            yNew += dy;
        }
        //If we've hit a wall, set the position to just touch the wall and set veloicty to 0
        if (wall[0]) {
            //North
            yNew = 0;
        } else if (wall[1]) {
            //East
            xNew = Game.getStaticWidth() - width;
        } else if (wall[2]) {
            //South
            yNew = Game.getStaticHeight() - height;
        } else if (wall[3]) {
            //West
            xNew = 0;
        }
        this.x = xNew;
        this.y = yNew;
        
        return collision[0] || collision[1];
    }

    /**
     * Checks if there will be a collision with the wall
     *
     * @param x - current x
     * @param y - current y
     * @param v - current velocity vector
     * @return if there's a collision
     */
    public boolean[] checkCollision(int x, int y, double[] v) {
        boolean[] collisions = new boolean[]{false, false};
        wall[0] = false;
        wall[1] = false;
        wall[2] = false;
        wall[3] = false;
        //WEST wall
        if (x + (int) v[0] < 0) {
            collisions[0] = true;
            wall[3] = true;
        } //EAST wall
        else if (x + (int) v[0] + width > Game.getStaticWidth()) {
            collisions[0] = true;
            wall[1] = true;
        }
        //NORTH wall
        if (y + (int) v[1] < 0) {
            collisions[1] = true;
            wall[0] = true;
        } //SOUTH Wall
        else if (y + (int) v[1] + height > Game.getStaticHeight()) {
            collisions[1] = true;
            wall[2] = true;
        }

        return collisions;
    }

    public abstract void destroy();
    
    /**
     * Get the x position
     * @return 
     */
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    
    public boolean isDestroyed(){
        return destroyed;
    }

}
