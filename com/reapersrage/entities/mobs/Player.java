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
<<<<<<< HEAD
        
        //Attributes of the player
        private int health;
=======
        //Pixels for the player to move every update
        private int[] speed= new int[2];
        private final int START_SPEED = 5;
        //How long the player has been moving in each cardinal direction
        //UP/DOWN LEFT/RIGHT
        private int[] acceleration = new int[2];
>>>>>>> origin/newTile

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
                this.speed = new int[]{5, 5};
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
            incrementSpeed();
            int xNew = x;
            int yNew = y;
            if(dirs[0] && !collision(x, y - speed[1])[1]){
                //Up
                yNew = yNew - speed[1];
            }
            if(dirs[1] && !collision(x, y + speed[1])[1]){
                //Down
                yNew = yNew + speed[1];
            }
            if(dirs[2] && !collision(x - speed[0], y)[0]){
                //Left
                xNew = xNew - speed[0];
            }
            if(dirs[3] && !collision(x + speed[0], y)[0]){
                //Right
                xNew = xNew + speed[0];
            }
            if(xNew == x){
                //If we're still then acceleration is 0
                acceleration[0] = 0;
            } else{
                //Acceleration goes up by 1 if velocity is positive
                //Otherwise goes down by one
                acceleration[0] += xNew > x ? 1 : -1;
            }
            if(yNew == y){
                acceleration[1] = 0;
            } else{
                acceleration[1] += yNew > y ? 1 : -1;
            }
            
            x = xNew;
            y = yNew;
        }
        
        //Increment both the x and y speeds based on acceleration
        public void incrementSpeed(){
            if(acceleration[0] == 0){
                speed[0] = START_SPEED;
            } else{
                speed[0] += 1;
            }
            if(acceleration[1] == 0){
                speed[1] = START_SPEED;
            } else{
                speed[1] += 1;
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
