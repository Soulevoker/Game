/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reapersrage.game;

/**
 * Static class for doing math on vectors
 *
 * @author David
 */
public final class VectorMath {

    //Scales a vector to a specified length

    public static double[] scaleVector(double[] vec, double scale) {
        double currMag = vec[0] * vec[0] + vec[1] * vec[1];
        vec[0] = vec[0] / currMag * scale;
        vec[1] = vec[1] / currMag * scale;
        return vec;
    }
}
