package com.reapersrage.things;

import com.reapersrage.gfx.Screen;
import com.reapersrage.gfx.Sprite;
import com.reapersrage.world.level.Level;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Soulevoker
 * Date: 10/27/13
 * Time: 2:37 AM
 * Copyright © Reapers' Rage 2013
 */
public class Thing {
    protected int x;
    protected int y;
    protected int dir;
    protected String title;
    protected boolean removed = false;
    private Sprite sprite;
    private Level level;
    private Random random;
    
    public Thing() {
    	this.x=0;
    	this.y=0;
        this.dir=0;
        this.title="";
    	this.removed=false;	//default true or false?
    	this.level=null;
    	this.sprite=null;
    }
    
    public Thing(int x, int y) {
    	this.x=x;
    	this.y=y;
        this.dir=0;
        this.title="";
    	this.removed=false;	//default true or false?
    	this.level=null;
    	this.sprite=null;
    }
    
    public Thing(int x, int y, boolean removed, Level level, Sprite sprite, int dir, String title) {
    	setThing(x,y,removed,level, sprite, dir, title);
    }
    
    public void setThing(int x, int y, boolean removed, Level level, Sprite sprite, int dir, String title) {
    	this.x=x;
    	this.y=y;
    	this.removed=removed;
    	this.level=level;
    	this.sprite=sprite;
    	this.dir=dir;
    	this.title=title;	//generic name (ex. wall, knight, ghost, bot)
    }

    public void update() {

    }

    public void render(Screen screen) {

    }
    
    public void move(double xa, double ya) {
        if (xa > 0) {
            dir = 2;
        }
        if (xa < 0) {
            dir = 3;
        }
        if (ya > 0) {
            dir = 1;
        }
        if (ya < 0) {
            dir = 0;
        }

        if (!collision()) {
            x += xa;
            y += ya;
        }
    }
    
    //move this method to AllThings
    private boolean collision() {
        return false;
    }

    /**
     * Remove Thing from Level
     */
    public void remove() {
        removed = true;	//called from removeAt(curr,prev) in ThingBlock?
    }
    
    public void restore() {
    	removed = false;
    }

    public boolean isRemoved() {
        return removed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getDir() {
        return dir;
    }
    
    public int compareX(Thing other) {
    	return this.x-other.getX();
    }
    
    public boolean equals(Thing other) {
    	return (this.x==other.x&&this.y==other.y&&this.dir==other.dir&&this.title.equals(other.title)
    			&&this.removed==other.removed&&this.level==other.level&&this.sprite==other.sprite);
    }
}
