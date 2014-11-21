package com.reapersrage.gfx;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameTile {
	private boolean isPassable;
	private int x;
	private int y;
	private int tileWidth;
	private int tileHeight;
	private BufferedImage originalImage;
	private BufferedImage resizedImage;

	public GameTile(int tileType, int xLocation, int yLocation, int width,
			int height) throws IOException {
		setTile(tileType);
		x = xLocation;
		y = yLocation;
		tileWidth = width;
		tileHeight = height;
		resizedImage = resize(originalImage, width, height);
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

	public BufferedImage resize(BufferedImage original, int scaledWidth, int scaledHeight)
			throws IOException {

		resizedImage = new BufferedImage(scaledWidth,
				scaledHeight, original.getType());

		Graphics2D g2d = resizedImage.createGraphics();
		g2d.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();
		return resizedImage;
	}

	public BufferedImage getImage() {
		return resizedImage;
	}

	public int getWidth() {
		return x;
	}

	public int getHeight() {
		return y;
	}

}
