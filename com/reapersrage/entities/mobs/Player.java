package com.reapersrage.entities.mobs;

import com.reapersrage.entities.projectiles.FireBall;
import com.reapersrage.entities.projectiles.Projectile;
import com.reapersrage.game.Debug;
import com.reapersrage.game.Game;
import com.reapersrage.game.ImageResizer;
import com.reapersrage.game.VectorMath;
import com.reapersrage.gfx.GameTile;
import com.reapersrage.gfx.Screen;
import com.reapersrage.input.Buttons;
import com.reapersrage.input.Keyboard;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.ImageIO;
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
        
        //Projectiles the player has fired
        private ArrayList<Projectile> ProjList = new ArrayList<>();
        
        //Attributes of the player
        private int health;
        private int mana;
        private int gold;

        //Pixels for the player to move every update
        private double[] velocity = new double[2];
        //private final int START_SPEED = 5;
        //Maximum velocity
        private final double MAX_V;
        //How much to accelerate the player by key presses
        private final double acceleration;
        //How much the player slows down due to the map
        private final double friction;
        //If the player is about to hit a wall NORTH EAST SOUTH WEST
        private boolean[] wall = new boolean[4];
        private final int DEF_HEALTH = 2000000;
        private final int DEF_MANA = 100;
        private Random random = new Random(); //so randum xD

        private boolean[] playerDirs;
        //This way there's no autofire
        private boolean alreadyFired;
        //This way there's no autoblink
        private boolean alreadyBlinked;
        private boolean alreadyBlast;

        
        private ImageResizer IR;


	public Player(int x, int y, int width, int height) {
                this.mana = DEF_MANA;
		//Initial x position (pixels)
		this.x = x;
                //Initial y position (pixels)
		this.y = y;
                //Widith and height of player 
		this.width = width;
		this.height = height;
                //Velocty increase per update
                this.acceleration = .8;
                this.friction = 1;
                this.velocity = new double[]{0.0,0.0};
                this.MAX_V = 10.0;
                this.playerDirs = new boolean[10];
                this.alreadyFired = false;
                this.alreadyBlinked = false;
                this.alreadyBlast = false;
                
		try {
			OImage = ImageIO
					.read(GameTile.class
							.getResourceAsStream("/com/reapersrage/res/textures/JimIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			IR = new ImageResizer(OImage, this.width, this.height);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RImage = IR.getResizedImage();
                
                //sets initial health
                health = DEF_HEALTH;
	}

        //Update the player each frame
        public void update(){
            parseInput();
            boolean[] collision = checkCollision(x, y, velocity); //check collisions before moving
            move();
            updateProjectiles();
            health+=1;
            mana+=1;
            
        }
        
        public void updateProjectiles(){
            Iterator<Projectile> projIterator = ProjList.iterator();
            while(projIterator.hasNext()){
                Projectile currProj = projIterator.next();
                if(currProj.destroyed){
                projIterator.remove();
            }
                currProj.update();
            }
        }
        
        //Moves the player, stopping if we hit a wall
        private void move(){
            int xNew = x;
            int yNew = y;
            boolean[] collision = checkCollision(x, y, velocity);
            //Movement of the player
            if(!collision[1]){
                //Down
                yNew = yNew + (int)velocity[1];
            }
            if(!collision[0]){
                //Right
                xNew = xNew + (int)velocity[0];
            }
            //If we've hit a wall, set the position to just touch the wall and set veloicty to 0
            if(wall[0]){
                //North
                yNew = 0;
                velocity[1] = 0;
            }
            else if(wall[1]){
                //East
                xNew = Game.getStaticWidth() - width;
                velocity[0] = 0;
            }
            else if(wall[2]){
                //South
                yNew = Game.getStaticHeight() - height;
                velocity[1] = 0;
            }
            else if(wall[3]){
                //West
                xNew = 0;
                velocity[0] = 0;
            }
            x = xNew;
            y = yNew;
        }
            
        
        //Parses the keypresses
        //If the key is pressed, increments the velocity by acceleration
        //velocity gets updated based on the inputs and acceleration
        public void parseInput(){
            //For each direction, check input
            if(Buttons.up){
                //Up
                if(-1.0*velocity[1] < MAX_V)
                    velocity[1] -= acceleration;
                if (velocity[1] < -MAX_V) velocity[1] = -MAX_V;
            }
            if(Buttons.down){
                //Down
                if(velocity[1] < MAX_V)
                    velocity[1] += acceleration;
                if(velocity[1] > MAX_V) velocity[1] = MAX_V;
            }
            if(Buttons.left){
                //Left
                if(-velocity[0] < MAX_V)
                    velocity[0] -= acceleration;
                if(velocity[0] < -MAX_V) velocity[0] = -MAX_V;
            }
            if(Buttons.right){
                //Right
                if(velocity[0] < MAX_V)
                    velocity[0] += acceleration;
                if(velocity[0] > MAX_V) velocity[0] = MAX_V;
            }
            //if not accelerating in y direction
            if(!Buttons.up && !Buttons.down && velocity[1] != 0){
                double newVelocity = velocity[1];
                newVelocity -= velocity[1] > 0 ? friction : -friction;
                if(velocity[1] > 0 && newVelocity < 0)
                    newVelocity = 0;
                else if(velocity[1] < 0 && newVelocity > 0)
                    newVelocity = 0;
                velocity[1] = newVelocity;
                 
            }
            //If not accelerating in x
            if(!Buttons.left && !Buttons.right && velocity[0] != 0){
                double newVelocity = velocity[0];
                newVelocity -= velocity[0] > 0 ? friction : -friction;
                if(velocity[0] < 0 && newVelocity > 0)
                    newVelocity = 0;
                else if(velocity[0] > 0 && newVelocity < 0)
                    newVelocity = 0;
                velocity[0] = newVelocity;
            }
            //fire a projectile
            
            String text="<html>";
            text += "projUp: " + Buttons.projUp + "<br>";
            text += "projDown: " + Buttons.projDown + "<br>";
            text += "projLeft: " + Buttons.projLeft + "<br>";
            text += "projRight: " + Buttons.projRight + "<br>";
            text += "alreadyFired: " + !(Buttons.projUp || Buttons.projDown || Buttons.projLeft || Buttons.projRight);
            text = text + "</html>";
            Game.debugPanel.setLabel(2,text);
            //fire
            if((Buttons.projUp || Buttons.projDown || Buttons.projLeft || Buttons.projRight) && !this.alreadyFired){
                this.alreadyFired = true;
                fire();
            }
            if(this.alreadyFired && !(Buttons.projUp || Buttons.projDown || Buttons.projLeft || Buttons.projRight)){
                this.alreadyFired = false;
            }
            //blink
            if(Buttons.space && !this.alreadyBlinked) {
                this.alreadyBlinked = true;
                int x1 = random.nextInt(Game.getStaticWidth() - width);
                int y1 = random.nextInt(Game.getStaticHeight() - height);
                setPos(x1,y1);
            }
            if(!Buttons.space && this.alreadyBlinked){
                this.alreadyBlinked = false;
            }
            if(Buttons.blast && !this.alreadyBlast) {
                this.alreadyBlast = true;
                blast();
            }
            if(!Buttons.blast && this.alreadyBlast){
                this.alreadyBlast = false;
            }
        }

        //Checks for a collision in both x and y and return an array of booleans indicating such
        //for collisions with walls. Collisions with mobs is handeled by each individual mob
        public boolean[] checkCollision(int x, int y, double[] v){
            boolean[] collisions =  new boolean[]{false, false};            
            wall[0] = false;
            wall[1] = false;
            wall[2] = false;
            wall[3] = false;
            //WEST wall
            if(x + (int)v[0] < 0){
                collisions[0] = true;
                wall[3] = true;
            }
            //EAST wall
            else if(x + (int)v[0] + width > Game.getStaticWidth()){
                collisions[0] = true;
                wall[1] = true;
            }
            //NORTH wall
            if(y + (int)v[1] < 0){
                collisions[1] = true;
                wall[0] = true;
            }
            //SOUTH Wall
            else if(y + (int)v[1] + height > Game.getStaticHeight()){
                collisions[1] = true;
                wall[2] = true;
            }
            
            return collisions;
        }
        
        //Draws the player
	public void drawPlayer(Graphics2D g) {
            g.drawImage(RImage, x, y, null);
            Iterator<Projectile> projIterator = ProjList.iterator();
            //Draw projectile
            while(projIterator.hasNext()){
                Projectile currProj = projIterator.next();
                currProj.drawProj(g);
            }
	}
	
   
        
        //Player's health
        public int getHealth(){
            return health;
        }
        public int getGold(){
            return gold;
        }
        public int getMana(){
            return mana;
        }
        
        //changes players health. Negative lowers health (damage)
        public void changeHealth(int change) {
            health += change;
        }
        public void changeGold(int change) {
            gold += change;
        }    
        
        //get the position
         public int getX(){
             return x;
        }
        public int getY(){
            return y;
        }
        public int getWidth(){
            return width;
        }
        public int getHeight(){
            return height;
        }
        public int getVelX(){
            return (int)velocity[0];
        }
        public int getVelY(){
            return (int)velocity[1];
        }
        public boolean isTransparent(int x, int y){
           int pixel = RImage.getRGB(x,y);
           if ( (pixel>>24) == 0x00) return true;
           else return false; 
        }
        public void setPos(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        
        
        private void fire(){
            
            double[] currVel = new double[2];
            //Don't fire if velocity will be zero
            boolean[] willFire = new boolean[]{true,true};
            
            if(Buttons.projUp && !Buttons.projDown){
                //Up and not down
                currVel[1] = -1;
            } else if(Buttons.projDown && !Buttons.projUp) {
                //Down and not up
                currVel[1] = 1;   
            } else {
                //Both up and down or neither
                //currVel[1] = 0;
                willFire[1] = false;
            }
            if(Buttons.projLeft && !Buttons.projRight){
                //left and not right
                currVel[0] = -1;
            } else if(Buttons.projRight && !Buttons.projLeft) {
                //Right and not left
                currVel[0] = 1;   
            } else {
                //Both left and right or neither
                //currVel[0] = 0;
                willFire[0] = false;
            }
           
            
            if (willFire[0] || willFire[1]){
                ProjList.add(new FireBall(this.x+this.width-20, this.y+10, 15, 15, 50, VectorMath.scaleVector(currVel, 15)));
            }
        }
        private void blast (){
            for (int i=0; i<30; i++){
                double[] newDir = new double[2];
                    newDir[0] = (double)random.nextInt(40)-20;
                    newDir[1] = (double)random.nextInt(40)-20;
                    ProjList.add(new FireBall(this.x+this.width-20, this.y+10,15,15,50,newDir));
            }
        }    
        
        
        public Iterator getProjectiles(){
            return ProjList.iterator();
        }
        public boolean isDestroyed() {
            return (this.health < 0); 
        }

}
