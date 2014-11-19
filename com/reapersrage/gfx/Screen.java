package com.reapersrage.gfx;

import com.reapersrage.world.tiles.Tile;

import java.util.Arrays;
import java.util.Random;

public class Screen {
    private static final int MAP_WIDTH = 64;
    private static final int MAP_HEIGHT = 64;
    private static final int MAP_MASK = MAP_WIDTH - 1;
    private int width, height;
    private int[] pixels;
    int[] tiles = new int[MAP_WIDTH * MAP_HEIGHT];
    Random random = new Random();

    public int xOffset, yOffset;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = random.nextInt(0xffffff);
        }
        tiles[0] = 0x000000;
    }

    //TODO: Render Sprite Maps, and implement tile loading
    public void render(int xOffset, int yOffset) {
        for (int y = 0; y < height; y++) {
            int relativeY = y + yOffset;
            if (relativeY >= height || relativeY < 0) {
                continue;
            }

            for (int x = 0; x < width; x++) {
                int relativeX = x + xOffset;
                if (relativeX >= width || relativeX < 0) {
                    continue;
                }
                int tileIndex = ((relativeX >> 4) & MAP_MASK) + ((relativeY >> 4) & MAP_MASK) * MAP_WIDTH;
                //pixels[x + y * width] = tiles[tileIndex];
                pixels[relativeX + relativeY * width] = Sprite.grass.pixels[(x & 15) + (y & 15) * Sprite.grass.SIZE];
            }
        }

    }

    public void renderTile(int xp, int yp, Tile tile) {
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < tile.sprite.SIZE; y++) {
            int ya = y + yp;
            for (int x = 0; x < tile.sprite.SIZE; x++) {
                int xa = x + xp;

                if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) {
                    break;
                }
                if (xa < 0) {
                    xa = 0;
                }
                pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
            }
        }
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    /**
     * Get the pixel at the index given
     *
     * @param index
     * @return
     */
    public int getPixel(int index) {
        return this.pixels[index];
    }

    public int getScreenPixelAmount() {
        return this.pixels.length;
    }

    public int[] getScreenPixels() {
        return Arrays.copyOf(pixels, pixels.length);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setOffsets(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}
