package com.reapersrage.world.level;

import com.reapersrage.gfx.Screen;


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


    
}
