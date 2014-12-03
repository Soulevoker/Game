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

    /**
     * Scales a vector
     *
     * @param vec - vector to scale
     * @param scale - length to scale to (1 for unit vector)
     * @return scaled vector
     */
    public static double[] scaleVector(double[] vec, double scale) {
        double currMag = Math.sqrt(vec[0] * vec[0] + vec[1] * vec[1]);
        vec[0] = vec[0] / currMag * scale;
        vec[1] = vec[1] / currMag * scale;
        return vec;
    }

    /**
     * Scales an integer vector
     *
     * @param vec - vector
     * @param scale - new magnitude
     * @return scaled vector
     */
    public static double[] scaleVector(int[] vec, double scale) {
        double currMag = Math.sqrt(vec[0] * vec[0] + vec[1] * vec[1]);
        double[] newVec = new double[2];
        newVec[0] = (vec[0] / currMag * scale);
        newVec[1] = (vec[1] / currMag * scale);
        return newVec;
    }

    /**
     * Generates a random position on map, taking into account the size of the
     * object so it is rendered within the walls
     *
     * @param width - object width
     * @param height - object hight
     * @return a random position
     */
    public static int[] randomPos(int width, int height) {
        int[] pos = new int[2];
        pos[0] = rand.nextInt(Game.getStaticWidth() - width);
        pos[1] = rand.nextInt(Game.getStaticHeight() - height);
        return pos;
    }

    /**
     * Random position
     *
     * @return a random position on the map
     */
    public static int[] randomPos() {
        int[] pos = new int[2];
        pos[0] = rand.nextInt(Game.getStaticWidth());
        pos[1] = rand.nextInt(Game.getStaticHeight());
        return pos;
    }

    /**
     * Finds the arctangent of vec[1]/vec[0] taking in account quadrant
     *
     * @param vec - position vector
     * @return the angle in degrees, from the x axis
     */
    public static double atan(double[] vec) {
        int quad = 0;
        if (vec[0] >= 0 && vec[1] >= 0) {
            quad = 1;
        }
        else if (vec[0] <= 0 && vec[1] > 0) {
            quad = 2;
        }
        else if (vec[0] <= 0 && vec[1] <= 0) {
            quad = 3;
        }
        else {
            quad = 4;
        }
        System.out.println(quad);
        double angle = Math.atan(Math.abs(vec[1] / vec[0])) * (180.0 / Math.PI) + 90.0 * (quad - 1);
        return toCompass(angle);
    }

    /**
     * Converts math angle notation into "compass" notation, with 0 degrees
     * being North
     *
     * @param angle angle in math notation
     * @return angle in compass notation
     */
    public static double toCompass(double angle) {
        double retAng = (90.0 - angle) % 360.0;
        return retAng >= 0 ? retAng : retAng + 360.0;
    }
}
