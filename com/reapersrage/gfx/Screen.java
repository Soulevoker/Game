package com.reapersrage.gfx;

import com.reapersrage.game.Game;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import org.omg.CORBA.PUBLIC_MEMBER;

public class Screen {

    private int tileWidth, tileHeight;
    private GameTile background[][];


    public Screen(int width, int height) throws IOException {
    	tileWidth = width / Game.getMapWidth();
    	tileHeight = height / Game.getMapHeight();
    	
    	background = new GameTile[Game.getMapHeight()][Game.getMapWidth()];
    	
    	for(int y = 0; y < Game.getMapHeight(); y++){
    		for(int x = 0; x < Game.getMapWidth(); x++){
        		background[y][x] = new GameTile(Game.getLevel().getTile(y, x), (tileWidth * x), (tileHeight * y), tileWidth, tileHeight);
        	}
    	}
  	
    	
    }
    
    public void drawBackground(Graphics2D g){
    	for(int y = 0; y < Game.getMapHeight(); y++){
    		for(int x = 0; x < Game.getMapWidth(); x++){
        		g.drawImage(background[y][x].getImage(), background[y][x].getWidth(), background[y][x].getHeight(), null);
    	}
    }
    	
    }
   
}
