package com.reapersrage.gfx;

public class Sprite {
    public final int SIZE;
    private int x, y;
    public int[] pixels;
    private SpriteSheet sheet;

    public static Sprite grass = new Sprite(16, 0, 0, new SpriteSheet("/textures/spritesheet.png"));

    public Sprite(int size, int x, int y, SpriteSheet sheet) {
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        this.x = x * size;
        this.y = y * size;
        this.sheet = sheet;
        load();
        for (int y0 = 0; y0 < size; y0++) {
            for (int x0 = 0; x0 < size; x0++) {
                pixels[x0 + y0 * size] = sheet.pixels[(x0 + this.x) + (y0 + this.y) * sheet.width];
            }
        }
    }

    private void load() {

    }
}
