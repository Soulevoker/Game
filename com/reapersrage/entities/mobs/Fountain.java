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
public class Fountain extends Mob {

    int id;

    public Fountain(int x, int y, int width, int height, int healingOnHit, int dps) {
        super(x, y, width, height, -healingOnHit, dps, "fountain");
        this.id = 0;
        this.imortalObject=true;
    }

    public Fountain(int x, int y, int width, int height, int healingOnHit, int dps, int id) {
        super(x, y, width, height, -healingOnHit, dps, "fountain");
        this.id = id;
        this.imortalObject=true;
    }

    public String getName() {
        return "Fountain " + id;
    }

}
