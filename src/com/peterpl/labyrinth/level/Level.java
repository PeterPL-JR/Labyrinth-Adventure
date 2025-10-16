package com.peterpl.labyrinth.level;

public class Level {
    public final int width, height;
    public final Tile[][] tiles;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;

        tiles = new Tile[width][height];
        for(int x = 0; x < width; x++) {
            tiles[x] = new Tile[height];
        }
    }
}
