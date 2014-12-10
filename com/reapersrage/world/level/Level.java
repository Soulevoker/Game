package com.reapersrage.world.level;

import com.reapersrage.entities.Entity;
import com.reapersrage.entities.Item;
import com.reapersrage.entities.Mob;
import com.reapersrage.entities.Player;
import com.reapersrage.entities.Projectile;
import com.reapersrage.gfx.Screen;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA. User: Soulevoker Date: 10/24/13 Time: 5:51 PM
 * Copyright © Reapers' Rage 2013
 */
public abstract class Level {

    protected int mapwidth, mapheight;
    protected int[][] tiles;
    //protected ArrayList<Mob> MobList = new ArrayList<>();
    //protected ArrayList<Item> ItemList = new ArrayList<>();
    //protected ArrayList<Projectile> ProjList = new ArrayList<>();
    protected ArrayList<Entity> EntityList = new ArrayList<>();
    protected Queue<Entity> EntityQueue = new LinkedList<>();

    public Level(int mapwidth, int mapheight) {
        this.mapwidth = mapwidth;
        this.mapheight = mapheight;
        tiles = new int[this.mapheight][this.mapwidth];

    }

    public int getTile(int y, int x) {
        return tiles[y][x];
    }

    public abstract void update(Player player);

    public void updateEntities(Player player) {
        Iterator<Entity> entityIterator = EntityList.iterator();
        while (entityIterator.hasNext()) {
            Entity currEntity = entityIterator.next();
            currEntity.update(player);
            if (currEntity.isDestroyed()) {
                entityIterator.remove();
            }

        }
        while (!EntityQueue.isEmpty()) {
            EntityList.add(EntityQueue.poll());
        }
    }

    public void drawEntities(Graphics2D g) {
        Iterator<Entity> entityIterator = EntityList.iterator();
        while (entityIterator.hasNext()) {
            Entity currEntity = entityIterator.next();
            currEntity.draw(g);
        }

    }

    public void addEntity(Entity entity) {
        EntityQueue.add(entity);
    }

}
