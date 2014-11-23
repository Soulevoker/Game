/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.reapersrage.entities.mobs;

import com.reapersrage.entities.items.GoldPiece;
import com.reapersrage.entities.items.Item;
import com.reapersrage.entities.mobs.Player;
import com.reapersrage.entities.projectiles.Projectile;
import com.reapersrage.game.Game;
import com.reapersrage.game.ImageResizer;
import com.reapersrage.gfx.GameTile;
import com.reapersrage.gfx.Screen;
import com.reapersrage.input.Keyboard;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.ImageIO;

//creates a mob
public abstract class Mob {
    
        //--------//
        ///TAKEN FROM PLAYER CLASS. 
        protected int x;
	protected int y;
	ImageResizer IR;
	//O = original R = resized
	protected BufferedImage OImage;
	protected BufferedImage RImage;
	protected int dir;
	protected int width;
	protected int height;
        protected boolean destroyed;
        protected boolean imortalObject;
        
        //for Chest
        protected int gold;
        
        //Attributes of the player
        //private int health;
        //------//
        
        //Attributes of mob
        protected int health;
        private int damageOnHit; //damage player takes on impact
        private int dps; //damage per second
        private String name; //name of the mob
        private boolean[] wall; //will the mob hit the wall?
        protected Random rand;
        
        public Mob (int x, int y, int width, int height, int damageOnHit, int dps, String name){
                dir = 0;
                this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
                this.damageOnHit = damageOnHit;
                this.dps = dps;
                this.wall = new boolean[4];
                this.destroyed = false;
                this.rand = new Random();
		String fileName = "/com/reapersrage/res/textures/";
                fileName = fileName + name + ".png";
                try {
			OImage = ImageIO
					.read(GameTile.class
							.getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
                
                try {
					IR = new ImageResizer(OImage, width, height);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			RImage = IR.getResizedImage();
		
                
                //sets initial health
                health = 100;
        
        }
        
        public Mob (int[] pos, int width, int height, int damageOnHit, int dps, String name){
            this(pos[0], pos[1], width, height, damageOnHit, dps, name);
        }
   
        public void drawMob(Graphics2D g) {
		g.drawImage(RImage, x, y, null);
	}
	
        public int getHealth(){
            return health;
        }
    
        //updates the mob in respect to a given player
        public void update(Player person){
            if (imortalObject) this.health = 100000;
            if (isCollided(person)){
                dealDamage(person);
                giveGold(person);
            }
            projCollision(person);
            
            
        }
        
        //checks to see if collided with player
        public boolean isCollided(Player person){
            int[] mobXrange = {x,x+width};
            int[] personXrange = {person.getX(), person.getX()+person.getWidth()};
            int[] mobYrange = {y,y+height};
            int[] personYrange = {person.getY(), person.getY()+person.getHeight()};
           
            
            //checks if any pixel in mob overlaps with any pixel in player
            for (int i=mobXrange[0] ; i<mobXrange[1]; i++){
                for (int j=mobYrange[0]; j<mobYrange[1]; j++){
                    for (int k=personXrange[0]; k<personXrange[1]; k++ ){
                        for (int l=personYrange[0]; l<personYrange[1]; l++){
                            //The pixels of the mob and player must overlap  AND  the mob must not be transparent at that point
                            if (((i==k) && (j==l)) && !isTransparent(i-mobXrange[0],j-mobYrange[0]) && !(person.isTransparent(k-personXrange[0],l-personYrange[0]))) return true;
                            
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
        
        public void giveGold(Player person) {
            person.changeGold(gold);
        }
        
        //checks if the mob sprite is transparent at give x,y. Transparent pixels do not count as part of the hitbox
        public boolean isTransparent(int x, int y){
            int pixel = RImage.getRGB(x,y);
            if ( (pixel>>24) == 0x00) return true;
            else return false;
        }
        //Move the mob by dx and dy
        public void move(int dx, int dy){
            boolean[] collision = checkCollision(x, y, new double[]{dx, dy});
            //if we aren't colliding on the X axis
            int xNew = x;
            int yNew = y;
            if(!collision[0]){
                xNew += dx;
            }
            //if we aren't colliding on the Y axis
            if(!collision[1]){
                yNew += dy;
            }
            //If we've hit a wall, set the position to just touch the wall and set veloicty to 0
            if(wall[0]){
                //North
                yNew = 0;
            }
            else if(wall[1]){
                //East
                xNew = Game.getStaticWidth() - width;
            }
            else if(wall[2]){
                //South
                yNew = Game.getStaticHeight() - height;
            }
            else if(wall[3]){
                //West
                xNew = 0;
            }
            this.x = xNew;
            this.y = yNew;
        }
        
        //Will collide with x or y?
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
        
        //The displacement of the ghost from the player (direction only)
        public double[] displacementFromPlayer(Player player){
            double[] disp = new double[2];
            disp[0] = player.getX() - this.x;
            disp[1] = player.getY() - this.y;
            double mag = Math.sqrt(disp[0]*disp[0] + disp[1]*disp[1]);
            disp[0] = disp[0]/mag;
            disp[1] = disp[1]/mag;
            return disp;
        }
        //Displacement from player, scaled by mag
        public double[] displacementFromPlayer(Player player, double magnitude){
            double[] disp = new double[2];
            disp[0] = player.getX() - this.x;
            disp[1] = player.getY() - this.y;
            double mag = Math.sqrt(disp[0]*disp[0] + disp[1]*disp[1]);
            disp[0] = disp[0]/mag*magnitude;
            disp[1] = disp[1]/mag*magnitude;
            return disp;
        }
        
        public boolean projCollision(Player player)
        {
            boolean collision = false;
            Iterator<Projectile> projIterator = player.getProjectiles();
            while(projIterator.hasNext()){
                Projectile proj = projIterator.next();
                if(proj.isCollided(this)){
                    collision = true;
                    proj.dealDamage(this);
                    proj.destroy();
                }
            }
            return collision;
        }
        
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
        public String getName(){
            return this.name;
        }
        public void setGold(int gold) {
            this.gold = gold;
        }
        
        public void changeHealth(int h){
            this.health += h;
        }
        public boolean isDestroyed() {
            if(health <= 0) destroy();
            return destroyed; 
        }
        
        public void destroy(){
            destroyed = true;
        }
        
        public String getType(){
            return name;
        }
        
        public int[] getPos(){
            return new int[]{this.x, this.y};
        }
        
        public void dropGold(int gold){
            Game.getLevel().addItem(new GoldPiece(new int[]{this.x,this.y}, gold));
        }
        
        public void dropItem(Item item){
            Game.getLevel().addItem(item);
        }
}
