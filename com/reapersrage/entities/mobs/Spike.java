/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.reapersrage.entities.mobs;

/**
 *
 * @author David
 */
public class Spike extends Mob{
    int id;
    public Spike(int x, int y, int width, int height, int damageOnHit, int dps) {
        super(x, y, width, height, damageOnHit, dps, "spike");
        id = 0;
        this.imortalObject=true;
    }
    
    //Constructor that includes the mod's id
    public Spike(int x, int y, int width, int height, int damageOnHit, int dps, int id) {
        super(x, y, width, height, damageOnHit, dps, "spike");
        this.id = id;
        this.imortalObject=true;
    }
    
    
    public String getName() {
        return "Spike " + id;
    }
    
}
