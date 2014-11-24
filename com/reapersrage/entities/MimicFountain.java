/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reapersrage.entities;

import com.reapersrage.game.VectorMath;

/**
 *
 * @author Chris
 */
public class MimicFountain extends Fountain {
    public static final int defHealing = -50;
    
    public MimicFountain(int x, int y, int width, int height, int healingOnHit, int dps) {
        super(x, y, width, height, healingOnHit);
        this.imortalObject = false;
    }
    
    public MimicFountain(int x, int y, int width, int height, int healingOnHit, int dps, int id) {
        super(x, y, width, height, healingOnHit, id);
        this.imortalObject = false;
    }
    
    public MimicFountain(int[] pos){
        super(pos, MimicFountain.defHealing);
        this.imortalObject = false;
    }
    
    public MimicFountain(){
        this(VectorMath.randomPos(Fountain.defWidth, Fountain.defHeight));
    }
    
    public void destroy(){
        destroyed = true;
        MimicFountain.NUM--;
    }
    
    
    public boolean isDestroyed() {
        if(health <= 0) destroy();
        
        return destroyed; 
    }

    
}
