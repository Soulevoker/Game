package com.reapersrage.entities.mobs;

import com.reapersrage.entities.projectiles.FireBall;
import com.reapersrage.entities.projectiles.Projectile;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import com.reapersrage.world.level.RandomLevel;
/**
 *
 * @author David
 */
public class Ghost extends Mob {
    int id;
    //For movement and damage
    Random rand = new Random();
    
    //List to store the ghost's projectiles
    private ArrayList<Projectile> ProjList = new ArrayList<>();
    
    //contrcutor for a ghost mob 
    public Ghost(int x, int y, int width, int height, int damageOnHit, int dps, int id) {
        super(x, y, width, height, damageOnHit, dps, "ghost");
        this.id = id;
        this.imortalObject=false;
        
    }
    
    public Ghost(int x, int y, int width, int height, int damageOnHit, int dps) {
        super(x, y, width, height, damageOnHit, dps, "ghost");
        this.id = 0;
        this.imortalObject=false;
    }
    
    public String getName() {
        return "Ghost " + id;
    }
    
    //Move the ghost in the direction of the player
    public void update(Player person){
        if (isCollided(person)) dealDamage(person); 
        double[] disp = displacementFromPlayer(person);
        this.move((int)(5*disp[0]),(int)(5*disp[1]));
        if(rand.nextInt(200) % 50 == 0) fireball(person);
        Iterator<Projectile> projIterator = ProjList.iterator();
        while(projIterator.hasNext()){
            Projectile currProj = projIterator.next();
            currProj.update(person);
            if(currProj.destroyed){
                projIterator.remove();
            }
        }
        projCollision(person);
        
    }
    
    public void drawMob(Graphics2D g) {
            g.drawImage(RImage, x, y, null);
            Iterator<Projectile> projIterator = ProjList.iterator();
            while(projIterator.hasNext()){
                Projectile currProj = projIterator.next();
                currProj.drawProj(g);
            }
	}
    
    
    
    //Shoots a fireball in the specified direction
    private void fireball(Player player){
        ProjList.add(new FireBall(this.x, this.y, 20, 20, 100, displacementFromPlayer(player, 10)));
    }
    public String projDebug() {
        String out = "";
        Iterator<Projectile> projIterator = ProjList.iterator();
        int i =0;
            while(projIterator.hasNext()){
                Projectile currentProj = projIterator.next();
                out = out + "Proj "+i+":("+currentProj.getX()+","+currentProj.getY()+")";
            }
        return out;
    }
    
  
    
    
    
}
