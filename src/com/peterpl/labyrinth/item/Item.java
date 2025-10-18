package com.peterpl.labyrinth.item;

import com.peterpl.labyrinth.graphics.*;

public abstract class Item {
    protected Texture texture;

    public Item(String name) {
        this.texture = new Texture("item/" + name);
    }

    public abstract void update();

    public Texture getTexture() {
        return texture;
    }
}
