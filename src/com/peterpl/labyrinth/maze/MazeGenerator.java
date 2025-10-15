package com.peterpl.labyrinth.maze;

public class MazeGenerator {
    public final int[][] array;

    public final int width, height;
    public final int arrayWidth, arrayHeight;

    public static final int FLOOR = 0;
    public static final int WALL = 1;

    public MazeGenerator(int width, int height) {
        if(width % 2 != 1) {
            width += 1;
        }
        if(height % 2 != 1) {
            height += 1;
        }

        this.width = width;
        this.height = height;

        int arrayWidth = width * 2 + 1;
        int arrayHeight = height * 2 + 1;

        this.arrayWidth = arrayWidth;
        this.arrayHeight = arrayHeight;

        array = new int[arrayWidth][arrayHeight];
        for(int x = 0; x < arrayWidth; x++) {
            array[x] = new int[arrayHeight];
            for(int y = 0; y < arrayHeight; y++) {
                array[x][y] = x % 2 == 1 && y % 2 == 1 ? FLOOR : WALL;
            }
        }
    }
}
