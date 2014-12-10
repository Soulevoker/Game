package com.reapersrage.gfx;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.reapersrage.game.ImageResizer;

public class GameTile {
	private boolean isPassable;
	private int x;
	private int y;
	private int tileWidth;
	private int tileHeight;
	
	private int tileType;
	
	ImageResizer IR;
	
	private BufferedImage originalImage;
	private BufferedImage resizedImage;

	public GameTile(int tileType, int xLocation, int yLocation, int width,
			int height) throws IOException {
		this.tileType = tileType;
		x = xLocation;
		y = yLocation;
		tileWidth = width;
		tileHeight = height;
		if(this.tileType != 0){
			setTile(tileType);
		IR = new ImageResizer(originalImage, tileWidth, tileHeight);
		resizedImage = IR.getResizedImage();
		}
	}


	private void setTile(int type) {
		switch (type) {
		case 0:
			isPassable = true;
			try {
				originalImage = ImageIO
						.read(GameTile.class
								.getResourceAsStream("/com/reapersrage/res/textures/brown.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 1:
			isPassable = true;
			try {
				originalImage = ImageIO
						.read(GameTile.class
								.getResourceAsStream("/com/reapersrage/res/textures/green.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			isPassable = false;
			try {
				originalImage = ImageIO
						.read(GameTile.class
								.getResourceAsStream("/com/reapersrage/res/textures/blue.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	public void ChangeX(int velx){
		x += (velx * -1);
	}
	
	public void ChangeY(int vely){
		y += (vely * -1);
	} 
	
	public BufferedImage getImage() {
		return resizedImage;
	}

        /**
         * Pixel location of tile with respect to the top of the screen
         * @return x location (local)
         */
	public int getX() {
		return x;
	}

        /**
         * Pixel location of tile with respect to the top of the screen
         * @return y location (local)
         */
	public int getY() {
		return y;
	}


	public int getTileType() {
		return tileType;
	}
        
        public int getTileWidth() {
            return tileWidth;
        }
        
        public int getTileHeight() {
            return tileHeight;
        }


	public void setTileType(int tileType) {
		this.tileType = tileType;
	}


	
	

}
