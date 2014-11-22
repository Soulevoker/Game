/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reapersrage.game;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
/**
 *
 * @author Chris
 */
public class Debug {
    private JFrame debugPanel;
    private JLabel label1;
    private JLabel label2;
    private JLabel[] LabelList;
     //Initialize the debug panel
    public Debug(){
            GridLayout experimentLayout = new GridLayout(0,2);
            this.LabelList = new JLabel[2];
            debugPanel = new JFrame("Debug Panel");
            debugPanel.setPreferredSize(new Dimension(450,100));
            debugPanel.setResizable(false);
            debugPanel.pack();
            debugPanel.setVisible(true);
            debugPanel.setAlwaysOnTop(true);
            debugPanel.setLocation(900,100);
            debugPanel.setLayout(experimentLayout);
            //String collisionStr = "Health: "+player.getHealth()+" PlayerPos: ("+ player.getX()+","+player.getY()+") SpikePos: ("+spikeMob.getX()+","+spikeMob.getY()+")" + " Collision: "+spikeMob.isCollided(player);
            label1 = new JLabel("hello");
            debugPanel.add(label1);
            label2 = new JLabel("hello again");
            debugPanel.add(label2);
            
            LabelList[0] = label1;
            LabelList[1] = label2;
            
            
            
    }
    public void setLabel(int ListLoc, String labelNew ){
        //JLabel labelTemp = new JLabel(labelNew);
        LabelList[ListLoc].setText(labelNew);
    }
        
        
}
