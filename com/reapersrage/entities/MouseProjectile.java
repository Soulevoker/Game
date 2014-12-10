/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.reapersrage.entities;

/**
 *
 * @author David
 */
public class MouseProjectile extends Projectile {

    public MouseProjectile(int x, int y, int width, int height, int damageOnHit, double[] dir) {
        super(x, y, width, height, damageOnHit, dir, "mouse");
    }
    
}
