/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reapersrage.res.sound;
import sun.audio.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Chris
 */
public class Sound {
    public void music(String sound){
        AudioStream newAudio;
        AudioData audioFile;
        AudioPlayer playAudio = AudioPlayer.player;
        ContinuousAudioDataStream loop=null;
        try {
            
            newAudio = new AudioStream(new FileInputStream("test.wav"));
            audioFile = newAudio.getData();
            loop = new ContinuousAudioDataStream(audioFile);
            playAudio.start(loop);
        } catch (IOException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
