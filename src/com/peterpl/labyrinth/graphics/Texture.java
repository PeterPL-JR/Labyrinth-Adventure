package com.peterpl.labyrinth.graphics;

import com.peterpl.labyrinth.utils.*;

import javax.imageio.*;
import java.awt.image.*;

public class Texture extends Pixelated {
    public static final Texture PLAYER = new Texture("player");

    public Texture(String path) {
        try {
            BufferedImage img = ImageIO.read(Texture.class.getResourceAsStream("/textures/" + path + ".png"));
            width = img.getWidth();
            height = img.getHeight();
            pixels = new int[width * height];
            img.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Texture(Texture spritesheet, int xPos, int yPos, int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];

        for(int x = 0; x < width; x++) {
            int xx = x + xPos;
            for(int y = 0; y < height; y++) {
                int yy = y + yPos;
                setPixel(x, y, spritesheet.getPixel(xx, yy));
            }
        }
    }

    public Texture(Texture texture) {
        this(texture, 0, 0, texture.width, texture.height);
    }
}
