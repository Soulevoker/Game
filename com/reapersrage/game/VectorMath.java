/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reapersrage.game;
import java.util.Random;
/**
 * Static class for doing math on vectors
 *
 * @author David
 */
public final class VectorMath {
    private static Random rand = new Random();
    //Scales a vector to a specified length

    public static double[] scaleVector(double[] vec, double scale) {
        double currMag = Math.sqrt(vec[0] * vec[0] + vec[1] * vec[1]);
        vec[0] = vec[0] / currMag * scale;
        vec[1] = vec[1] / currMag * scale;
        return vec;
    }
    
    public static double[] scaleVector(int[] vec, double scale) {
        double currMag = Math.sqrt(vec[0] * vec[0] + vec[1] * vec[1]);
        double[] newVec = new double[2];
        newVec[0] = (vec[0] / currMag * scale);
        newVec[1] = (vec[1] / currMag * scale);
        return newVec;
    }
    
    public static int[] randomPos(int width, int height){
        int[] pos = new int[2];
        pos[0] = rand.nextInt(Game.getStaticWidth()-width);
        pos[1] = rand.nextInt(Game.getStaticHeight()-height);
        return pos;
    }
    
    public static int[] randomPos(){
        int[] pos = new int[2];
        pos[0] = rand.nextInt(Game.getStaticWidth());
        pos[1] = rand.nextInt(Game.getStaticHeight());
        return pos;
    }
}
