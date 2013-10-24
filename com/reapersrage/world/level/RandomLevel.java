package com.reapersrage.world.level;

import com.reapersrage.gfx.Screen;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Soulevoker
 * Date: 10/24/13
 * Time: 5:57 PM
 * Copyright © Reapers' Rage 2013
 */
public abstract class RandomLevel extends Level {

    private Random random = new Random();

    public RandomLevel(int width, int height) {
        super(width, height);
    }

    protected void generateLevel() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x + y * width] = random.nextInt(4);
            }
        }
    }

    @Override
    protected abstract void update();

    @Override
    protected abstract void render(int xScroll, int yScroll, Screen screen);

    @Override
    protected abstract void time();
}
