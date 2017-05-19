package com.mock.input;

/**
 * Handles returning key input by simple method calls.
 * @author Trever Mock
 */
public class GameKeys {
    
    private static boolean[] keys;
    private static boolean[] pkeys;
    
    private static final int NUM_KEYS = 5;
    
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int SPACE = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    
    static {
        keys = new boolean[NUM_KEYS];
        pkeys = new boolean[NUM_KEYS];
    }
    
    public static void update() {
        for (int i = 0; i < NUM_KEYS; i++) {
            pkeys[i] = keys[i];
        }
    }
    
    public static void setKey(int keycode, boolean b) {
        keys[keycode] = b;
    }
    
    public static boolean isDown(int keycode) {
        return keys[keycode];
    }
    
    public static boolean isUp(int keycode) {
        return !keys[keycode];
    }
    
    public static boolean isPressed(int keycode) {
        return keys[keycode] && !pkeys[keycode];
    }
}
