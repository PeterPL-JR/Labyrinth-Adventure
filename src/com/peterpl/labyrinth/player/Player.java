package com.peterpl.labyrinth.player;

import com.peterpl.labyrinth.graphics.*;

public class Player {
    public int x, y;

    public int texX = 0, texY = 0;
    public final Texture texture = Texture.PLAYER;

    public static final int SIZE = 16;

    public Player() {
        this(0, 0);
    }

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
