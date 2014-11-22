package com.reapersrage.world.level;

import com.reapersrage.entities.mobs.Mob;
import com.reapersrage.entities.mobs.Player;
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
    private ArrayList<Mob> MobList = new ArrayList<>();
    
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
        this.MobList = new ArrayList<>();
        MobList.add(new Mob(100, 100,80 , 80, 1, 1, "spike"));
        MobList.add(new Mob(300,150,80,80,-1,1,"foutain"));
    }

    public void update(Player player){
        String collisionDebug = "Health: "+player.getHealth()+
                        " PlayerPos: ("+ player.getX()+","+player.getY() + ")";
                
        Iterator<Mob> mobIterator = MobList.iterator();
        while(mobIterator.hasNext()){
            Mob currentMob = mobIterator.next();
            currentMob.update(player);
            collisionDebug = collisionDebug + " Pos of " + currentMob.getName() + " (" + currentMob.getX() + ", " + currentMob.getY() + ")"; 
        }
        Game.setDebugText(collisionDebug);
                
    }
    
    public void renderMobs(Graphics2D g){
        Iterator<Mob> mobIterator = MobList.iterator();
        while(mobIterator.hasNext()){
            mobIterator.next().drawMob(g);
        }
    }
}
