package com.reapersrage.entities;

import com.reapersrage.gfx.Screen;
import com.reapersrage.world.level.Level;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Soulevoker
 * Date: 10/27/13
 * Time: 2:37 AM
 * Copyright © Reapers' Rage 2013
 */
public abstract class Entity {
    public int x, y;
    private boolean removed = false;
    protected Level level;
    protected Random random;

    public void update() {

    }

    public void render(Screen screen) {

    }

    /**
     * Remove Entity from Level
     */
    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }
}
