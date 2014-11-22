package com.reapersrage.entities.mobs;

import com.reapersrage.entities.projectiles.FireBall;
import com.reapersrage.entities.projectiles.Projectile;
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
    Random rand = new Random();
    
    //List to store the ghost's projectiles
    private ArrayList<Projectile> ProjList = new ArrayList<>();
    
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
        if(rand.nextInt(200) % 50 == 0) fireball(person);
        Iterator<Projectile> projIterator = ProjList.iterator();
        while(projIterator.hasNext()){
            Projectile currProj = projIterator.next();
            currProj.update(person);
            if(currProj.destroyed){
                projIterator.remove();
            }
        }
        
    }
    
    public void drawMob(Graphics2D g) {
            g.drawImage(RImage, x, y, null);
            Iterator<Projectile> projIterator = ProjList.iterator();
            while(projIterator.hasNext()){
                Projectile currProj = projIterator.next();
                currProj.drawProj(g);
            }
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
    //Displacement from player, scaled by mag
    public double[] displacementFromPlayer(Player player, double magnitude){
        double[] disp = new double[2];
        disp[0] = player.getX() - this.x;
        disp[1] = player.getY() - this.y;
        double mag = Math.sqrt(disp[0]*disp[0] + disp[1]*disp[1]);
        disp[0] = disp[0]/mag*magnitude;
        disp[1] = disp[1]/mag*magnitude;
        return disp;
    }
    
    //Shoots a fireball in the specified direction
    private void fireball(Player player){
        ProjList.add(new FireBall(this.x, this.y, 20, 20, 100, displacementFromPlayer(player, 10)));
    }
    
    
    
}
