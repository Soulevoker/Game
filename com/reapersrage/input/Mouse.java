package com.reapersrage.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.reapersrage.game.Game;

public class Mouse extends MouseAdapter  {

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			Game.setClickedX(e.getX());
			Game.setClickedY(e.getY());
			Game.setButton1_isClicked(true);
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


	

	
}
