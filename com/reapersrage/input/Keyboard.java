package com.reapersrage.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.reapersrage.game.Game;

/**
 * Created with IntelliJ IDEA. User: Soulevoker Date: 10/22/13 Time: 10:00 PM
 * Copyright © Reapers' Rage 2013
 */
public class Keyboard extends KeyAdapter {


	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN
				|| e.getKeyCode() == KeyEvent.VK_S) {
			Game.setButtonPressed("down");
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			Game.setButtonPressed("up");
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_A) {
			Game.setButtonPressed("left");
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_D) {
			Game.setButtonPressed("right");
		}
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                        Game.setButtonPressed("space");
                }
                
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN
				|| e.getKeyCode() == KeyEvent.VK_S) {
			Game.setButtonReleased("down");
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			Game.setButtonReleased("up");
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_A) {
			Game.setButtonReleased("left");
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_D) {
			Game.setButtonReleased("right");
		}
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                        Game.setButtonReleased("space");
                }
	}

	public void keyTyped(KeyEvent e) {

	}


}
