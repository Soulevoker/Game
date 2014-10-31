package com.reapersrage.entities.mobs;

import com.reapersrage.gfx.Screen;
import com.reapersrage.input.Keyboard;
import com.reapersrage.world.tiles.Tile;

/**
 * Created with IntelliJ IDEA.
 * User: Soulevoker
 * Date: 10/27/13
 * Time: 1:24 PM
 * Copyright © Reapers' Rage 2013
 */
public class Player extends Mob {

    private Keyboard input;

    public Player(Keyboard input) {
        this.input = input;
    }

    public Player(int x, int y, Keyboard input) {
        this.x = x;
        this.y = y;
        this.input = input;
    }

    public void update() {
        int xa = 0, ya = 0;
        if (input.up) {
            ya--;
        }
        if (input.down) {
            ya++;
        }
        if (input.left) {
            xa--;
        }
        if (input.right) {
            xa++;
        }
        if (xa != 0 || ya != 0) {
            move(xa, ya);
        }
    }

    public void render(Screen screen) {
        Tile.playerTile.render(getX(), getY(), screen);
    }
}
