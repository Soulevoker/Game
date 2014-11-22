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
public class Chest extends Mob {
    private int id;
    
    public Chest(int x, int y, int width, int height, int gold) {
        super(x, y, width, height, 0, 0, "chest");
        this.id = 0;
        setGold(gold);
    }

    public Chest(int x, int y, int width, int height, int gold, int id) {
        super(x, y, width, height, 0, 0, "chest");
        this.id = id;
        setGold(gold);
    }

    public String getName() {
        return "Chest " + id;
    }
}
