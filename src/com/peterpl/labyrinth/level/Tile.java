package com.peterpl.labyrinth.level;

import com.peterpl.labyrinth.graphics.*;

public class Tile {
    public static final int SIZE = 16;

    public static final Tile WALL = new Tile(new Texture("tile/wall"));
    public static final Tile HEDGE = new Tile(new Texture("tile/hedge"));
    public static final Tile FLOOR = new Tile(new Texture("tile/floor"));
    public static final Tile GRASS = new Tile(new Texture("tile/grass"));

    protected Texture texture;

    public Tile(Texture texture) {
        this.texture = texture;
    }

    public void update() {
    }

    public Texture getTexture() {
        return texture;
    }
}
