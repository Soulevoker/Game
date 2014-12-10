package com.reapersrage.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

//this class is used to resize any image in the game to the size we want it to be.

public class ImageResizer {
	int scaledW, scaledH;
BufferedImage originalImage;
	BufferedImage resizedImage;
	
	public ImageResizer(BufferedImage img, int width, int height) throws IOException{
		originalImage = img;
		scaledW = width;
		scaledH = height;
		resizedImage = resize(originalImage, scaledW, scaledH);
	}
	
    //Resizes the player (for buffs?)
	public BufferedImage resize(BufferedImage original, int scaledWidth, int scaledHeight)
			throws IOException {

		resizedImage = new BufferedImage(scaledWidth,
				scaledHeight, original.getType());

		Graphics2D g2d = resizedImage.createGraphics();
		g2d.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();
		return resizedImage;
	}

	public BufferedImage getResizedImage() {
		return resizedImage;
	}

	
	
}
