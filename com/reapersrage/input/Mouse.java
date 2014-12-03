package com.reapersrage.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.reapersrage.game.Game;

public class Mouse extends MouseAdapter {
    
    public static int ClickedX;
    public static int ClickedY;
    public static boolean button1_isClicked;
	
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            setClickedX(e.getX());
            setClickedY(e.getY());
            setButton1_isClicked(true);
        }
        super.mouseClicked(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        super.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        super.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        super.mouseReleased(e);
    }

    public static int getClickedX() {
        return ClickedX;
    }

    public static void setClickedX(int clickedX) {
        ClickedX = clickedX;
    }

    public static int getClickedY() {
        return ClickedY;
    }

    public static void setClickedY(int clickedY) {
        ClickedY = clickedY;
    }

    public static boolean isButton1_isClicked() {
        return button1_isClicked;
    }

    public static void setButton1_isClicked(boolean n) {
        button1_isClicked = n;
    }

}
