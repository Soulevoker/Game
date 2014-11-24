package com.reapersrage.things;

import com.reapersrage.things.Thing;
import com.reapersrage.things.Player;
import com.reapersrage.gfx.Screen;
import com.reapersrage.gfx.Sprite;
import com.reapersrage.input.Keyboard;
import com.reapersrage.world.level.Level;

public class AllThings {
	ThingBlock<Thing> allThings;
	ThingBlock<Player> allPlayers;
	
	public AllThings(){
		allThings=new ThingBlock<Thing>();
		allPlayers=new ThingBlock<Player>();
	}
	
	public AllThings(int maxThings, int maxPlayers){
		allThings=new ThingBlock<Thing>(maxThings);
		allPlayers=new ThingBlock<Player>(maxPlayers);
	}
	
	public int createDefaultThing() {
		return addThing(new Thing());
	}
	
	public int createThing(int x, int y, boolean removed, Level level, Sprite sprite, int dir, String title) {
		return addThing(new Thing(x,y,removed,level, sprite, dir, title));
	}
	
	public int createDefaultPlayer(Keyboard input) {
		return addPlayer(new Player(input));
	}
	
	public void update() {
		allThings.update();
		allPlayers.update();
	}
	
	public int addThing(Thing obj) {
		return allThings.add(obj);
	}
	
	public int removeThing(Thing obj) {
		return allThings.remove(obj);
	}
	
	public int addPlayer(Player obj) {
		return allPlayers.add(obj);
	}
	
	public int removePlayer(Player obj) {
		return allPlayers.remove(obj);
	}
	
	public Thing getThingAt(int index) {
		return allThings.getThingAt(index);
	}
	
	public Player getPlayerAt(int index) {
		return allPlayers.getThingAt(index);
	}
	
	public void render(Screen screen) {
		allPlayers.getThingAt(0).render(screen);
	}
}
