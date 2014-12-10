package com.reapersrage.entities;

import com.reapersrage.game.Debug;
import com.reapersrage.game.Game;
import com.reapersrage.game.ImageResizer;
import com.reapersrage.game.VectorMath;
import com.reapersrage.gfx.GameTile;
import com.reapersrage.gfx.Screen;
import com.reapersrage.input.Buttons;
import com.reapersrage.input.Keyboard;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Created with IntelliJ IDEA. User: Soulevoker Date: 10/27/13 Time: 1:24 PM
 * Copyright © Reapers' Rage 2013
 */
public class Player extends Entity {

	// O = original R = resized
    private BufferedImage OgunImage;
    private BufferedImage RgunImage;
    private int gunx;
    private int guny;

    // Projectiles the player has fired
    private ArrayList<Projectile> ProjList = new ArrayList<>();

    // Attributes of the player
    private int health;
    private int mana;
    private int gold;

    // Pixels for the player to move every update
    private double[] velocity = new double[2];
	// private final int START_SPEED = 5;
    // Maximum velocity
    private final double MAX_V;
    // How much to accelerate the player by key presses
    private final double acceleration;
    // How much the player slows down due to the map
    private final double friction;
    // If the player is about to hit a wall NORTH EAST SOUTH WEST
    private final int DEF_HEALTH = 2000000;
    private final int DEF_MANA = 100000000;
    private Random random = new Random(); // so randum xD

	//private boolean[] playerDirs;
    // This way there's no autofire
    private boolean alreadyFired;
    // This way there's no autoblink
    private boolean alreadyBlinked;
    private boolean alreadyBlast;

    private int angle;
    private int gunAngle;
    private double[] facing;
    private double[] gunFacing;

    private int xNew;
    private int yNew;

    private ImageResizer IRgun;

    public Player(int x, int y, int width, int height) {
        this.mana = DEF_MANA;
        // Initial x position (pixels)
        this.x = x;
        // Initial y position (pixels)
        this.y = y;
        // Widith and height of player
        this.width = width;
        this.height = height;
        // Velocty increase per update
        this.acceleration = .8;
        this.friction = 1;
        this.velocity = new double[]{0.0, 0.0};
        this.MAX_V = 10.0;
        //this.playerDirs = new boolean[10];
        this.alreadyFired = false;
        this.alreadyBlinked = false;
        this.alreadyBlast = false;
        this.wall = new boolean[4];
        this.gunx = x + (width / 3);
        this.guny = y;

        xNew = x;
        yNew = y;

        try {
            OImage = ImageIO
                    .read(GameTile.class
                            .getResourceAsStream("/com/reapersrage/res/textures/robot.png"));
            OgunImage = ImageIO
                    .read(GameTile.class
                            .getResourceAsStream("/com/reapersrage/res/textures/robotgun1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            IR = new ImageResizer(OImage, this.width, this.height);
            IRgun = new ImageResizer(OgunImage, this.width / 3, this.height);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RImage = IR.getResizedImage();
        RgunImage = IRgun.getResizedImage();

        angle = 0;
        gunAngle = 0;
        this.facing = new double[]{0, 0};
        this.gunFacing = new double[]{0, 0};

        // sets initial health
        health = DEF_HEALTH;
    }

    /**
     * Each frame, update the player
     *
     * @param person - set to null, this is the player
     */
    public void update(Player person) {
        parseInput();
        boolean[] collision = checkCollision(x, y, velocity);
        move();
        updateProjectiles();
        health += 1;
        mana += 1;
        setDebug();

    }

    /**
     * Updates the player's projectiles
     */
    public void updateProjectiles() {
        Iterator<Projectile> projIterator = ProjList.iterator();
        while (projIterator.hasNext()) {
            Projectile currProj = projIterator.next();
            if (currProj.destroyed) {
                projIterator.remove();
            }
            currProj.update();
        }
    }

    /**
     * Moves the player
     */
    private void move() {
        xNew = x;
        yNew = y;
        boolean[] collision = checkCollision(x, y, velocity);
        // Movement of the player
        if (!collision[1]) {
            // Down
            yNew = yNew + (int) velocity[1];
        }
        if (!collision[0]) {
            // Right
            xNew = xNew + (int) velocity[0];
        }
		// If we've hit a wall, set the position to just touch the wall and set
        // veloicty to 0
        if (wall[0]) {
            // North
            yNew = 0;
            velocity[1] = 0;
        } else if (wall[1]) {
            // East
            xNew = Game.getStaticWidth() - width;
            velocity[0] = 0;
        } else if (wall[2]) {
            // South
            yNew = Game.getStaticHeight() - height;
            velocity[1] = 0;
        } else if (wall[3]) {
            // West
            xNew = 0;
            velocity[0] = 0;
        }

        if (Game.getScreen().isScreenX()) {
            x = xNew;
        }
        if (Game.getScreen().isScreenY()) {
            y = yNew;
        }

        gunx = x + (width / 3);
        guny = y;
    }

    /**
     * Parses the keypresses If the key is pressed, increments the velocity by
     * acceleration velocity gets updated based on the inputs and acceleration
     */
    public void parseInput() {
        //BEGIN PARSING KEY PRESSES FOR INPUT
        double[] oldFacing = new double[2];
        System.arraycopy(facing, 0, oldFacing, 0, 2);
        this.facing = new double[]{0, 0};
        if (Buttons.up) {
            // Up
            if (-1.0 * velocity[1] < MAX_V) {
                velocity[1] -= acceleration;
            }
            if (velocity[1] < -MAX_V) {
                velocity[1] = -MAX_V;
            }
            facing[1] += 1;
        }
        if (Buttons.down) {
            // Down
            if (velocity[1] < MAX_V) {
                velocity[1] += acceleration;
            }
            if (velocity[1] > MAX_V) {
                velocity[1] = MAX_V;
            }
            facing[1] += -1;
        }
        if (Buttons.left) {
            // Left
            if (-velocity[0] < MAX_V) {
                velocity[0] -= acceleration;
            }
            if (velocity[0] < -MAX_V) {
                velocity[0] = -MAX_V;
            }
            facing[0] -= 1;
        }
        if (Buttons.right) {
            // Right
            if (velocity[0] < MAX_V) {
                velocity[0] += acceleration;
            }
            if (velocity[0] > MAX_V) {
                velocity[0] = MAX_V;
            }
            facing[0] += 1;
        }
        // if not accelerating in y direction, decelerate
        if (!Buttons.up && !Buttons.down && velocity[1] != 0) {
            double newVelocity = velocity[1];
            newVelocity -= velocity[1] > 0 ? friction : -friction;
            if (velocity[1] > 0 && newVelocity < 0) {
                newVelocity = 0;
            } else if (velocity[1] < 0 && newVelocity > 0) {
                newVelocity = 0;
            }
            velocity[1] = newVelocity;

        }
        // If not accelerating in x, decelerate
        if (!Buttons.left && !Buttons.right && velocity[0] != 0) {
            double newVelocity = velocity[0];
            newVelocity -= velocity[0] > 0 ? friction : -friction;
            if (velocity[0] < 0 && newVelocity > 0) {
                newVelocity = 0;
            } else if (velocity[0] > 0 && newVelocity < 0) {
                newVelocity = 0;
            }
            velocity[0] = newVelocity;
        }
        // If not accelerating at all use the old facing
        if (!Buttons.up && !Buttons.down && !Buttons.left && !Buttons.right) {
            facing = oldFacing;
        }
        System.out.println("Facing: " + facing[0] + " " + facing[1]);
        angle = (int) (VectorMath.atan(facing));

                //BEGIN CHECKING THE KEYPRESSES FOR SKILL TRIGGERS
        //fire projectile and set the gun pos to that projectiles velocity
        if ((Buttons.projUp || Buttons.projDown || Buttons.projLeft || Buttons.projRight)
                && !this.alreadyFired) {
            this.alreadyFired = true;
            gunFacing = fire();
            gunAngle = (int) (VectorMath.atan(gunFacing));
        }
        if (this.alreadyFired
                && !(Buttons.projUp || Buttons.projDown || Buttons.projLeft || Buttons.projRight)) {
            this.alreadyFired = false;
        }
        // blink
        if (Buttons.space && !this.alreadyBlinked) {
            this.alreadyBlinked = true;
            int x1 = random.nextInt(Game.getStaticWidth() - width);
            int y1 = random.nextInt(Game.getStaticHeight() - height);
            setPos(x1, y1);
        }
        if (!Buttons.space && this.alreadyBlinked) {
            this.alreadyBlinked = false;
        }
        if (Buttons.blast && !this.alreadyBlast) {
            this.alreadyBlast = true;
            blast();
        }
        if (!Buttons.blast && this.alreadyBlast) {
            this.alreadyBlast = false;
        }

    }

    /**
     * Sets the text on the debug panel for the player
     */
    private void setDebug() {
        //BEGIN LOGGING KEYPRESSES ON THE DEBUG PANNEL
        String text = "<html>";
        text += "projUp: " + Buttons.projUp + "<br>";
        text += "projDown: " + Buttons.projDown + "<br>";
        text += "projLeft: " + Buttons.projLeft + "<br>";
        text += "projRight: " + Buttons.projRight + "<br>";
        text += "alreadyFired: "
                + !(Buttons.projUp || Buttons.projDown || Buttons.projLeft || Buttons.projRight) + " <br>";
        text += "angle" + angle + " <br>";
        text = text + "</html>";
        Game.debugPanel.setLabel(2, text);
    }

    /**
     * Checks for a collision in both x and y and return an array of booleans
     * indicating such for collisions with walls. Collisions with mobs is
     * handeled by each individual mob
     *
     * @param x - current x position
     * @param y - current y position
     * @param v - velocity (vector)
     * @return whether or not there will be a collision in the x or y
     * respectively
     */
    public boolean[] checkCollision(int x, int y, double[] v) {
        boolean[] collisions = new boolean[]{false, false};
        wall[0] = false;
        wall[1] = false;
        wall[2] = false;
        wall[3] = false;
        // WEST wall
        if (x + (int) v[0] < 0) {
            collisions[0] = true;
            wall[3] = true;
        } // EAST wall
        else if (x + (int) v[0] + width > Game.getStaticWidth()) {
            collisions[0] = true;
            wall[1] = true;
        }
        // NORTH wall
        if (y + (int) v[1] < 0) {
            collisions[1] = true;
            wall[0] = true;
        } // SOUTH Wall
        else if (y + (int) v[1] + height > Game.getStaticHeight()) {
            collisions[1] = true;
            wall[2] = true;
        }

        return collisions;
    }

    /**
     * Draws the player to the given graphics
     *
     * @param g - grahpics on which to dtaw the player
     */
    public void draw(Graphics2D g) {
        Graphics2D gg = (Graphics2D) g.create();
        gg.rotate(Math.toRadians(angle), x + (width / 2), y + (height / 2));
        gg.drawImage(RImage, x, y, null);
        gg.dispose();

        Graphics2D gun = (Graphics2D) g.create();
        gun.rotate(Math.toRadians(gunAngle), x + (width / 2), y + (height / 2));
        gun.drawImage(RgunImage, gunx, guny, null);
        gun.dispose();

        Iterator<Projectile> projIterator = ProjList.iterator();
        // Draw projectile
        while (projIterator.hasNext()) {
            Projectile currProj = projIterator.next();
            currProj.draw(g);
        }
    }

    public int getHealth() {
        return health;
    }

    public int getGold() {
        return gold;
    }

    public int getMana() {
        return mana;
    }

    /**
     * Changes health by desired amount
     *
     * @param change amount to change (negative deals damage)
     */
    public void changeHealth(int change) {
        health += change;
    }

    /**
     * Changes gold
     *
     * @param change - amount to change (negative to take)
     */
    public void changeGold(int change) {
        gold += change;
    }

    /**
     *
     * @return x velocity
     */
    public int getVelX() {
        return (int) velocity[0];
    }

    /**
     *
     * @return y vecloty
     */
    public int getVelY() {
        return (int) velocity[1];
    }

    /**
     * Is it transparent
     *
     * @param x x pixel
     * @param y y pixel
     * @return true iff transparent
     */
    public boolean isTransparent(int x, int y) {
        int pixel = RImage.getRGB(x, y);
        if ((pixel >> 24) == 0x00) {
            return true;
        } else {
            return false;
        }
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Fires a bullet. The initial position of the bullet is the location of the
     * player's gun. The initial velocity of the bullet is determined by
     * keypresses. All bullets move under no net force until collision.
     *
     * @return the velocity of the bullet fired
     */
    private double[] fire() {
        int bulletx = x;
        int bullety = y;

        double[] currVel = new double[2];
        // Don't fire if velocity will be zero
        boolean[] willFire = new boolean[]{true, true};

        if (Buttons.projUp && !Buttons.projDown) {
            // Up and not down
            currVel[1] = -1;
        } else if (Buttons.projDown && !Buttons.projUp) {
            // Down and not up
            currVel[1] = 1;
        } else {
			// Both up and down or neither
            // currVel[1] = 0;
            willFire[1] = false;
        }
        if (Buttons.projLeft && !Buttons.projRight) {
            // left and not right
            currVel[0] = -1;
        } else if (Buttons.projRight && !Buttons.projLeft) {
            // Right and not left
            currVel[0] = 1;
        } else {
			// Both left and right or neither
            // currVel[0] = 0;
            willFire[0] = false;
        }

        if (Buttons.projUp) {
            bulletx = x + (width / 2);
            bullety = y;
        }
        if (Buttons.projDown) {
            bulletx = x + (width / 2);
            bullety = y + height;
        }
        if (Buttons.projRight) {
            bulletx = x + width;
            bullety = y + (height / 2);
        }
        if (Buttons.projLeft) {
            bulletx = x;
            bullety = y + (height / 2);
        }

        if (Buttons.projUp && Buttons.projRight) {
            bulletx = x + width;
            bullety = y;
        }
        if (Buttons.projUp && Buttons.projLeft) {
            bulletx = x;
            bullety = y;
        }
        if (Buttons.projDown && Buttons.projRight) {
            bulletx = x + width;
            bullety = y + height;
        }
        if (Buttons.projDown && Buttons.projLeft) {
            bulletx = x;
            bullety = y + height;
        }
        bulletx += Game.getScreen().getX();
        bullety += Game.getScreen().getY();
        if (willFire[0] || willFire[1]) {
            ProjList.add(new FireBall(bulletx, bullety,
                    15, 15, 50, VectorMath.scaleVector(currVel, 15)));
        }
        return currVel;
    }

    /**
     * Fires a bunch of bullets
     *
     * @deprecated this for loop takes too long
     */
    private void blast() {
        if (mana >= 100) {
            mana -= 100;

            for (int i = 0; i < 30; i++) {
                double[] newDir = new double[2];
                newDir[0] = (double) random.nextInt(40) - 20;
                newDir[1] = (double) random.nextInt(40) - 20;
                ProjList.add(new FireBall(this.x + this.width - 20,
                        this.y + 10, 15, 15, 500, newDir));
            }
        }
    }

    public Iterator getProjectiles() {
        return ProjList.iterator();
    }

    public boolean isDestroyed() {
        return (this.health < 0);
    }

    public void destroy() {
        this.health = 0;
    }
}
