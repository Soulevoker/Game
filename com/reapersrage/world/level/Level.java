package com.reapersrage.world.level;

import com.reapersrage.gfx.Screen;
import com.reapersrage.world.tiles.Tile;

/**
 * Created with IntelliJ IDEA.
 * User: Soulevoker
 * Date: 10/24/13
 * Time: 5:51 PM
 * Copyright © Reapers' Rage 2013
 */
public abstract class Level {
    protected int width, height;
    protected int[] tiles;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new int[width * height];
        generateLevel();
    }

    public Level(String path) {
        loadLevel(path);
    }



    protected void generateLevel() {

    }

    public void loadLevel(String path) {

    }

    protected abstract void update();

    protected void render(int xScroll, int yScroll, Screen screen) {
        screen.setOffsets(xScroll, yScroll);
        int x0 = xScroll >> 4;
        int x1 = (xScroll + screen.getWidth()) >> 4;
        int y0 = yScroll >> 4;
        int y1 = (yScroll + screen.getHeight()) >> 4;
    }

    public Tile getTile(int x, int y) {
        if (tiles[x+y *width] == 0) {
            return Tile.grass;
        }
        return Tile.voidTile;
    }

    protected abstract void time();
}
