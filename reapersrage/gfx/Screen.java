package com.reapersrage.gfx;

import java.util.ArrayList;
import java.util.List;

public class Screen {
    private static final int MAP_WIDTH = 64; //Must be 2^x
    private static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;
    public final int W, H;
    public int[] tiles = new int[MAP_WIDTH * MAP_WIDTH * 2];
    public int[] colors = new int[MAP_WIDTH * MAP_WIDTH * 4];
    public int[] databits = new int[MAP_WIDTH * MAP_WIDTH];
    public int xScroll;
    public int yScroll;
    private List<Sprite> sprites = new ArrayList<>();
    private SpriteSheet sheet;

    public Screen(int w, int h, SpriteSheet sheet) {
        this.W = w;
        this.H = h;
        this.sheet = sheet;
        for (int i = 0; i < MAP_WIDTH * MAP_WIDTH; i++) {
            colors[(i * 4)] = 0xff00ff;
            colors[i * 4 + 1] = 0x00ffff;
            colors[i * 4 + 2] = 0xffff00;
            colors[i * 4 + 3] = 0xffffff;
        }
    }
    @Deprecated
    /**
     * @Deprecated
     * Old way of rendering our screen.
     */
    public void render(int[] pixels, int offs, int row) {
        for (int yt = yScroll >> 3; yt <= (yScroll + H) >> 3; yt++) {
            int y0 = yt * 8 - yScroll;
            int y1 = y0 + 8;
            if (y0 < 0) {
                y0 = 0;
            }
            if (y1 > H) {
                y1 = H;
            }
            for (int xt = xScroll >> 3; xt <= (xScroll + W) >> 3; xt++) {
                int x0 = xt * 8 - xScroll;
                int x1 = x0 + 8;
                if (x0 < 0) {
                    x0 = 0;
                }
                if (x1 > W) {
                    x1 = W;
                }
                int tileIndex = (xt & (MAP_WIDTH_MASK)) + (yt & (MAP_WIDTH_MASK)) * MAP_WIDTH;
                for (int y = y0; y < y1; y++) {
                    int sp = ((y + yScroll) & 7) * sheet.width + ((x0 + xScroll) & 7);
                    int tp = offs + x0 + y * row;

                    for (int x = x0; x < x1; x++) {
                        pixels[tp++] = colors[tileIndex * 4 + sheet.pixels[sp++]];
                    }
                }
            }
        }
    }

    public void render() {

    }

    public void clear() {

    }
}
