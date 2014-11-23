/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.reapersrage.entities.items;

/**
 *
 * @author David
 */
public class GoldPiece extends Item {

    public GoldPiece(int x, int y, int width, int height, int gold) {
        super(x, y, width, height, gold, "gold");
    }
    
    public GoldPiece(int[] pos, int width, int height, int gold) {
        super(pos[0], pos[1], width, height, gold, "gold");
    }
    
}
