package com.peterpl.labyrinth.player;

import com.peterpl.labyrinth.graphics.*;
import com.peterpl.labyrinth.level.*;

public class Player {
    public int x, y;

    public int texX = 0, texY = 0;
    public final Texture texture = Texture.PLAYER;
    private Level level;

    public boolean moving = false;
    private int movingTime = 0;

    public static final int SIZE = 16;
    public static final int MOVE_SPEED = 1;

    public Player(Level level) {
        this(0, 0, level);
    }

    public Player(int x, int y, Level level) {
        this.x = x;
        this.y = y;
        this.level = level;
    }

    public void update() {
        if(moving) {
            final int FRAME_TIME = 12;
            final int FRAMES = 4;

            int mod = movingTime % (FRAMES * FRAME_TIME);

            if(mod <= FRAME_TIME) {
                texY = 0;
            } else if(mod <= FRAME_TIME * 2) {
                texY = 1;
            } else if(mod <= FRAME_TIME * 3) {
                texY = 0;
            } else {
                texY = 2;
            }
            movingTime++;
        } else {
            texY = 0;
            movingTime = 0;
        }
    }

    public void move(int x, int y) {
        int newX = this.x + x * MOVE_SPEED;
        int newY = this.y + y * MOVE_SPEED;

        if(x > 0) texX = 0;
        if(x < 0) texX = 1;
        if(y > 0) texX = 2;
        if(y < 0) texX = 3;

        int newTileX = newX;
        int newTileY = newY;

        if(x > 0) newTileX += Player.SIZE - 1;
        if(y > 0) newTileY += Player.SIZE - 1;

        if(newTileX < 0 || newTileY < 0) {
            return;
        }

        Tile tile = level.getTile(newTileX, newTileY);
        if(tile != null && !tile.solid) {
            this.x = newX;
            this.y = newY;
        }
    }
}
