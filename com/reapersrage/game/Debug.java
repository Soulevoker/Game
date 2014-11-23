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
import java.util.Iterator;
/**
 *
 * @author Chris
 */
public class Debug {
    private JFrame debugPanel;
    private JLabel label1;
    private JLabel label2;
    private ArrayList<JLabel> LabelList;
    
     //Initialize the debug panel
    public Debug(){
            GridLayout experimentLayout = new GridLayout(0,3);
            this.LabelList = new ArrayList<JLabel>();
            debugPanel = new JFrame("Debug Panel");
            debugPanel.setPreferredSize(new Dimension(200*4,400));
            debugPanel.setResizable(false);
            debugPanel.pack();
            debugPanel.setVisible(true);
            debugPanel.setAlwaysOnTop(true);
            debugPanel.setLocation(900,100);
            debugPanel.setLayout(experimentLayout);
            
            
            LabelList.add(new JLabel("1"));
            LabelList.add(new JLabel("2"));
            LabelList.add(new JLabel("3"));
            LabelList.add(new JLabel("4"));
            
            Iterator<JLabel> labelIterator = LabelList.iterator();
            while(labelIterator.hasNext()){
                debugPanel.add(labelIterator.next());
            }
            
            
            
            
            
    }
    public void setLabel(int ListLoc, String labelNew ){
        //JLabel labelTemp = new JLabel(labelNew);
        LabelList.get(ListLoc).setText(labelNew);
    }
    public void addLabel(int ListLoc, String LabelAdd){
        String out = LabelList.get(ListLoc).getText()+ LabelAdd;
        LabelList.get(ListLoc).setText(out);
    }
        
        
}
