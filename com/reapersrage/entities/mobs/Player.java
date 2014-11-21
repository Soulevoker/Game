package com.reapersrage.entities.mobs;

import com.reapersrage.gfx.Screen;
import com.reapersrage.input.Keyboard;
import com.reapersrage.world.tiles.Tile;

/**
 * Created with IntelliJ IDEA. User: Soulevoker Date: 10/27/13 Time: 1:24 PM
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

		if (input.up && getY() > 0) {
			ya--;
		}
		if (input.down && getY()<14) {
			ya++;
		}
		if (input.left && getX() > 0) {
			xa--;
		}
		if (input.right && getX() < 25) {
			xa++;
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);	//calls move(double,double) with ints?
		}
	}

	public void render(Screen screen) {
		Tile.playerTile.render(getX(), getY(), screen);
	}
}
