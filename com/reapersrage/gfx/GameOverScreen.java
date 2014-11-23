package com.reapersrage.gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.reapersrage.game.Game;
import com.reapersrage.game.ImageResizer;

public class GameOverScreen{
	private int width;
	private int height;
	private static int selector;
	private int selectorX;
	private int selectorY;
	
	private ImageResizer IR;
	private BufferedImage OImage;
	private BufferedImage RImage;
	
	private BufferedImage OselectorImage;
	private BufferedImage RselectorImage;
	private ImageResizer IRselector;
	
	private int selectorTransparency;
	


	public GameOverScreen(int width, int height){
	this.width = width;
	this.height = height;
	selector = 0;
	selectorTransparency = 100;
	try {
		OImage = ImageIO
				.read(GameTile.class
						.getResourceAsStream("/com/reapersrage/res/textures/gameover.png"));
		OselectorImage = ImageIO
				.read(GameTile.class
						.getResourceAsStream("/com/reapersrage/res/textures/border.png"));
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	try {
		IR = new ImageResizer(OImage, this.width, this.height);
		IRselector = new ImageResizer(OselectorImage, 355, 80);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	RImage = IR.getResizedImage();
	RselectorImage = IRselector.getResizedImage();
	}

	public static int getSelector() {
		return selector;
	}
	
	public void Update(){
		if(selector == 0){
			selectorX = 220;
			selectorY = 270;
		} else if(selector == 1){
			selectorX = 220;
			selectorY = 420;
		}
	}
	
	  public void drawBackground(Graphics2D g){
	    	
	        		g.drawImage(RImage, 0 , 0 , null);
	        		
	        		g.drawImage(RselectorImage, selectorX, selectorY, null);
	    	
	    }

	public static void selectorIncrement() {
		if(selector<1){
		selector++;
		} else{
			selector = 0;
		}
	}
	public static void selectorDecrement() {
		if(selector>0){
		selector--;
		} else{
			selector = 1;
		}
	}
	
	
}
