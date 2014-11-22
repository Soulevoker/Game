package com.reapersrage.world.level;

import com.reapersrage.entities.mobs.Fountain;
import com.reapersrage.entities.mobs.Ghost;
import com.reapersrage.entities.mobs.Mob;
import com.reapersrage.entities.mobs.Player;
import com.reapersrage.entities.mobs.Spike;
import com.reapersrage.entities.projectiles.Projectile;
import com.reapersrage.entities.mobs.Chest;
import com.reapersrage.game.Game;
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
        MobList.add(new Fountain(random.nextInt(Game.getStaticWidth()-80),random.nextInt(Game.getStaticHeight()-80),80,80,1,1,i++));
        MobList.add(new Spike(random.nextInt(Game.getStaticWidth()-80),random.nextInt(Game.getStaticHeight()-80),80,80,10,1,i++));
        //One of the fountains is a mimic that actually drains health
        MobList.add(new Fountain(random.nextInt(Game.getStaticWidth()-80),random.nextInt(Game.getStaticHeight()-80),80,80,-5,1,i++));
        MobList.add(new Ghost(random.nextInt(Game.getStaticWidth()-80),random.nextInt(Game.getStaticHeight()-80),80,80,10,1,i++));
        MobList.add(new Chest(100,100,64,40,10,i++));

    }

    public void update(Player player){
        Iterator<Mob> mobIterator = MobList.iterator();
        //Have each mob interact with the player
        while(mobIterator.hasNext()){
            Mob currentMob = mobIterator.next();
            currentMob.update(player);
        }
        //Display the debugging statistics
        displayDebug(player);
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
        String playerDebug = "<html>Player<br> Position: ("+player.getX()+","+player.getY()+")"+"<br>"+"Velocity: &lt;"+player.getVelX()+","+player.getVelY()+"> "+"<br>"+"Health: "+player.getHealth()+"<br>Gold: "+player.getGold() +"</html>";
        Iterator<Mob> mobIterator = MobList.iterator();
        while(mobIterator.hasNext()){
            Mob currentMob = mobIterator.next();
            collisionDebug = collisionDebug +"<br>"+ currentMob.getName() + " (" + currentMob.getX() + ", " + currentMob.getY() + ")"+ " collision: " + currentMob.isCollided(player); 
        }
        
        collisionDebug = collisionDebug+"</html>";
        projectileDebug = projectileDebug + "</html>";
        Game.setDebugText(0,playerDebug);
        Game.setDebugText(1, collisionDebug);
        Game.setDebugText(2, projectileDebug);
    }
}
