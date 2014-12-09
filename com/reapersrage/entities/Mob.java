/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.reapersrage.entities;

import com.reapersrage.entities.Player;
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
public abstract class Mob extends Entity {
    
        
	protected int dir;
	protected int gold;
        protected int health;
        private int damageOnHit; //damage player takes on impact
        private int dps; //damage per second
        protected String name; //name of the mob
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
        
        
        
        
        //Will collide with x or y?
        
        
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
        
        public int[] displacementFrom(int[] pos){
            return new int[]{this.x - pos[0], this.y - pos[1]};
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
            Game.getLevel().addEntity(new GoldPiece(new int[]{this.x,this.y}, gold));
        }
        
        public void dropItem(Item item){
            Game.getLevel().addEntity(item);
        }
}
