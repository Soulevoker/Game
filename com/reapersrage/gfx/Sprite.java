package com.reapersrage.gfx;

public class Sprite {
    public final int SIZE;
    private final int x, y;
    public final int[] pixels;
    private SpriteSheet sheet;

    public static final Sprite grass = new Sprite(16, 0, 0, new SpriteSheet("/textures/spritesheet.png"));
    public static final Sprite voidSprite = new Sprite(16, 0);
    public static Sprite jim = new Sprite(16, 1, 0, new SpriteSheet("/textures/spritesheet.png"));

    public Sprite(int size, int x, int y, SpriteSheet sheet) {
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        this.x = x * size;
        this.y = y * size;
        this.sheet = sheet;
        for (int y0 = 0; y0 < size; y0++) {
            for (int x0 = 0; x0 < size; x0++) {
                pixels[x0 + y0 * size] = sheet.pixels[(x0 + this.x) + (y0 + this.y) * sheet.width];
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
