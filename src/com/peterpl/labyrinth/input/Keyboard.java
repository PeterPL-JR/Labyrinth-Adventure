package com.peterpl.labyrinth.input;

import java.awt.event.*;

public class Keyboard implements KeyListener {
    private boolean[] keys = new boolean[65536];
    public boolean left, right, up, down;

    public void update() {
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
