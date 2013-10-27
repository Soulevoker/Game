package com.reapersrage.entities.mobs;

import com.reapersrage.entities.Entity;
import com.reapersrage.gfx.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: Soulevoker
 * Date: 10/27/13
 * Time: 1:08 PM
 * Copyright © Reapers' Rage 2013
 */
public abstract class Mob extends Entity {

    protected Sprite sprite;
    protected int dir;

    public void move(int xa, int ya) {
        if (xa > 0) {
            dir = 1;
        }
        if (xa < 0) {
            dir = 3;
        }
        if (ya > 0) {
            dir = 1;
        }
        if (ya < 0) {
            dir = 0;
        }

        if (!collision()) {
            x += xa;
            y += ya;
        }
    }

    public void update() {
    }

    public void render() {

    }

    private boolean collision() {
        return false;
    }

    public int getDir() {
        return dir;
    }
}
