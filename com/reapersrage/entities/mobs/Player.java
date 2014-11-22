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
	private int width;
	private int height;

        
        //Attributes of the player
        private int health;

        //Pixels for the player to move every update
        private double[] velocity = new double[2];
        //private final int START_SPEED = 5;
        //How much to accelerate the player by key presses
        private double acceleration;
        //How much the player slows down due to the map
        private double friction;
        

	public Player(int x, int y, int width, int height) {
		//Initial x position (pixels)
		this.x = x;
                //Initial y position (pixels)
		this.y = y;
                //Widith and height of player 
		this.width = width;
		this.height = height;
                //Velocty increase per update
                this.acceleration = .2;
                this.friction = .5;
                this.velocity = new double[]{0.0,0.0};
                
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
                
                //sets initial health
                health = 100;
	}

        //Updates the player direction from a list of possible directions
        //Array approach allows us to move diagnally
        //Array: {up, down, left, right}
        //Note to self: THE TOP RIGHT CORNER IS 0,0
        public void update(boolean[] dirs){
            parseInput(dirs);
            int xNew = x;
            int yNew = y;
            
            if(!collision(x, y + (int)velocity[1])[1]){
                //Down
                yNew = yNew + (int)velocity[1];
            }
            if(!collision(x + (int)velocity[0], y)[0]){
                //Right
                xNew = xNew + (int)velocity[0];
            }
            
            x = xNew;
            y = yNew;
        }
        
        //Parses the keypresses
        //If the key is pressed, increments the velocity by acceleration
        //velocity gets updated based on the inputs and acceleration
        public void parseInput(boolean[] dirs){
            //For each direction, check input
            if(dirs[0]){
                //Up
                velocity[1] -= acceleration;
            }
            if(dirs[1]){
                //Down
                 velocity[1] += acceleration;
            }
            if(dirs[2]){
                //Left
                velocity[0] -= acceleration;
            }
            if(dirs[3]){
                //Right
                velocity[0] += acceleration;
            }
            //if not accelerating in y direction
            if(!dirs[0] && !dirs[1] && velocity[1] != 0){
                velocity[1] -= velocity[1] > 0 ? friction : -friction;
            }
            if(!dirs[2] && !dirs[3] && velocity[0] != 0){
                velocity[0] -= velocity[0] > 0 ? friction : -friction;
            }
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
        
        public int getHealth(){
            return health;
        }
        
        //changes players health. Negative lowers health (damage)
        public void changeHealth(int change) {
            health += change;
        }
        
        //get the position
         public int getX(){
             return x;
        }
        public int getY(){
            return y;
        }
        

}
