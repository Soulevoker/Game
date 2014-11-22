/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reapersrage.entities.mobs;

/**
 *
 * @author Chris
 */
public class MimicFountain extends Fountain {

    public MimicFountain(int x, int y, int width, int height, int healingOnHit, int dps) {
        super(x, y, width, height, healingOnHit, dps);
        this.imortalObject = false;
    }
    
    public MimicFountain(int x, int y, int width, int height, int healingOnHit, int dps, int id) {
        super(x, y, width, height, healingOnHit, dps, id);
        this.imortalObject = false;
    }
    
}
