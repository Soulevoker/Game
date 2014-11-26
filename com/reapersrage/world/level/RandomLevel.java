package com.reapersrage.world.level;

import com.reapersrage.entities.*;
import com.reapersrage.game.Game;
import com.reapersrage.game.VectorMath;
//import com.reapersrage.res.sound.Sound;
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
        //MobList.add(new Spike(random.nextInt(Game.getStaticWidth()-80),random.nextInt(Game.getStaticHeight()-80), Game.getStaticWidth()/mapwidth, Game.getStaticHeight()/mapheight, 1, 1,i++));
        MobList.add(new Fountain(random.nextInt(Game.getStaticWidth()-80),random.nextInt(Game.getStaticHeight()-80),Game.getStaticWidth()/mapwidth,Game.getStaticHeight()/mapheight,10,1));
        //MobList.add(new Spike(random.nextInt(Game.getStaticWidth()-80),random.nextInt(Game.getStaticHeight()-80),Game.getStaticWidth()/mapwidth,Game.getStaticHeight()/mapheight,10,1,i++));
        //One of the fountains is a mimic that actually drains health
        MobList.add(new MimicFountain(random.nextInt(Game.getStaticWidth()-80),random.nextInt(Game.getStaticHeight()-80),Game.getStaticWidth()/mapwidth,Game.getStaticHeight()/mapheight,10,1));
        MobList.add(new Chest());


        for(int b=0; b<10; b++){
             MobList.add(new Ghost(random.nextInt(Game.getStaticWidth()-80),random.nextInt(Game.getStaticHeight()-80),Game.getStaticWidth()/mapwidth,Game.getStaticHeight()/mapheight,10,1));
        }
        //Sound bgMusic = new Sound();
        // bgMusic.music("bg");

    }

    public void update(Player player){
        Iterator<Mob> mobIterator = MobList.iterator();
        //Have each mob interact with the player
        //Update the mobs
        while(mobIterator.hasNext()){
            Mob currentMob = mobIterator.next();
            currentMob.update(player);
            
            if (currentMob.isDestroyed()){

                if (currentMob.getType().equals("ghost")){
                    Game.debugPanel.addLabel(3,"Dropping gold!");
                    ItemList.add(new GoldPiece(currentMob.getPos(), 10));
                }


                mobIterator.remove();
            }
        }
        Iterator<Item> ItemIterator = ItemList.iterator();
        //Have each item interact with the player
        while(ItemIterator.hasNext()){
            Item currentItem = ItemIterator.next();
            currentItem.update(player);
            
            if (currentItem.isDestroyed()){
                ItemIterator.remove();
            }
        }
        Iterator<Projectile> projIterator = ProjList.iterator();
        while(projIterator.hasNext()){
            Projectile currProj = projIterator.next();
            currProj.update(player);
            if(currProj.isDestroyed()){
                projIterator.remove();
            }
        }
        //Display the debugging statistics
        displayDebug(player);
        //Algorithm for summon rate, it's REALLY shitty, need to be fixed
        if(random.nextInt(100) < 5 && Ghost.NUM < 5){
           MobList.add(new Ghost());
        }
        //Add a new chests once all the chests have been collected
        if(Chest.NUM < 1){
            //Randomly add 10 chests
            if(random.nextInt(100) == 1){
                for(int i = 0; i < 10; i++){
                    MobList.add(new Chest());
                }
            } else{
                MobList.add(new Chest());
            }
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
