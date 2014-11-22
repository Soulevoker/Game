package com.reapersrage.entities.mobs;

import java.util.Random;
/**
 *
 * @author David
 */
public class Ghost extends Mob {
    int id;
    //For movement and damage
    Random rand = new Random();
    //contrcutor for a ghost mob
    public Ghost(int x, int y, int width, int height, int damageOnHit, int dps, int id) {
        super(x, y, width, height, damageOnHit, dps, "ghost");
        this.id = id;
    }
    
    public Ghost(int x, int y, int width, int height, int damageOnHit, int dps) {
        super(x, y, width, height, damageOnHit, dps, "ghost");
        this.id = 0;
    }
    
    public String getName() {
        return "Ghost " + id;
    }
    
    //Move the ghost in the direction of the player
    public void update(Player person){
        if (isCollided(person)) dealDamage(person); 
        double[] disp = displacementFromPlayer(person);
        this.move((int)(5*disp[0]),(int)(5*disp[1]));
        
    }
    
    //The displacement of the ghost from the player (direction only)
    public double[] displacementFromPlayer(Player player){
        double[] disp = new double[2];
        disp[0] = player.getX() - this.x;
        disp[1] = player.getY() - this.y;
        double mag = Math.sqrt(disp[0]*disp[0] + disp[1]*disp[1]);
        disp[0] = disp[0]/mag;
        disp[1] = disp[1]/mag;
        return disp;
    }
    
    
    
}
