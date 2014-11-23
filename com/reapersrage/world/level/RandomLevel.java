package com.reapersrage.world.level;

import com.reapersrage.entities.mobs.*;
import com.reapersrage.game.Game;
import com.reapersrage.game.VectorMath;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Soulevoker
 * Date: 10/24/13
 * Time: 5:57 PM
 * Copyright © Reapers' Rage 2013
 */
public class RandomLevel extends Level {

    private static Random random = new Random();
    //Stores all the mobs
    private ArrayList<Mob> MobList = new ArrayList<>();
    //private ArrayList<Projectile> ProjectileList = new ArrayList<>();
    //Stores all the projectiles
   
    
    public RandomLevel(int width, int height) {
        super(width, height);
        generateLevel();
    }

    protected void generateLevel() {
        for (int y = 0; y < mapheight; y++) {
            for (int x = 0; x < mapwidth; x++) {
            	int q = random.nextInt(3);
            	if(q == 2){
            		q = random.nextInt(3);
            	}
                tiles[y][x] = q;
            }
        }
        int i = 0;
        this.MobList = new ArrayList<>();
        MobList.add(new Spike(random.nextInt(Game.getStaticWidth()-80),random.nextInt(Game.getStaticHeight()-80),80 , 80, 1, 1,i++));
        MobList.add(new Fountain());
        MobList.add(new Spike(random.nextInt(Game.getStaticWidth()-80),random.nextInt(Game.getStaticHeight()-80),80,80,10,1,i++));
        //One of the fountains is a mimic that actually drains health
        MobList.add(new MimicFountain());
        MobList.add(new Chest());
        for(int b=0; b<10; b++){
            MobList.add(new Ghost(VectorMath.randomPos(Ghost.defWidth, Ghost.defHeight)));
        }
        
    }

    public void update(Player player){
        Iterator<Mob> mobIterator = MobList.iterator();
        //Have each mob interact with the player
        
        while(mobIterator.hasNext()){
            Mob currentMob = mobIterator.next();
            currentMob.update(player);
            
            if (currentMob.isDestroyed()){
                mobIterator.remove();
            }
        }
        //Display the debugging statistics
        displayDebug(player);
        if(random.nextInt(Game.ticks+1) % 100 < (int)Math.sqrt((double)Game.ticks)/2 && Ghost.NUM < 10){
           MobList.add(new Ghost());
        }
        if(Chest.NUM < 1){
            MobList.add(new Chest());
        }
    }
    
    public void renderMobs(Graphics2D g){
        Iterator<Mob> mobIterator = MobList.iterator();
        while(mobIterator.hasNext()){
            mobIterator.next().drawMob(g);
        }
    }
    
    //Debugging Information
    public void displayDebug(Player player){
        String collisionDebug = "<html>Mobs";
        String projectileDebug = "<html>Projectiles";
        String playerDebug = "<html>Player<br> Position: ("+player.getX()+","+player.getY()+")"+"<br>"+"Velocity: &lt;"+player.getVelX()+","+player.getVelY()+"> "+"<br>"+"Health: "+player.getHealth()+"<br>Mana"+player.getMana()+"<br>Gold: "+player.getGold()+"<br>"+(Game.getTicks()%50) +"</html>";
        Iterator<Mob> mobIterator = MobList.iterator();
        
        while(mobIterator.hasNext()){
            Mob currentMob = mobIterator.next();
            collisionDebug = collisionDebug +"<br>"+ currentMob.getName() + " (" + currentMob.getX() + ", " + currentMob.getY() + ")"+ " collision: " + currentMob.isCollided(player); 
        }
        collisionDebug = collisionDebug + "<br> Ghost.NUM: " +  Ghost.NUM;
        collisionDebug = collisionDebug + "<br> Fountain.NUM: " +  Fountain.NUM;
        collisionDebug = collisionDebug + "<br> MimicFountain.NUM: " +  MimicFountain.NUM;
        collisionDebug = collisionDebug + "<br> Chest.NUM: " +  Chest.NUM;
        
        collisionDebug = collisionDebug+"</html>";
        projectileDebug = projectileDebug + "</html>";
        Game.setDebugText(0,playerDebug);
        Game.setDebugText(1, collisionDebug);
        //Game.setDebugText(2, projectileDebug);
    }
}
