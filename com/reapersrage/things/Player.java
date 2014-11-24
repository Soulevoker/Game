package com.reapersrage.things;

import com.reapersrage.gfx.Screen;
import com.reapersrage.input.Keyboard;
import com.reapersrage.world.tiles.Tile;

/**
 * Created with IntelliJ IDEA. User: Soulevoker Date: 10/27/13 Time: 1:24 PM
 * Copyright © Reapers' Rage 2013
 */
public class Player extends Thing {

	private Keyboard input;
	private String name;	//specic name (ex. Kyle, Kevin, Jason, Droid00198)
							// includes subtitles (ex. the Mighty, the Destroyer)

	public Player(Keyboard input) {
		this.input = input;
		name="";
	}

	public Player(int x, int y, Keyboard input) {
		super(x,y);
		this.input = input;
	}

	public void update() {
		int xa = 0, ya = 0;
		
		//move border checkers to AllThings
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
