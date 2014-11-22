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
    
    public void update(Player person){
        if (isCollided(person)) dealDamage(person); 
        this.move(rand.nextInt(10)-5,rand.nextInt(10)-5);
        
    }
    
}
