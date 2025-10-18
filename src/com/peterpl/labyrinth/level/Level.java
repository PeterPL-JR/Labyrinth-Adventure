package com.peterpl.labyrinth.level;

import com.peterpl.labyrinth.maze.*;

public class Level {
    public final int width, height;
    public final Tile[][] tiles;

    private MazeGenerator maze;

    public Level(int mazeWidth, int mazeHeight, Tile wallTile, Tile floorTile) {
        maze = new MazeGenerator(mazeWidth, mazeHeight);

        width = maze.arrayWidth;
        height = maze.arrayHeight;

        tiles = new Tile[width][height];
        for(int x = 0; x < width; x++) {
            tiles[x] = new Tile[height];
            for(int y = 0; y < height; y++) {
                int tile = maze.array[x][y];
                tiles[x][y] = tile == MazeGenerator.WALL ? wallTile : floorTile;
            }
        }
    }

    public Tile getTile(int x, int y) {
        int tileX = x / Tile.SIZE;
        int tileY = y / Tile.SIZE;

        if(tileX >= 0 && tileY >= 0 && tileX < width && tileY < height) {
            return tiles[tileX][tileY];
        }
        return null;
    }
}
