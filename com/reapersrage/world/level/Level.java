package com.reapersrage.world.level;

import com.reapersrage.entities.Item;
import com.reapersrage.entities.Mob;
import com.reapersrage.entities.Player;
import com.reapersrage.entities.Projectile;
import com.reapersrage.gfx.Screen;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created with IntelliJ IDEA.
 * User: Soulevoker
 * Date: 10/24/13
 * Time: 5:51 PM
 * Copyright © Reapers' Rage 2013
 */
public abstract class Level {
    protected int mapwidth, mapheight;
    protected int[][] tiles;
    protected ArrayList<Mob> MobList = new ArrayList<>();
    protected ArrayList<Item> ItemList = new ArrayList<>();
    protected ArrayList<Projectile> ProjList = new ArrayList<>();

    public Level(int mapwidth, int mapheight) {
        this.mapwidth = mapwidth;
        this.mapheight = mapheight;
        tiles = new int[this.mapheight][this.mapwidth];
   
    }
    
    public int getTile(int y, int x){
    	return tiles[y][x];
    }
    
    public void renderMobs(Graphics2D g){
        Iterator<Mob> mobIterator = MobList.iterator();
        while(mobIterator.hasNext()){
            mobIterator.next().draw(g);
        }
        Iterator<Projectile> projIterator = ProjList.iterator();
        while(projIterator.hasNext()){
            Projectile currProj = projIterator.next();
            currProj.draw(g);
        }
    }
    
    public void renderItems(Graphics2D g){
        Iterator<Item> ItemIterator = ItemList.iterator();
        while(ItemIterator.hasNext()){
            ItemIterator.next().draw(g);
        }
    }
    
    public void addItem(Item item){
        ItemList.add(item);
    }
    
    public void addProjectile(Projectile proj){
        ProjList.add(proj);
    }
    
    public abstract void update(Player player);


    
}
