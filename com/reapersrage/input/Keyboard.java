package com.reapersrage.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.reapersrage.game.Game;
import com.reapersrage.gfx.GameOverScreen;

/**
 * Created with IntelliJ IDEA. User: Soulevoker Date: 10/22/13 Time: 10:00 PM
 * Copyright © Reapers' Rage 2013
 */
public class Keyboard extends KeyAdapter {


    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            Buttons.setButtonPressed("down");
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            Buttons.setButtonPressed("up");
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            Buttons.setButtonPressed("left");
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            Buttons.setButtonPressed("right");
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Buttons.setButtonPressed("space");
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            Buttons.setButtonPressed("projUp");
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            Buttons.setButtonPressed("projLeft");
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            Buttons.setButtonPressed("projDown");
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            Buttons.setButtonPressed("projRight");
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            Buttons.setButtonReleased("down");
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            Buttons.setButtonReleased("up");
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            Buttons.setButtonReleased("left");
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            Buttons.setButtonReleased("right");
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Buttons.setButtonReleased("space");
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            Buttons.setButtonReleased("projUp");
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            Buttons.setButtonReleased("projLeft");
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            Buttons.setButtonReleased("projDown");
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            Buttons.setButtonReleased("projRight");
        }
    }

    public void keyTyped(KeyEvent e) {

    }

}
