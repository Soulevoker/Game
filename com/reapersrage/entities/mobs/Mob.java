/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.reapersrage.entities.mobs;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.reapersrage.game.Game;
import com.reapersrage.gfx.GameTile;
import com.reapersrage.gfx.Screen;
import com.reapersrage.input.Keyboard;
import com.reapersrage.entities.mobs.Player;

//creates a mob
public class Mob {
    
        //--------//
        ///TAKEN FROM PLAYER CLASS. 
        private int x;
	private int y;
	//O = original R = resized
	private BufferedImage OImage;
	private BufferedImage RImage;
	private int dir;
	private int width;
	private int height;
        
        //Attributes of the player
        //private int health;
        //------//
        
        //Attributes of mob
        private int health;
        private int damageOnHit; //damage player takes on impact
        private int dps; //damage per second
        
        public Mob (int x, int y, int width, int height, int damageOnHit, int dps){
            dir = 0;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
                this.damageOnHit = damageOnHit;
                this.dps = dps;
		try {
			OImage = ImageIO
					.read(GameTile.class
							.getResourceAsStream("/com/reapersrage/res/textures/spike.png"));
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
 
        public void drawMob(Graphics2D g) {
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
//        public void changeHealth(int change) {
//            health += change;
//        }
        
        //updates the mob in respect to a given player
        public void update(Player person){
            if (isCollided(person)) dealDamage(person); 
        }
        
        //checks to see if collided with player
        public boolean isCollided(Player person){
            int[] mobXrange = {x,x+width};
            int[] personXrange = {person.getX(), person.getX()+person.getWidth()};
            int[] mobYrange = {y,y+height};
            int[] personYrange = {person.getY(), person.getY()+person.getHeight()};
           
            
            //checks if any pixel in mob overlaps with any pixel in player
            for (int i=mobXrange[0] ; i<=mobXrange[1]; i++){
                for (int j=mobYrange[0]; j<=mobYrange[1]; j++){
                    for (int k=personXrange[0]; k<=personXrange[1]; k++ ){
                        for (int l=personYrange[0]; l<=personYrange[1]; l++){
                            if ((i==k) && (j==l)) return true;
                            
                        }
                    }
                }
            }
            return false;
        }
        
        //deals damage to player
        public void dealDamage(Player person){
            int damage = damageOnHit;
            long currentTime = System.currentTimeMillis();
            
            //if the current time is divisible by 1000/dps, do damage
            //this is because milliseconds mod 1000 will be true once a second
            //boolean timeToHit = (currentTime%(1000/dps)<10);
            boolean timeToHit = true;
            if (timeToHit) person.changeHealth(-damageOnHit);
        }
        
        public int getX(){
             return x;
        }
        public int getY(){
            return y;
        }
        
        
        

}
