package com.reapersrage.entities.mobs;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.reapersrage.gfx.GameTile;
import com.reapersrage.gfx.Screen;
import com.reapersrage.input.Keyboard;

/**
 * Created with IntelliJ IDEA.
 * User: Soulevoker
 * Date: 10/27/13
 * Time: 1:24 PM
 * Copyright © Reapers' Rage 2013
 */
public class Player extends Mob {

    private Keyboard input;
    private BufferedImage Image;


    public Player(Keyboard input) {
        this.input = input;
    }

    public Player(int x, int y, Keyboard input) {
        this.x = x;
        this.y = y;
        this.input = input;
        try {
			Image = ImageIO
					.read(GameTile.class
							.getResourceAsStream("/com/reapersrage/res/textures/jim.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void update() {
        int xa = 0, ya = 0;
        
        if (input.up &&  getY() > 0) {
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
    
    public void drawPlayer(Graphics2D g){
    	g.drawImage(Image, x, y, null);
    }

}
