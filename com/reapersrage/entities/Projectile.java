package com.reapersrage.entities;

import com.reapersrage.entities.Mob;
import com.reapersrage.entities.Player;
import com.reapersrage.game.Game;
import com.reapersrage.gfx.GameTile;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Projectile class for things such as fileballs
 *
 * @author David
 */
public abstract class Projectile extends com.reapersrage.entities.Entity {

    //protected int x;
    //protected int y;
    //O = original R = resized
    //protected BufferedImage OImage;
    //protected BufferedImage RImage;
    //Initial velocity of the projectile
   
    protected double[] dir;
    //protected int width;
    //protected int height;

    protected int damageOnHit; //damage player takes on impact
    protected String name; //name of the mob
    //private boolean[] wall; //has the projectile hit a wall?
    int id;
    //public boolean destroyed;

    public Projectile(int x, int y, int width, int height, int damageOnHit, double[] dir, String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.damageOnHit = damageOnHit;
        this.id = 0;
        this.dir = dir;
        this.destroyed = false;
        this.wall = new boolean[4];
        String fileName = "/com/reapersrage/res/textures/";
        fileName = fileName + name + ".png";
        try {
            OImage = ImageIO
                    .read(GameTile.class
                            .getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            resize(OImage, this.width, this.height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage resize(BufferedImage original, int scaledWidth, int scaledHeight)
            throws IOException {

        RImage = new BufferedImage(scaledWidth,
                scaledHeight, original.getType());

        Graphics2D g2d = RImage.createGraphics();
        g2d.drawImage(OImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
        return RImage;
    }

    public void draw(Graphics2D g) {
        g.drawImage(RImage, x, y, null);
    }

    //deals damage to player
    public void dealDamage(Player person) {
        int damage = damageOnHit;
        long currentTime = System.currentTimeMillis();

        //if the current time is divisible by 1000/dps, do damage
        //this is because milliseconds mod 1000 will be true once a second
        //boolean timeToHit = (currentTime%(1000/dps)<10);
        boolean timeToHit = true;
        if (timeToHit) {
            person.changeHealth(-damageOnHit);
        }
    }
    
    //deals damage to mob
    public void dealDamage(Mob mob) {
        int damage = damageOnHit;
        long currentTime = System.currentTimeMillis();

        //if the current time is divisible by 1000/dps, do damage
        //this is because milliseconds mod 1000 will be true once a second
        //boolean timeToHit = (currentTime%(1000/dps)<10);
        boolean timeToHit = true;
        if (timeToHit) {
            mob.changeHealth(-damageOnHit);
        }
    }

    public void destroy() {
        this.destroyed = true;
    }
    
    //Update a mobs projectile 
    public void update(Player person){
        this.move((int)dir[0], (int)dir[1]);
        //If we've hit the player
        if (isCollided(person)){
            dealDamage(person);
            this.destroy();
        } 
        //If we've hit a wall
        if(wall[0] || wall[1] || wall[2] || wall[3]){
            this.destroy();
        }
    }
    
    //Update a player projectile
    public void update(){
        //Move the projectile
        this.move((int)dir[0], (int)dir[1]);
        //Destroy self if hits a wall
        if(wall[0] || wall[1] || wall[2] || wall[3]){
            this.destroy();
        }
    }

    

    

  


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
