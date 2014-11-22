/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reapersrage.game;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Chris
 */
public class Debug {
    private JFrame debugPanel;
    private JLabel label1;
     //Initialize the debug panel
    public Debug(){
            debugPanel = new JFrame("Debug Panel");
            debugPanel.setPreferredSize(new Dimension(500,500));
            debugPanel.setResizable(false);
            debugPanel.pack();
            debugPanel.setVisible(true);
            debugPanel.setAlwaysOnTop(true);
            //String collisionStr = "Health: "+player.getHealth()+" PlayerPos: ("+ player.getX()+","+player.getY()+") SpikePos: ("+spikeMob.getX()+","+spikeMob.getY()+")" + " Collision: "+spikeMob.isCollided(player);
            label1 = new JLabel("hello");
            debugPanel.add(label1);
    }
    public void setLabel1(String labelNew){
        label1.setText(labelNew);
    }
        
        
}
