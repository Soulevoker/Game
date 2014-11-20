package com.reapersrage.gfx;

public class Sprite {
	public final int SIZE;		//The length and width of square sprite
	private final int x, y;		//x and y positions of sprite (left/top?)
	public final int[] pixels;	//int representation of rgb values of
								// sprite in single array
	private SpriteSheet sheet;	//image width, height, and int array of pixels

	public static final Sprite grass = new Sprite(16, 0, 0, new SpriteSheet(
			"/com/reapersrage/res/textures/spritesheet.png"));
	public static final Sprite voidSprite = new Sprite(16, 2, 0,
			new SpriteSheet("/com/reapersrage/res/textures/spritesheet.png"));
	public static final Sprite playerSprite = new Sprite(16, 1, 0,
			new SpriteSheet("/com/reapersrage/res/textures/spritesheet.png"));
	public static final Sprite rockOnGrassSprite = new Sprite(16, 3, 0,
			new SpriteSheet("/com/reapersrage/res/textures/spritesheet.png"));

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		//Set x and y, each coming in as a block value (ex 0, 1, 2...)
		// converts by multiplying by block size (16) to obtain actual
		// bit location of x; for clean run, size should be the same (16)
		// for all objects. Potential error. Fix with static block size?
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		for (int y0 = 0; y0 < size; y0++) {
			for (int x0 = 0; x0 < size; x0++) {
				pixels[x0 + y0 * size] = sheet.pixels[(x0 + this.x)
						+ (y0 + this.y) * sheet.width];
			}
		}
	}

	public Sprite(final int size, int color) {
		this.SIZE = size;
		pixels = new int[size * size];
		setColor(color);
		x = 0;
		y = 0;
	}

	public void setColor(int color) {
		for (int i = 0; i < SIZE; i++) {
			pixels[i] = color;
		}
	}
}
