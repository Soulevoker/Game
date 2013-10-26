package com.reapersrage.world.tiles;

import com.reapersrage.gfx.Screen;
import com.reapersrage.gfx.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: Soulevoker
 * Date: 10/24/13
 * Time: 8:19 PM
 * Copyright © Reapers' Rage 2013
 */
public class VoidTile extends Tile {

    public VoidTile(Sprite sprite) {
        super(sprite);
    }

    public void render(int x, int y, Screen screen) {
        screen.renderTile(x << 4, y << 4, this);
    }
}
