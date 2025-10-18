package com.peterpl.labyrinth.level;

import com.peterpl.labyrinth.graphics.*;

public class Tile {
    public static final int SIZE = 16;

    public static final Tile WALL = new Tile(new Texture("tile/wall"), true);
    public static final Tile HEDGE = new Tile(new Texture("tile/hedge"), true);
    public static final Tile FLOOR = new Tile(new Texture("tile/floor"), false);
    public static final Tile GRASS = new Tile(new Texture("tile/grass"), false);

    protected Texture texture;
    public final boolean solid;

    public Tile(Texture texture, boolean solid) {
        this.texture = texture;
        this.solid = solid;
    }

    public void update() {
    }

    public Texture getTexture() {
        return texture;
    }
}
