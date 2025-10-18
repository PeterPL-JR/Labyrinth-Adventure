package com.peterpl.labyrinth.graphics;

import com.peterpl.labyrinth.*;
import com.peterpl.labyrinth.item.*;
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
        int playerRenderX = (LabyrinthAdventure.WIDTH - LabyrinthAdventure.SIZE) / 2;
        int playerRenderY = (LabyrinthAdventure.HEIGHT - LabyrinthAdventure.SIZE) / 2;

        int xOffset = -player.x + playerRenderX;
        int yOffset = -player.y + playerRenderY;

        final int MAX_OFFSET_X = -level.width * LabyrinthAdventure.SIZE + LabyrinthAdventure.WIDTH;
        final int MAX_OFFSET_Y = -level.height * LabyrinthAdventure.SIZE + LabyrinthAdventure.HEIGHT;

        if(xOffset >= 0) {
            xOffset = 0;
            playerRenderX = player.x;
        } else if(xOffset < MAX_OFFSET_X) {
            xOffset = MAX_OFFSET_X;
            playerRenderX = player.x + MAX_OFFSET_X;
        }

        if(yOffset >= 0) {
            yOffset = 0;
            playerRenderY = player.y;
        } else if(yOffset < MAX_OFFSET_Y) {
            yOffset = MAX_OFFSET_Y;
            playerRenderY = player.y + MAX_OFFSET_Y;
        }

        renderLevel(level, xOffset, yOffset);
        renderTexture(player.texture, playerRenderX, playerRenderY, player.texX * LabyrinthAdventure.SIZE, player.texY * LabyrinthAdventure.SIZE, LabyrinthAdventure.SIZE, LabyrinthAdventure.SIZE);

        int playerCenterX = playerRenderX + LabyrinthAdventure.SIZE / 2;
        int playerCenterY = playerRenderY + LabyrinthAdventure.SIZE / 2;

        final int LIGHT_RADIUS = 130;

        for(int x = 0; x < width; x++) {
            double xPow = Math.pow(playerCenterX - x, 2);
            for(int y = 0; y < height; y++) {
                double yPow = Math.pow(playerCenterY - y, 2);
                double distanceSquare = xPow + yPow;

                if(distanceSquare <= LIGHT_RADIUS * LIGHT_RADIUS) {
                    int colour = getPixel(x, y);
                    double percent = 1.0 - distanceSquare / (LIGHT_RADIUS * LIGHT_RADIUS);

                    int r = (colour >> 16) & 0xff;
                    int g = (colour >> 8) & 0xff;
                    int b = colour & 0xff;

                    r *= percent;
                    g *= percent;
                    b *= percent;

                    setPixel(x, y, (255 << 24) | (r << 16) | (g << 8) | b);
                } else {
                    setPixel(x, y, 0xff000000);
                }
            }
        }
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
                Item item = level.items[x][y];
                if(item != null) {
                    renderItem(item, x, y, xOffset, yOffset);
                }
            }
        }
    }

    public void renderTile(Tile tile, int x, int y, int xOffset, int yOffset) {
        int xx = x * LabyrinthAdventure.SIZE + xOffset;
        int yy = y * LabyrinthAdventure.SIZE + yOffset;

        if(tile instanceof WallTile wallTile) {
            Texture texture = wallTile.getTexture(
                    level.getTileFromIndex(x, y - 1) instanceof WallTile,
                    level.getTileFromIndex(x + 1, y) instanceof WallTile,
                    level.getTileFromIndex(x, y + 1) instanceof WallTile,
                    level.getTileFromIndex(x - 1, y) instanceof WallTile
            );
            renderTexture(texture, xx, yy);
        } else {
            renderTexture(tile.getTexture(), xx, yy);
        }
    }

    public void renderItem(Item item, int x, int y, int xOffset, int yOffset) {
        int xx = x * LabyrinthAdventure.SIZE + xOffset;
        int yy = y * LabyrinthAdventure.SIZE + yOffset;

        if(item instanceof AnimatedItem anim) {
            renderTexture(anim.getTexture(), xx, yy, anim.getFrameIndex() * LabyrinthAdventure.SIZE, 0, LabyrinthAdventure.SIZE, LabyrinthAdventure.SIZE);
        } else {
            renderTexture(item.getTexture(), xx, yy);
        }
    }
}
