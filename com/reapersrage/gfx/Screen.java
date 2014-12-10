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

//import org.omg.CORBA.PUBLIC_MEMBER;
public class Screen {

    private int tileWidth, tileHeight;
    private GameTile background[][];

    private BufferedImage backgroundTile;
    private ImageResizer IR;

    private int endx;
    private int endy;

    private int starty;
    private int startx;

    private boolean screenX;
    private boolean screenY;

    public Screen(int width, int height) throws IOException {
        tileWidth = width / Game.getMapWidth();
        tileHeight = height / Game.getMapHeight();

        endx = Game.getMapWidth() + 2;
        endy = Game.getMapHeight() + 2;

        startx = 0;
        starty = 0;

        screenX = false;
        screenY = false;

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
                        (tileWidth * (x - 2)), (tileHeight * (y - 2)), tileWidth,
                        tileHeight);

            }
        }

    }

    public void Update(Player p) {

        for (int y = 0; y < Game.getAbsolute_MapHeight(); y++) {
            for (int x = 0; x < Game.getAbsolute_MapWidth(); x++) {

                if (!screenX) {
                    background[y][x].ChangeX(p.getVelX());
                }
                if (!screenY) {
                    background[y][x].ChangeY(p.getVelY());
                }

            }
        }

        if (background[starty + 2][startx + 2].getX() <= 0 && endx + 1 < Game.getAbsolute_MapWidth()) {
            startx++;
            endx++;
        }
        if (background[starty + 2][startx + 2].getY() <= 0 && endy + 1 < Game.getAbsolute_MapHeight()) {
            starty++;
            endy++;
        }
        if (background[endy - 2][endx - 2].getX() >= Game.getStaticWidth() && startx - 1 > -1) {
            startx--;
            endx--;
        }
        if (background[endy - 2][endx - 2].getY() >= Game.getStaticHeight() && starty - 1 > -1) {
            starty--;
            endy--;
        }

        if (startx == 0 && background[starty][startx].getX() >= 0) {
            screenX = true;
        }
        if (starty == 0 && background[starty][startx].getY() >= 0) {
            screenY = true;
        }
        if (endx == Game.getAbsolute_MapWidth() - 1 && background[endy][endx].getX() <= Game.getStaticWidth()) {
            screenX = true;
        }
        if (endy == Game.getAbsolute_MapHeight() - 1 && background[endy][endx].getY() <= Game.getStaticHeight()) {
            screenY = true;
        }

        if (p.getX() + p.getWidth() / 2 >= Game.getStaticWidth() / 2 && Game.getButtonsPressed().right && startx == 0) {
            screenX = false;
        }
        if (p.getX() + p.getWidth() / 2 <= Game.getStaticWidth() / 2 && Game.getButtonsPressed().left && endx == Game.getAbsolute_MapWidth() - 1) {
            screenX = false;
        }
        if (p.getY() + p.getHeight() / 2 >= Game.getStaticHeight() / 2 && Game.getButtonsPressed().down && starty == 0) {
            screenY = false;
        }
        if (p.getY() + p.getHeight() / 2 <= Game.getStaticHeight() / 2 && Game.getButtonsPressed().up && endy == Game.getAbsolute_MapHeight() - 1) {
            screenY = false;
        }

    }

    public void drawBackground(Graphics2D g) {
        g.drawImage(backgroundTile, 0, 0, null);

        for (int y = starty; y < endy; y++) {
            for (int x = startx; x < endx; x++) {

                if (background[y][x].getTileType() != 0) {
                    g.drawImage(background[y][x].getImage(), background[y][x].getX(), background[y][x].getY(), null);
                }

            }
        }

    }

    public void clean() {
        background = new GameTile[Game.getMapHeight()][Game.getMapWidth()];
    }

    public boolean isScreenX() {
        return screenX;
    }

    public void setScreenX(boolean screenX) {
        this.screenX = screenX;
    }

    public boolean isScreenY() {
        return screenY;
    }

    public void setScreenY(boolean screenY) {
        this.screenY = screenY;
    }

    /**
     * Returns the X position of the LCS
     *
     * @return x
     */
    public int getX() {
        return background[starty][startx].getTileWidth() * (startx - 1) - background[starty][startx].getX();
    }

    /**
     * Returns he Y position of the LCS
     *
     * @return y
     */
    public int getY() {
        return background[starty][startx].getTileHeight() * (starty - 1) - background[starty][startx].getY();
    }

}
