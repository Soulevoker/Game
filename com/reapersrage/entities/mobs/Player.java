package com.reapersrage.entities.mobs;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.reapersrage.game.Game;
import com.reapersrage.gfx.GameTile;
import com.reapersrage.gfx.Screen;
import com.reapersrage.input.Keyboard;

/**
 * Created with IntelliJ IDEA. User: Soulevoker Date: 10/27/13 Time: 1:24 PM
 * Copyright © Reapers' Rage 2013
 */
public class Player {
	private int x;
	private int y;
	//O = original R = resized
	private BufferedImage OImage;
	private BufferedImage RImage;
	private int dir;
	private int width;
	private int height;
        //Pixels for the player to move every update
        private int speed;

	public Player(int x, int y, int width, int height) {
		dir = 0;
                //Initial x position (pixels)
		this.x = x;
                //Initial y position (pixels)
		this.y = y;
                //Widith and height of player 
		this.width = width;
		this.height = height;
                //Defult to a speed of 5
                this.speed = 5;
		try {
			OImage = ImageIO
					.read(GameTile.class
							.getResourceAsStream("/com/reapersrage/res/textures/jim.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			resize(OImage,this.width,this.height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

        //Updates the player direction from a list of possible directions
        //Array approach allows us to move diagnally
        //Array: {up, down, left, right}
        //Note to self: THE TOP RIGHT CORNER IS 0,0
        public void update(boolean[] dirs){
            if(dirs[0] && !collision(x, y - speed)[1]){
                //Up
                y -= speed;
            }
            if(dirs[1] && !collision(x, y + speed)[1]){
                //Down
                y += speed;
            }
            if(dirs[2] && !collision(x - speed, y)[0]){
                //Left
                x -= speed;
            }
            if(dirs[3] && !collision(x + speed, y)[0]){
                //Right
                x += speed;
            }
            
        }
                
        public void setSpeed(int newSpeed){
            this.speed = newSpeed;
        }

        //Checks for a collision in both x and y and return an array of booleans indicating such
        public boolean[] collision(int x, int y){
            boolean[] collisions =  new boolean[]{false, false};
            
            if(x < 0 || x + width > Game.getStaticWidth()){
                collisions[0] = true;
            }
            if(y < 0 || y + height > Game.getStaticHeight()){
                collisions[1] = true;
            }
            
            return collisions;
        }
        
	public void drawPlayer(Graphics2D g) {
		g.drawImage(RImage, x, y, null);
	}
	

	public BufferedImage resize(BufferedImage original, int scaledWidth, int scaledHeight)
			throws IOException {

		RImage = new BufferedImage(scaledWidth,
				scaledHeight, original.getType());

		Graphics2D g2d = RImage.createGraphics();
		g2d.drawImage(OImage, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();
		return RImage;
	}

}
