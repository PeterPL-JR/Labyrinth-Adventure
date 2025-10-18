package com.peterpl.labyrinth.level;

import com.peterpl.labyrinth.*;
import com.peterpl.labyrinth.item.*;
import com.peterpl.labyrinth.maze.*;
import com.peterpl.labyrinth.utils.*;

public class Level {
    public final int width, height;
    public final Tile[][] tiles;
    public final Item[][] items;

    public Level(int mazeWidth, int mazeHeight, Tile wallTile, Tile floorTile) {
        MazeGenerator maze = new MazeGenerator(mazeWidth, mazeHeight);

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

        items = new Item[width][height];
        for(int x = 0; x < width; x++) {
            items[x] = new Item[height];
        }

        init();
    }

    public void update() {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Tile tile = tiles[x][y];
                Item item = items[x][y];

                if(tile != null) {
                    tile.update();
                }
                if(item != null) {
                    item.update();
                }
            }
        }
    }

    public Tile getTile(int x, int y) {
        int tileX = x / LabyrinthAdventure.SIZE;
        int tileY = y / LabyrinthAdventure.SIZE;
        return getTileFromIndex(tileX, tileY);
    }

    public Tile getTileFromIndex(int x, int y) {
        if(x >= 0 && y >= 0 && x < width && y < height) {
            return tiles[x][y];
        }
        return null;
    }

    private void init() {
        final int DIAMONDS = 100;
        for(int i = 0; i < DIAMONDS; i++) {
            int x, y;
            x = Rand.number(1, width - 2);
            y = Rand.number(1, height - 2);

            if(!tiles[x][y].solid && items[x][y] == null) {
                items[x][y] = new Diamond();
            } else {
                i--;
            }
        }
    }
}
