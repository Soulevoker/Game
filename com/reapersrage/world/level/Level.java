package com.reapersrage.world.level;

import com.reapersrage.entities.Item;
import com.reapersrage.entities.Player;
import com.reapersrage.gfx.Screen;
import java.awt.Graphics2D;


/**
 * Created with IntelliJ IDEA.
 * User: Soulevoker
 * Date: 10/24/13
 * Time: 5:51 PM
 * Copyright © Reapers' Rage 2013
 */
public abstract class Level {
    protected int mapwidth, mapheight;
    protected int[][] tiles;

    public Level(int mapwidth, int mapheight) {
        this.mapwidth = mapwidth;
        this.mapheight = mapheight;
        tiles = new int[this.mapheight][this.mapwidth];
   
    }
    
    public int getTile(int y, int x){
    	return tiles[y][x];
    }
    
    public abstract void renderMobs(Graphics2D g);
    
    public abstract void renderItems(Graphics2D g);
    
    public abstract void addItem(Item item);
    
    public abstract void update(Player player);


    
}
