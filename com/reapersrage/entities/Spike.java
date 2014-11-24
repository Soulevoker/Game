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
public class Spike extends Mob {

    int id;

    public Spike(int x, int y, int width, int height, int damageOnHit, int dps) {
        super(x, y, width, height, damageOnHit, dps, "spike");
        id = 0;
        this.wall = new boolean[4];
        this.imortalObject = true;
    }

    //Constructor that includes the mod's id
    public Spike(int x, int y, int width, int height, int damageOnHit, int dps, int id) {
        super(x, y, width, height, damageOnHit, dps, "spike");
        this.id = id;
        this.wall = new boolean[4];
        this.imortalObject = true;
    }

    public void update(Player person) {
        super.update(person);
        super.move(this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1);

    }

    public String getName() {
        return "Spike " + id;
    }

}
