package com.reapersrage.gfx;

import com.reapersrage.game.Game;
import com.reapersrage.game.ImageResizer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

import org.omg.CORBA.PUBLIC_MEMBER;

public class Screen {

    private int tileWidth, tileHeight;
    private GameTile background[][];
    
    private BufferedImage backgroundTile;
    private ImageResizer IR;
    
    


    public Screen(int width, int height) throws IOException {
    	tileWidth = width / Game.getMapWidth();
    	tileHeight = height / Game.getMapHeight();
    	

    	try {
    		backgroundTile = ImageIO
					.read(GameTile.class
							.getResourceAsStream("/com/reapersrage/res/textures/brown.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	IR = new ImageResizer(backgroundTile, width, height);
    	backgroundTile = IR.getResizedImage();
    	
    	
    	background = new GameTile[Game.getMapHeight()][Game.getMapWidth()];
    	
    	for(int y = 0; y < Game.getMapHeight(); y++){
    		for(int x = 0; x < Game.getMapWidth(); x++){
        		background[y][x] = new GameTile(Game.getLevel().getTile(y, x), (tileWidth * x), (tileHeight * y), tileWidth, tileHeight);
        	}
    	}
    	
    	
  	
    	
    }
    
    public void drawBackground(Graphics2D g){
    	g.drawImage(backgroundTile, 0, 0, null);
    	
    	for(int y = 0; y < Game.getMapHeight(); y++){
    		for(int x = 0; x < Game.getMapWidth(); x++){
    			
    			if(background[y][x].getTileType() != 0){
        		g.drawImage(background[y][x].getImage(), background[y][x].getWidth(), background[y][x].getHeight(), null);
    			}
    			
    	}
    }
    	
    }
    
    public void clean(){
    	background = new GameTile[Game.getMapHeight()][Game.getMapWidth()];
    }
   
}
