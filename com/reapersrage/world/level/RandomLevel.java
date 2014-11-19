package com.reapersrage.world.level;

import java.util.Random;

/**
 * Created with IntelliJ IDEA. User: Soulevoker Date: 10/24/13 Time: 5:57 PM
 * Copyright © Reapers' Rage 2013
 */
public class RandomLevel extends Level {

	private static Random random = new Random();

	public RandomLevel(int width, int height) {
		super(width, height);
	}

	protected void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x + y * width] = random.nextInt(4);
			}
		}

		tiles[20] = 4;
		tiles[64] = 4;
	}

	@Override
	protected void update() {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	@Override
	protected void time() {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}
}
