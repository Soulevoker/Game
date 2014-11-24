/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reapersrage.entities.mobs;

import com.reapersrage.game.VectorMath;

/**
 *
 * @author David
 */
public class Fountain extends Mob {

    int id;
    public static final int defWidth = 80;
    public static final int defHeight = 80;
    public static final int defHealing = 10;
    public static int ID = 0;
    //Number of ghosts summoned
    public static int NUM = 0;

    public Fountain(int x, int y, int width, int height, int healingOnHit) {
        super(x, y, width, height, -healingOnHit, 0, "fountain");
        this.id = 0;
        this.imortalObject=true;
        NUM++;
    }

    public Fountain(int x, int y, int width, int height, int healingOnHit, int id) {
        super(x, y, width, height, -healingOnHit, 0, "fountain");
        this.id = id;
        this.imortalObject=true;
        NUM++;
    }
    
    public Fountain(int[] pos){
        this(pos[0], pos[1], Fountain.defWidth, Fountain.defHeight, Fountain.defHealing, Fountain.ID++);
    }
    
    public Fountain(int[] pos, int healing){
        this(pos[0], pos[1], Fountain.defWidth, Fountain.defHeight, healing, Fountain.ID++);
    }
    
    public Fountain(){
        this(VectorMath.randomPos(Fountain.defWidth, Fountain.defHeight));
    }

    public String getName() {
        return "Fountain " + id;
    }
    
    public void destroy(){
        destroyed = true;
        Fountain.NUM--;
    }
    
    
    public boolean isDestroyed() {
        if(health <= 0) destroy();
        
        return destroyed; 
    }

}
