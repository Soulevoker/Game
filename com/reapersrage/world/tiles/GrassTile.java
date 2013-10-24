package com.reapersrage.world.tiles;

import com.reapersrage.gfx.Screen;
import com.reapersrage.gfx.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: Soulevoker
 * Date: 10/24/13
 * Time: 6:29 PM
 * Copyright © Reapers' Rage 2013
 */
public class GrassTile extends Tile {

    public GrassTile(Sprite sprite) {
        super(sprite);
    }

    @Override
    protected void render(int x, int y, Screen screen) {
        screen.renderTile(x, y, this);
    }
}
