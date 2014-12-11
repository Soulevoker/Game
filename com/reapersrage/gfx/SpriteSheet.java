package com.reapersrage.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
	public int width, height;
	public int[] pixels;

	public SpriteSheet(String path) {
		BufferedImage image;
		try {
			//get temporary BufferedImage
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
			width = image.getWidth();	//set width
			height = image.getHeight();	//set height
			// Set pixels
			pixels = image.getRGB(0, 0, width, height, null, 0, width);	//RETURNS SINGLE ARRAY?
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * for (int i = 0; i < pixels.length; i++) { pixels[i] = (pixels[i] &
		 * 0xff) / 64; }
		 */
	}
}
