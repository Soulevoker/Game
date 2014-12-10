/*
 * To change Buttons license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reapersrage.input;

/**
 * A structure for storing the buttons currently pressed by the player
 *
 * @author David
 */
public final class Buttons {

    //these can be held down
    public static boolean up;
    public static boolean down;
    public static boolean left;
    public static boolean right;
    public static boolean space;
    public static boolean projUp;
    public static boolean projDown;
    public static boolean projLeft;
    public static boolean projRight;
    public static boolean blast;

    public static void setButtonPressed(String b) {
        //buttonPressed = b;
        if (b.equals("up")) {
            Buttons.up = true;
        }
        if (b.equals("down")) {
            Buttons.down = true;
        }
        if (b.equals("left")) {
            Buttons.left = true;
        }
        if (b.equals("right")) {
            Buttons.right = true;
        }
        if (b.equals("space")) {
            Buttons.space = true;
        }
        if (b.equals("projUp")) {
            Buttons.projUp = true;
        }
        if (b.equals("projDown")) {
            Buttons.projDown = true;
        }
        if (b.equals("projLeft")) {
            Buttons.projLeft = true;
        }
        if (b.equals("projRight")) {
            Buttons.projRight = true;
        }
        if (b.equals("Blast")){
            Buttons.blast = true;
        }
    }

    public static void setButtonReleased(String b) {
        if (b.equals("up")) {
            Buttons.up = false;
        }
        if (b.equals("down")) {
            Buttons.down = false;
        }
        if (b.equals("left")) {
            Buttons.left = false;
        }
        if (b.equals("right")) {
            Buttons.right = false;
        }
        if (b.equals("space")) {
            Buttons.space = false;
        }
        if (b.equals("projUp")) {
            Buttons.projUp = false;
        }
        if (b.equals("projDown")) {
            Buttons.projDown = false;
        }
        if (b.equals("projLeft")) {
            Buttons.projLeft = false;
        }
        if (b.equals("projRight")) {
            Buttons.projRight = false;
        }
        if (b.equals("Blast")){
            Buttons.blast = false;
        }
    }
}
