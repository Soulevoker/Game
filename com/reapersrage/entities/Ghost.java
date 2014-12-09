package com.reapersrage.entities;

import com.reapersrage.game.Game;
import com.reapersrage.game.VectorMath;
import com.reapersrage.world.level.RandomLevel;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
/**
 *
 * @author David
 */
public class Ghost extends Mob {
    int id;
    //For movement and damage
    public static final int defWidth = 80;
    public static final int defHeight = 80;
    public static final int defDamage = 10;
    public static final int defDPS = 1;
    public static int ID = 0;
    //Number of ghosts summoned
    public static int NUM = 0;
    //Current direction 
    public static int[] direction;

    
    
    //List to store the ghost's projectiles
    //private ArrayList<Projectile> ProjList = new ArrayList<>();
    
    //contrcutor for a ghost mob 
    public Ghost(int x, int y, int width, int height, int damageOnHit, int dps, int id) {
        super(x, y, width, height, damageOnHit, dps, "ghost");
        this.id = id;
        this.imortalObject=false;
        this.direction = VectorMath.randomPos();
        Ghost.NUM++;
    }
    
    public Ghost(int x, int y, int width, int height, int damageOnHit, int dps) {
        super(x, y, width, height, damageOnHit, dps, "ghost");
        this.id = 0;
        this.imortalObject=false;
        this.direction = VectorMath.randomPos();
        Ghost.NUM++;
    }
    
    public Ghost(int[] pos){
        this(pos[0], pos[1], Ghost.defWidth, Ghost.defHeight, Ghost.defDamage, Ghost.defDPS, Ghost.ID++);
    }
    
    //Random positon
    public Ghost(){
        this(VectorMath.randomPos(Ghost.defWidth, Ghost.defHeight));
    }
    
    public String getName() {
        return "Ghost " + id;
    }
    
    /**
     * Updates the ghost
     * <p>
     * Algorithm:
     *      first the ghost decides if it will change destination
     *       (default destination is what?)
     *      move in that direction at a speed of 5 pixels/frame
     *      randomly change direction ever 1000 frames (20 sec)
     *       or change direction if we've hit something
     *      fire a attack at the player every 200 frames (4 sec)
     * <p>
     * TODO:
     *      fix issue where for some reason ghosts just flock to the walls and
     *       do nothing
     * 
     * @param person - the player
     */
    public void update(Player person){
        if (isCollided(person)) dealDamage(person); 
        //double[] disp = displacementFromPlayer(person);
        double[] disp = VectorMath.scaleVector(displacementFrom(this.direction), 1);
        boolean collided = this.move((int)(5*disp[0]),(int)(5*disp[1]));
        //if we hit a wall or it's time to change direction
        if(rand.nextInt(1000) == 1 ||  collided)
            setDirection();
        if(rand.nextInt(200) == 10) fireball(person);
        projCollision(person);
    }
    
    /**
     * Sets the direction of movement
     */
    private void setDirection(){
        this.direction = VectorMath.randomPos();
    }
    
    
    
    
    /**
     * Shoots a singe fireball at player
     * @param player the player
     */
    private void fireball(Player player){
        Game.getLevel().addEntity(new FireBall(this.x, this.y, 40, 40, 100, displacementFromPlayer(player, 10)));
    }

    
    public void destroy(){
        dropGold(1);
        destroyed = true;
        Ghost.NUM--;
    }
    
    
    public boolean isDestroyed() {
        if(health <= 0) destroy();
        
        return destroyed; 
    }
  
    
    
    
}
