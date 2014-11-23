package com.reapersrage.entities.mobs;

import com.reapersrage.entities.projectiles.FireBall;
import com.reapersrage.entities.projectiles.Projectile;
import com.reapersrage.game.VectorMath;
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
    public static final int defWidth = 80;
    public static final int defHeight = 80;
    public static final int defDamage = 10;
    public static final int defDPS = 1;
    public static int ID = 0;
    //Number of ghosts summoned
    public static int NUM = 0;
    
    //List to store the ghost's projectiles
    private ArrayList<Projectile> ProjList = new ArrayList<>();
    
    //contrcutor for a ghost mob 
    public Ghost(int x, int y, int width, int height, int damageOnHit, int dps, int id) {
        super(x, y, width, height, damageOnHit, dps, "ghost");
        this.id = id;
        this.imortalObject=false;
        Ghost.NUM++;
        
    }
    
    public Ghost(int x, int y, int width, int height, int damageOnHit, int dps) {
        super(x, y, width, height, damageOnHit, dps, "ghost");
        this.id = 0;
        this.imortalObject=false;
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
    
    //Move the ghost in the direction of the player
    public void update(Player person){
        if (isCollided(person)) dealDamage(person); 
        double[] disp = displacementFromPlayer(person);
        this.move((int)(5*disp[0]),(int)(5*disp[1]));
        if(rand.nextInt(200) % 50 == 0) fireball(person);
        updateProjectiles(person);        
    }
    
    private void updateProjectiles(Player person){
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
        ProjList.add(new FireBall(this.x, this.y, 40, 40, 100, displacementFromPlayer(player, 10)));
    }
    public String projDebug() {
        String out = "";
        Iterator<Projectile> projIterator = ProjList.iterator();
        int i = 0;
            while(projIterator.hasNext()){
                Projectile currentProj = projIterator.next();
                out = out + "Proj "+i+":("+currentProj.getX()+","+currentProj.getY()+")";
            }
        return out;
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
