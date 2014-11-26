/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.reapersrage.entities;

import com.reapersrage.entities.Player;
import com.reapersrage.game.Game;

/**
 *
 * @author David
 */
public class GoldPiece extends Item {
    public static int NUM;
    public GoldPiece(int x, int y, int width, int height, int gold) {
        super(x, y, width, height, gold, "gold");
        NUM++;
    }
    
    public GoldPiece(int[] pos, int width, int height, int gold) {
        super(pos[0], pos[1], width, height, gold, "gold");
        NUM++;
    }
    
    public GoldPiece(int[] pos, int amount){
        this(pos, 10, 10, amount);
    }
    
    public void update(Player person) {
        if (isCollided(person)) {
            giveGold(person);
            destroy();
        }
    }
    
    public void destroy(){
        this.destroyed = true;
        NUM--;
    }
    
}
