package com.reapersrage.gfx;

import com.reapersrage.entities.Player;
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

	
	private int endx;
	private int endy;
	
	private int starty;
	private int startx;

	public Screen(int width, int height) throws IOException {
		tileWidth = width / Game.getMapWidth();
		tileHeight = height / Game.getMapHeight();


		endx = Game.getMapWidth() + 2;
		endy = Game.getMapHeight() + 2;
		
		startx = 0;
		starty = 0;

		
		try {
			backgroundTile = ImageIO
					.read(GameTile.class
							.getResourceAsStream("/com/reapersrage/res/textures/brown.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		IR = new ImageResizer(backgroundTile, width, height);
		backgroundTile = IR.getResizedImage();

		background = new GameTile[Game.getAbsolute_MapHeight()][Game
				.getAbsolute_MapWidth()];

		for (int y = 0; y < Game.getAbsolute_MapHeight(); y++) {
			for (int x = 0; x < Game.getAbsolute_MapWidth(); x++) {
				background[y][x] = new GameTile(Game.getLevel().getTile(y, x),
						(tileWidth * (x-2)), (tileHeight * (y-2)), tileWidth,
						tileHeight);
			}
		}

	}

	public void Update(Player p) {

		for (int y = 0; y < Game.getAbsolute_MapHeight(); y++) {
			for (int x = 0; x < Game.getAbsolute_MapWidth(); x++) {
						background[y][x].ChangeX(p.getVelX());
						background[y][x].ChangeY(p.getVelY());				
			
					
			}
		}

		
		
		if(background[starty+2][startx+2].getWidth() <= 0 && endx+1 < Game.getAbsolute_MapWidth()){
			startx++;
			endx++;
		}
		if(background[starty+2][startx+2].getHeight() <= 0  && endy+1 < Game.getAbsolute_MapHeight()){
			starty++;
			endy++;
		}
		if(background[endy-2][endx-2].getWidth() >= Game.getStaticWidth() && startx-1 > -1){
			startx--;
			endx--;
		}
		if(background[endy-2][endx-2].getHeight() >= Game.getStaticHeight() && starty-1 > -1){
			starty--;
			endy--;
		}

		
	}

	public void drawBackground(Graphics2D g) {
		g.drawImage(backgroundTile, 0, 0, null);

		for (int y = starty; y < endy; y++) {
			for (int x = startx; x < endx; x++) {

				if (background[y][x].getTileType() != 0) {
					g.drawImage(background[y][x].getImage(),background[y][x].getWidth(),background[y][x].getHeight(), null);
				}

			}
		}

	}

	public void clean() {
		background = new GameTile[Game.getMapHeight()][Game.getMapWidth()];
	}

}
