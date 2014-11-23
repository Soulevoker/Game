/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reapersrage.entities.mobs;

import com.reapersrage.game.VectorMath;

/**
 *
 * @author Chris
 */
public class Chest extends Mob {
    private int id;
    public static final int defWidth = 80;
    public static final int defHeight = 55;
    public static final int defGold = 10;
    public static int ID = 0;
    //Number of chests summoned
    public static int NUM = 0;
    
    public Chest(int x, int y, int width, int height, int gold) {
        super(x, y, width, height, 0, 0, "chest");
        this.id = Chest.ID++;
        setGold(gold);
        this.imortalObject=true;
        NUM++;
    }

    public Chest(int x, int y, int width, int height, int gold, int id) {
        super(x, y, width, height, 0, 0, "chest");
        this.id = id;
        setGold(gold);
        this.imortalObject=true;
        NUM++;
    }
    
    public Chest(int[] pos) {
        this(pos[0], pos[1], Chest.defWidth, Chest.defHeight, Chest.defGold, Chest.ID++);
    }
    
    public Chest(){
        this(VectorMath.randomPos(Chest.defWidth, Chest.defHeight));
    }
    
    
    public void destroy(){
        destroyed = true;
        Chest.NUM--;
    }
    
    
    public boolean isDestroyed() {
        if(health <= 0) destroy();
        
        return destroyed; 
    }
    

    public String getName() {
        return "Chest " + id;
    }
    
    public void giveGold(Player player){
        player.changeGold(this.gold);
        this.destroy();
    }
}
