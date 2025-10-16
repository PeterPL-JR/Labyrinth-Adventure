package com.peterpl.labyrinth.graphics;

import com.peterpl.labyrinth.level.*;
import com.peterpl.labyrinth.player.*;
import com.peterpl.labyrinth.utils.*;

import java.util.*;

public class Render extends Pixelated {
    private Level level;
    private Player player;

    public Render(int width, int height, Level level, Player player) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        this.level = level;
        this.player = player;
    }

    public void clear() {
        Arrays.fill(pixels, 0);
    }

    public void render() {
        renderLevel(level, 0, 0);
        renderTexture(player.texture, player.x, player.y, player.texX * Player.SIZE, player.texY * Player.SIZE, Player.SIZE, Player.SIZE);
    }

    public void renderTexture(Texture tex, int xPos, int yPos, int texX, int texY, int width, int height) {
        for(int x = 0; x < width; x++) {
            int xx = x + xPos;
            int tx = x + texX;
            if(!isCorrectX(xx)) continue;
            for(int y = 0; y < height; y++) {
                int yy = y + yPos;
                int ty = y + texY;
                if(!isCorrectY(yy)) continue;

                int colour = tex.getPixel(tx, ty);
                if(colour != 0x00000000) {
                    setPixel(xx, yy, colour);
                }
            }
        }
    }

    public void renderTexture(Texture tex, int xPos, int yPos) {
        renderTexture(tex, xPos, yPos, 0, 0, tex.getWidth(), tex.getHeight());
    }

    public void renderLevel(Level level, int xOffset, int yOffset) {
        for(int x = 0; x < level.width; x++) {
            for(int y = 0; y < level.height; y++) {
                Tile tile = level.tiles[x][y];
                if(tile != null) {
                    renderTile(tile, x, y, xOffset, yOffset);
                }
            }
        }
    }

    public void renderTile(Tile tile, int x, int y, int xOffset, int yOffset) {
        renderTexture(tile.getTexture(), x * Tile.SIZE + xOffset, y * Tile.SIZE + yOffset);
    }
}
