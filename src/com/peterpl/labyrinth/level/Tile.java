package com.peterpl.labyrinth.level;

import com.peterpl.labyrinth.graphics.*;

public abstract class Tile {
    public static final FloorTile FLOOR = new FloorTile("floor");
    public static final FloorTile GRASS = new FloorTile("grass");

    public static final WallTile WALL = new WallTile("wall", Tile.FLOOR);
    public static final WallTile HEDGE = new WallTile("hedge", Tile.GRASS);

    protected Texture texture;
    public final boolean solid;

    public Tile(String name, boolean solid) {
        this.texture = new Texture("tile/" + name);
        this.solid = solid;
    }

    public abstract void update();

    public Texture getTexture() {
        return texture;
    }
}
