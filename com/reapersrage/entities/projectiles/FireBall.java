/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.reapersrage.entities.projectiles;

/**
 *
 * @author David
 */
public class FireBall extends Projectile {

    public FireBall(int x, int y, int width, int height, int damageOnHit, double[] dir) {
        super(x, y, width, height, damageOnHit, dir, "fireball");
    }
    
}
