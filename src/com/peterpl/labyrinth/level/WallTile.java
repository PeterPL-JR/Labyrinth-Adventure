package com.peterpl.labyrinth.level;

import com.peterpl.labyrinth.*;
import com.peterpl.labyrinth.graphics.*;

public class WallTile extends Tile {
    public static final Texture[][][][] BASIC_TEXTURES = createBasicTextures();

    public final Texture[][][][] textures;

    public WallTile(String name, FloorTile floorTile) {
        super(name, true);
        Texture floorTexture = floorTile.texture;

        textures = new Texture[2][][][];
        for(int u = 0; u < 2; u++) {
            textures[u] = new Texture[2][][];
            for(int r = 0; r < 2; r++) {
                textures[u][r] = new Texture[2][];
                for(int d = 0; d < 2; d++) {
                    textures[u][r][d] = new Texture[2];
                    for(int l = 0; l < 2; l++) {
                        Texture tex = new Texture(BASIC_TEXTURES[u][r][d][l]);
                        for(int x = 0; x < LabyrinthAdventure.SIZE; x++) {
                            for(int y = 0; y < LabyrinthAdventure.SIZE; y++) {
                                tex.setPixel(x, y, tex.getPixel(x, y) == 0xff000000 ? texture.getPixel(x, y) : floorTexture.getPixel(x, y));
                            }
                        }
                        textures[u][r][d][l] = tex;
                    }
                }
            }
        }
    }

    @Override
    public void update() {
    }

    public Texture getTexture(boolean up, boolean right, boolean down, boolean left) {
        int upIndex = up ? 1 : 0;
        int rightIndex = right ? 1 : 0;
        int downIndex = down ? 1 : 0;
        int leftIndex = left ? 1 : 0;

        return textures[upIndex][rightIndex][downIndex][leftIndex];
    }

    private static Texture[][][][] createBasicTextures() {
        Texture basicTexture = new Texture("tile/basic_tile");

        Texture[][][][] textures = new Texture[2][][][];
        for(int u = 0; u < 2; u++) {
            textures[u] = new Texture[2][][];
            for(int r = 0; r < 2; r++) {
                textures[u][r] = new Texture[2][];
                for(int d = 0; d < 2; d++) {
                    textures[u][r][d] = new Texture[2];
                    for(int l = 0; l < 2; l++) {
                        Texture texture = new Texture(basicTexture);
                        if(u == 1) {
                            for(int x = 1; x < LabyrinthAdventure.SIZE - 1; x++) {
                                texture.setPixel(x, 0, 0xff000000);
                                texture.setPixel(x, 1, 0xff000000);
                            }
                        }
                        if(l == 1) {
                            for(int y = 1; y < LabyrinthAdventure.SIZE - 1; y++) {
                                texture.setPixel(0, y, 0xff000000);
                                texture.setPixel(1, y, 0xff000000);
                            }
                        }
                        if(d == 1) {
                            for(int x = 1; x < LabyrinthAdventure.SIZE - 1; x++) {
                                texture.setPixel(x, LabyrinthAdventure.SIZE - 1, 0xff000000);
                                texture.setPixel(x, LabyrinthAdventure.SIZE - 2, 0xff000000);
                            }
                        }
                        if(r == 1) {
                            for(int y = 1; y < LabyrinthAdventure.SIZE - 1; y++) {
                                texture.setPixel(LabyrinthAdventure.SIZE - 1, y, 0xff000000);
                                texture.setPixel(LabyrinthAdventure.SIZE - 2, y, 0xff000000);
                            }
                        }
                        textures[u][r][d][l] = texture;
                    }
                }
            }
        }
        return textures;
    }
}
